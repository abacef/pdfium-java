package com.abacef.pdfium_java;

import com.abacef.pdfium_java.fpdf_formfill.FPDF_FORMFILLINFO;
import com.abacef.pdfium_java.fpdf_view.FPDF_LIBRARY_CONFIG;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import lombok.Builder;
import lombok.Data;
import lombok.val;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Renderer {

    public static Pdfium pdfium;

    private static void initializeLibrary() {
        FPDF_LIBRARY_CONFIG config = new FPDF_LIBRARY_CONFIG();
        config.version = 2;
        config.m_pUserFontPaths = Pointer.NULL;
        config.m_pIsolate = Pointer.NULL;
        config.m_v8EmbedderSlot = 0;
        config.m_pPlatform = Pointer.NULL;
    }

    static void closeLibrary() {
        pdfium.FPDF_DestroyLibrary();
        pdfium = null;
    }

    static Pointer loadDocument(
            Pointer pdf_bytes,
            int pdfBytesLen,
            IntByReference errorCode
    ) {
        Pointer doc = pdfium.FPDF_LoadMemDocument(pdf_bytes, pdfBytesLen, Pointer.NULL);
        if (doc == null) {
            errorCode.setValue(pdfium.FPDF_GetLastError().intValue());
            return Pointer.NULL;
        }

        return doc;
    }

    static FPDF_FORMFILLINFO makeFormFillInfo() {
        val formFillInfo = new FPDF_FORMFILLINFO();
        formFillInfo.version = 2;
        return formFillInfo;
    }

    static Pointer loadForm(Pointer doc, FPDF_FORMFILLINFO formFillInfo, IntByReference errorCode) {
        Pointer form = pdfium.FPDFDOC_InitFormFillEnvironment(doc, formFillInfo);
        if (form == null) {
            errorCode.setValue(pdfium.FPDF_GetLastError().intValue());
            return null;
        }

        pdfium.FPDF_SetFormFieldHighlightColor(form, Pdfium.FPDF_FORMFIELD_UNKNOWN, new NativeLong(0xFFE4dd));
        pdfium.FPDF_SetFormFieldHighlightAlpha(form, (byte)100);
        pdfium.FORM_DoDocumentOpenAction(form);

        return form;
    }

    static void closeDocument(Pointer doc) {
        pdfium.FPDF_CloseDocument(doc);
    }

    static Pointer loadPage(Pointer doc, Pointer form, int pageNum, IntByReference errorCode) {
        Pointer page = pdfium.FPDF_LoadPage(doc, pageNum);
        if (page == null) {
            errorCode.setValue(pdfium.FPDF_GetLastError().intValue());
            return null;
        }

        pdfium.FORM_OnAfterLoadPage(page, form);
        pdfium.FORM_DoPageAAction(page, form, Pdfium.FPDFPAGE_AACTION_OPEN);

        return page;
    }

    static void closeForm(Pointer form) {
        pdfium.FPDFDOC_ExitFormFillEnvironment(form);
    }

    static void closePage(Pointer page, Pointer form) {
        pdfium.FORM_DoPageAAction(page, form, Pdfium.FPDFPAGE_AACTION_CLOSE);
        pdfium.FORM_OnBeforeClosePage(page, form);
        pdfium.FPDF_ClosePage(page);
    }

    static void closeRenderPage(Pointer page) {
        pdfium.FPDF_RenderPage_Close(page);
    }

    @Data
    @Builder
    private static class RenderBitmapReturn {
        private Pointer buffer;
        private int width;
        private int height;
    }

    static RenderBitmapReturn renderPageToBitmap(
            Pointer page,
            Pointer form,
            int dpi
    ) {
        val pageWidthInPoints = pdfium.FPDF_GetPageWidthF(page);
        val pageWidthInInches = pageWidthInPoints / 72;
        val width = (int)(pageWidthInInches * dpi);

        val pageHeightInPoints = pdfium.FPDF_GetPageHeightF(page);
        val pageHeightInInches = pageHeightInPoints / 72;
        val height = (int)(pageHeightInInches * dpi);

        val alpha = pdfium.FPDFPage_HasTransparency(page);

        System.out.println("Width " + width);
        System.out.println("Height " + height);
        System.out.println("alpha " + alpha);

        Pointer bitmap = pdfium.FPDFBitmap_Create(width, height, alpha);
        if (bitmap == null) {
            System.out.println("Failed");
            return null;
        }

        NativeLong fillColor = new NativeLong(alpha == 1 ? 0x00000000 : 0xffffffff);
        pdfium.FPDFBitmap_FillRect(bitmap, 0, 0, width, height, fillColor);
        pdfium.FPDF_RenderPageBitmap(bitmap, page, 0, 0, width, height, 0, Pdfium.FPDF_ANNOT);
        pdfium.FPDF_FFLDraw(form, bitmap, page, 0, 0, width, height, 0, Pdfium.FPDF_ANNOT);

        Pointer buffer = pdfium.FPDFBitmap_GetBuffer(bitmap);

        return RenderBitmapReturn.builder()
                .buffer(buffer)
                .width(width)
                .height(height)
                .build();
    }

    public static BufferedImage renderPdfPageToImage(
            Pointer pdfBytes,
            int pdfBytesLen,
            int pageNum,
            int dpi,
            IntByReference errorCode
    ) {
        Pointer doc = loadDocument(pdfBytes, pdfBytesLen, errorCode);
        if (doc == null) {
            System.out.println("Doc is null");
            closeLibrary();
            return null;
        }

        FPDF_FORMFILLINFO formFillInfo = makeFormFillInfo();
        Pointer form = loadForm(doc, formFillInfo, errorCode);
        if (form == null) {
            System.out.println("form is null");
            closeDocument(doc);
            closeLibrary();
            return null;
        }

        Pointer page = loadPage(doc, form, pageNum, errorCode);
        if (page == null) {
            System.out.println("page is null");
            closeForm(form);
            closeDocument(doc);
            closeLibrary();
            return null;
        }

        RenderBitmapReturn bitmapRenderReturn = renderPageToBitmap(page, form, dpi);
        BufferedImage image = null;
        if (bitmapRenderReturn == null) {
            System.out.println("image buff is null");
            System.out.println(errorCode.getValue());
            closeRenderPage(page);
        } else {
            val width = bitmapRenderReturn.getWidth();
            val height = bitmapRenderReturn.getHeight();
            val buffer = bitmapRenderReturn.getBuffer();

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            int pixelCount = width * height;
            int[] javaBuffer = buffer.getIntArray(0, pixelCount);
            bufferedImage.setRGB(0, 0, width, height, javaBuffer, 0, width);
            image = bufferedImage;
        }

        closePage(page, form);
        closeForm(form);
        closeDocument(doc);
        closeLibrary();
        return image;
    }
}
