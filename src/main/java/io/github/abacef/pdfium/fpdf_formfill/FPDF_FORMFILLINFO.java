package io.github.abacef.pdfium.fpdf_formfill;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({
        "version",
        "Release",
        "FFI_Invalidate",
        "FFI_OutputSelectedRect",
        "FFI_SetCursor",
        "FFI_SetTimer",
        "FFI_KillTimer",
        "FFI_GetLocalTime",
        "FFI_OnChange",
        "FFI_GetPage",
        "FFI_GetCurrentPage",
        "FFI_GetRotation",
        "FFI_ExecuteNamedAction",
        "FFI_SetTextFieldFocus",
        "FFI_DoURIAction",
        "FFI_DoGoToAction",
        "m_pJsPlatform",
        "xfa_disabled",
        "FFI_DisplayCaret",
        "FFI_GetCurrentPageIndex",
        "FFI_SetCurrentPage",
        "FFI_GotoURL",
        "FFI_GetPageViewRect",
        "FFI_PageEvent",
        "FFI_PopupMenu",
        "FFI_OpenFile",
        "FFI_EmailTo",
        "FFI_UploadTo",
        "FFI_GetPlatform",
        "FFI_GetLanguage",
        "FFI_DownloadFromURL",
        "FFI_PostRequestURL",
        "FFI_PutRequestURL",
        "FFI_OnFocusChange",
        "FFI_DoURIActionWithKeyboardModifier"
})
public class FPDF_FORMFILLINFO extends Structure {
    public int version;
    public Pointer Release;
    public Pointer FFI_Invalidate;
    public Pointer FFI_OutputSelectedRect;
    public Pointer FFI_SetCursor;
    public Pointer FFI_SetTimer;
    public Pointer FFI_KillTimer;
    public Pointer FFI_GetLocalTime;
    public Pointer FFI_OnChange;
    public Pointer FFI_GetPage;
    public Pointer FFI_GetCurrentPage;
    public Pointer FFI_GetRotation;
    public Pointer FFI_ExecuteNamedAction;
    public Pointer FFI_SetTextFieldFocus;
    public Pointer FFI_DoURIAction;
    public Pointer FFI_DoGoToAction;
    public Pointer m_pJsPlatform;
    public boolean xfa_disabled;
    public Pointer FFI_DisplayCaret;
    public Pointer FFI_GetCurrentPageIndex;
    public Pointer FFI_SetCurrentPage;
    public Pointer FFI_GotoURL;
    public Pointer FFI_GetPageViewRect;
    public Pointer FFI_PageEvent;
    public Pointer FFI_PopupMenu;
    public Pointer FFI_OpenFile;
    public Pointer FFI_EmailTo;
    public Pointer FFI_UploadTo;
    public Pointer FFI_GetPlatform;
    public Pointer FFI_GetLanguage;
    public Pointer FFI_DownloadFromURL;
    public Pointer FFI_PostRequestURL;
    public Pointer FFI_PutRequestURL;
    public Pointer FFI_OnFocusChange;
    public Pointer FFI_DoURIActionWithKeyboardModifier;
}
