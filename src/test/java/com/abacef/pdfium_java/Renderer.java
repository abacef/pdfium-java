package com.abacef.pdfium_java;

import com.abacef.pdfium_java.fpdf_formfill.FPDF_FORMFILLINFO;
import com.abacef.pdfium_java.fpdf_view.FPDF_LIBRARY_CONFIG;
import com.sun.jna.Memory;
import com.sun.jna.Native;
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
        return Pointer.NULL;
    }

    public static void renderPdfPageToImage(Pointer pdfBytes,
                                     int pdfBytesLen,
                                     int pageNum,
                                     int dpi,
                                     Pointer image_buf,
                                     Pointer imageBuffLen,
                                     Pointer errorCode) {
        Pointer doc = loadDocument(pdfBytes, pdfBytesLen, errorCode);
        System.out.println("doc" + doc);
        if (doc == Pointer.NULL) {
            closeLibrary();
            return;
        }

        FPDF_FORMFILLINFO formFillInfo = makeFormFillInfo();
        Pointer formHandle = loadForm(doc, formFillInfo, errorCode);

    }
}
