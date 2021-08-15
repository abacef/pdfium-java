package com.abacef.pdfium_java;

import org.junit.jupiter.api.Test;

public class PdfiumBackendTest {

    @Test
    public void initTest() {
        PdfiumBackend backend = PdfiumBackend.instance;
        backend.FPDF_InitLibrary();
        System.out.println("hello");
    }
}
