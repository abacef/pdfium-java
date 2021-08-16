package com.abacef.pdfium_java.fpdf_view;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({
        "version",
        "m_pUserFontPaths",
        "m_pIsolate",
        "m_v8EmbedderSlot",
        "m_pPlatform"
})
public class FPDF_LIBRARY_CONFIG extends Structure {
    int version;
    Pointer m_pUserFontPaths;
    Pointer m_pIsolate;
    int m_v8EmbedderSlot;
    Pointer m_pPlatform;
}
