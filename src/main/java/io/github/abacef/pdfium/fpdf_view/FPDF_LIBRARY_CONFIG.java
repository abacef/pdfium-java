package io.github.abacef.pdfium.fpdf_view;

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
    public int version;
    public Pointer m_pUserFontPaths;
    public Pointer m_pIsolate;
    public int m_v8EmbedderSlot;
    public Pointer m_pPlatform;
}
