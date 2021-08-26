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
    float FPDF_GetPageWidthF(Pointer page);
    float FPDF_GetPageHeightF(Pointer page);
    int FPDFPage_HasTransparency(Pointer page);
    Pointer FPDFBitmap_Create(int width, int height, int alpha);
    void FPDFBitmap_FillRect(Pointer bitmap, int left, int top, int width, int height, NativeLong color);
    void FPDF_RenderPageBitmap(
            Pointer bitmap, Pointer page, int startX, int startY, int sizeX, int sizeY, int rotate, int flags);
    void FPDF_FFLDraw(Pointer form, Pointer bitmap, Pointer page, int startX, int startY, int sizeX, int sizeY, int rotate, int flags);
    Pointer FPDFBitmap_GetBuffer(Pointer bitmap);



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

    // Page rendering flags. They can be combined with bit-wise OR.
    //
    // Set if annotations are to be rendered.
    int FPDF_ANNOT = 0x01;
    // Set if using text rendering optimized for LCD display. This flag will only
    // take effect if anti-aliasing is enabled for text.
    int FPDF_LCD_TEXT = 0x02;
    // Don't use the native text output available on some platforms
    int FPDF_NO_NATIVETEXT = 0x04;
    // Grayscale output.
    int FPDF_GRAYSCALE = 0x08;
    // Obsolete, has no effect, retained for compatibility.
    int FPDF_DEBUG_INFO = 0x80;
    // Obsolete, has no effect, retained for compatibility.
    int FPDF_NO_CATCH = 0x100;
    // Limit image cache size.
    int FPDF_RENDER_LIMITEDIMAGECACHE = 0x200;
    // Always use halftone for image stretching.
    int FPDF_RENDER_FORCEHALFTONE = 0x400;
    // Render for printing.
    int FPDF_PRINTING = 0x800;
    // Set to disable anti-aliasing on text. This flag will also disable LCD
    // optimization for text rendering.
    int FPDF_RENDER_NO_SMOOTHTEXT = 0x1000;
    // Set to disable anti-aliasing on images.
    int FPDF_RENDER_NO_SMOOTHIMAGE = 0x2000;
    // Set to disable anti-aliasing on paths.
    int FPDF_RENDER_NO_SMOOTHPATH = 0x4000;
    // Set whether to render in a reverse Byte order, this flag is only used when
    // rendering to a bitmap.
    int FPDF_REVERSE_BYTE_ORDER = 0x10;
    // Set whether fill paths need to be stroked. This flag is only used when
    // FPDF_COLORSCHEME is passed in, since with a single fill color for paths the
    // boundaries of adjacent fill paths are less visible.
    int FPDF_CONVERT_FILL_TO_STROKE = 0x20;
}
