package com.abacef.pdfium_java;

import com.abacef.pdfium_java.fpdf_formfill.FPDF_FORMFILLINFO;
import com.abacef.pdfium_java.fpdf_view.FPDF_LIBRARY_CONFIG;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

public interface Pdfium extends Library {
    String SHARED_OBJECT_NAME = "pdfium";

    Pdfium instance = Native.load(SHARED_OBJECT_NAME, Pdfium.class);

    void FPDF_InitLibrary();
    void FPDF_DestroyLibrary();
    void FPDF_InitLibraryWithConfig(FPDF_LIBRARY_CONFIG config);
    Pointer FPDF_LoadMemDocument(Pointer dataBuf, int size, Pointer password);
    NativeLong FPDF_GetLastError();
    void FPDFDOC_InitFormFillEnvironment(Pointer doc, FPDF_FORMFILLINFO formFillInfo);

    int FPDF_ERR_SUCCESS = 0;    // No error.
    int FPDF_ERR_UNKNOWN  = 1;   // Unknown error.
    int FPDF_ERR_FILE = 2;       // File not found or could not be opened.
    int FPDF_ERR_FORMAT = 3;     // File not in PDF format or corrupted.
    int FPDF_ERR_PASSWORD = 4;   // Password required or incorrect password.
    int FPDF_ERR_SECURITY = 5;   // Unsupported security scheme.
    int FPDF_ERR_PAGE = 6;       // Page not found or content error.
    // #ifdef PDF_ENABLE_XFA
    int FPDF_ERR_XFALOAD = 7;    // Load XFA error.
    int FPDF_ERR_XFALAYOUT = 8;  // Layout XFA error.
    // #endif
}
