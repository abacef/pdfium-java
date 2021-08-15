package com.abacef.pdfium_java;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface PdfiumBackend extends Library {
    String SHARED_OBJECT_NAME = "pdfium";

    PdfiumBackend instance = Native.load(SHARED_OBJECT_NAME, PdfiumBackend.class);

    void FPDF_InitLibrary();
//    void FPDF_InitLibraryWithConfig
}
