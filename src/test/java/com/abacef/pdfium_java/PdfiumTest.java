package com.abacef.pdfium_java;

import com.abacef.pdfium_java.fpdf_view.FPDF_LIBRARY_CONFIG;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static com.abacef.pdfium_java.Renderer.renderPdfPageToImage;

public class PdfiumTest {

    Pdfium pdfium;

    void initLibraryWithConfigSetup() {
        val config = new FPDF_LIBRARY_CONFIG();
        config.version = 2;
        config.m_pUserFontPaths = Pointer.NULL;
        config.m_pIsolate = Pointer.NULL;
        config.m_v8EmbedderSlot = 0;
        config.m_pPlatform = Pointer.NULL;
        pdfium.FPDF_InitLibraryWithConfig(config);
    }

    @BeforeEach
    void beforeEach() {
        pdfium = Pdfium.instance;
        initLibraryWithConfigSetup();
    }

    @AfterEach
    void afterEach() {
        pdfium.FPDF_DestroyLibrary();
        pdfium = null;
    }

    @Test
    @Ignore
    public void initLibrary() {
        pdfium.FPDF_DestroyLibrary();
        pdfium.FPDF_InitLibrary();
    }

    @Test
    public void testRenderPdf() throws IOException {
        val bytes = Files.readAllBytes(Paths.get("src/test/resources/sample doc.pdf"));

        val pdfBytesMemory = new Memory((long) bytes.length * Native.getNativeSize(Byte.TYPE));
        IntStream.range(0, bytes.length).forEach(i ->
                pdfBytesMemory.setByte((long) i * Native.getNativeSize(Byte.TYPE), bytes[i]));

        Renderer.pdfium = pdfium;

        renderPdfPageToImage(pdfBytesMemory, bytes.length, 0, 300, Pointer.NULL, Pointer.NULL, Pointer.NULL);
    }




}
