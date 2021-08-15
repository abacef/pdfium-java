package com.abacef.pdfium_java;

public class Main {
    public static void main(String[] args) {
        PdfiumBackend backend = PdfiumBackend.instance;
        backend.FPDF_InitLibrary();
        System.out.println("hello");
    }
}
