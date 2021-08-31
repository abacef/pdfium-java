package com.abacef.pdfium;

import com.abacef.pdfium.fpdf_view.FPDF_LIBRARY_CONFIG;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static com.abacef.pdfium.Renderer.renderPdfPageToImage;

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
    public void testRenderPdf() throws IOException {
        val bytes = Files.readAllBytes(Paths.get("src/test/resources/sample doc.pdf"));

        val pdfBytesMemory = new Memory((long) bytes.length * Native.getNativeSize(Byte.TYPE));
        IntStream.range(0, bytes.length).forEach(i ->
                pdfBytesMemory.setByte((long) i * Native.getNativeSize(Byte.TYPE), bytes[i]));

        Renderer.pdfium = pdfium;

        val errorCode = new IntByReference();

        BufferedImage bufferedImage = renderPdfPageToImage(pdfBytesMemory, bytes.length, 0, 300, errorCode);
        assert bufferedImage != null;

//        File out = new File("src/test/resources/sampleOutput" + 0 + ".png");
//        ImageIO.write(bufferedImage,"png", out);
    }




}
