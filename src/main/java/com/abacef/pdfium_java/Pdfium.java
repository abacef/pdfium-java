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
    Pointer FPDFDOC_InitFormFillEnvironment(Pointer doc, FPDF_FORMFILLINFO formFillInfo);
    void FPDF_SetFormFieldHighlightColor(Pointer form, int fieldType, NativeLong color);
    void FPDF_SetFormFieldHighlightAlpha(Pointer form, byte alpha);
    void FORM_DoDocumentOpenAction(Pointer form);
    void FPDF_CloseDocument(Pointer doc);
    Pointer FPDF_LoadPage(Pointer doc, int pageIndex);
    void FORM_OnAfterLoadPage(Pointer page, Pointer form);
    void FORM_DoPageAAction(Pointer page, Pointer form, int action);
    void FPDFDOC_ExitFormFillEnvironment(Pointer form);
    void FPDF_RenderPage_Close(Pointer page);
    void FORM_OnBeforeClosePage(Pointer page, Pointer form);
    void FPDF_ClosePage(Pointer page);


    // Error Codes
    int FPDF_ERR_SUCCESS = 0;    // No error.
    int FPDF_ERR_UNKNOWN  = 1;   // Unknown error.
    int FPDF_ERR_FILE = 2;       // File not found or could not be opened.
    int FPDF_ERR_FORMAT = 3;     // File not in PDF format or corrupted.
    int FPDF_ERR_PASSWORD = 4;   // Password required or incorrect password.
    int FPDF_ERR_SECURITY = 5;   // Unsupported security scheme.
    int FPDF_ERR_PAGE = 6;       // Page not found or content error.

    // Form Field Types
    int FPDF_FORMFIELD_UNKNOWN = 0;      // Unknown.
    int FPDF_FORMFIELD_PUSHBUTTON = 1;   // push button type.
    int FPDF_FORMFIELD_CHECKBOX = 2;     // check box type.
    int FPDF_FORMFIELD_RADIOBUTTON = 3;  // radio button type.
    int FPDF_FORMFIELD_COMBOBOX = 4;     // combo box type.
    int FPDF_FORMFIELD_LISTBOX = 5;      // list box type.
    int FPDF_FORMFIELD_TEXTFIELD = 6;    // text field type.
    int FPDF_FORMFIELD_SIGNATURE = 7;    // text field type.

    // Additional-action types of page object:
    //   OPEN (/O) -- An action to be performed when the page is opened
    //   CLOSE (/C) -- An action to be performed when the page is closed
    int FPDFPAGE_AACTION_OPEN = 0;
    int FPDFPAGE_AACTION_CLOSE = 1;
}
