package com.abacef.pdfium_java;

public class Main {
    public static void main(String[] args) {
        Pdfium backend = Pdfium.instance;
        backend.FPDF_InitLibrary();



        backend.FPDF_DestroyLibrary();
        System.out.println("hello");
    }
}
