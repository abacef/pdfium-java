package com.abacef.pdfium_java;

import com.abacef.pdfium_java.fpdf_formfill.FPDF_FORMFILLINFO;
import com.abacef.pdfium_java.fpdf_view.FPDF_LIBRARY_CONFIG;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

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
            Pointer errorCode
    ) {
        Pointer doc = pdfium.FPDF_LoadMemDocument(pdf_bytes, pdfBytesLen, Pointer.NULL);
        if (doc == null) {
            errorCode.setInt(0, pdfium.FPDF_GetLastError().intValue());
            return Pointer.NULL;
        }

        return doc;
    }

    static FPDF_FORMFILLINFO makeFormFillInfo() {
        val formFillInfo = new FPDF_FORMFILLINFO();
        formFillInfo.version = 2;
        return formFillInfo;
    }

    static Pointer loadForm(Pointer doc, FPDF_FORMFILLINFO formFillInfo, Pointer errorCode) {
        Pointer form = pdfium.FPDFDOC_InitFormFillEnvironment(doc, formFillInfo);
        if (form == null) {
            errorCode.setInt(0, pdfium.FPDF_GetLastError().intValue());
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

    static Pointer loadPage(Pointer doc, Pointer form, int pageNum, Pointer errorCode) {
        Pointer page = pdfium.FPDF_LoadPage(doc, pageNum);
        if (page == null) {
            errorCode.setInt(0, pdfium.FPDF_GetLastError().intValue());
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

    static void renderPageToImage(
            Pointer page,
            Pointer form,
            int dpi,
            Pointer imageBuff,
            Pointer imageBuffLen,
            Pointer errorCode
    ) {
        //WIP
//        float pageWidthInPoints = 2
    }

    public static void renderPdfPageToImage(Pointer pdfBytes,
                                     int pdfBytesLen,
                                     int pageNum,
                                     int dpi,
                                     Pointer imageBuff,
                                     Pointer imageBuffLen,
                                     Pointer errorCode) {
        Pointer doc = loadDocument(pdfBytes, pdfBytesLen, errorCode);
        System.out.println("doc" + doc);
        if (doc == Pointer.NULL) {
            closeLibrary();
            return;
        }

        FPDF_FORMFILLINFO formFillInfo = makeFormFillInfo();
        Pointer form = loadForm(doc, formFillInfo, errorCode);
        if (form == null) {
            closeDocument(doc);
            closeLibrary();
            return;
        }

        Pointer page = loadPage(doc, form, pageNum, errorCode);
        if (page == null) {
            closeForm(form);
            closeDocument(doc);
            closeLibrary();
            return;
        }

        renderPageToImage(page, form, dpi, imageBuff, imageBuffLen, errorCode);
        if (imageBuff == null) {
            closeRenderPage(page);
        }

        closePage(page, form);
        closeForm(form);
        closeDocument(doc);
        closeLibrary();
    }
}
