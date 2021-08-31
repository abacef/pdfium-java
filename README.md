# PDFium Java
I exposed a couple methods from the [PDFium](https://pdfium.googlesource.com/pdfium/) library that can be called using this package. You do not need to have the shared object downloaded somewhere! It is bundled in this jar.

I have heard of automated ways of doing this like JNAerator, but I was running into a wall trying to get it to work, so I did a couple methods manually

## Using

Currently, I have only bundled the linux x86_64 binary, so if you want to use it on another platform, let me know. It is as simple as adding a binary to an aptly named folder.

I hope to deploy this to maven central in the future. I may have done that by the time you are reading this. If so, download it using maven like so

```xml
<dependency>
    <groupId>io.github.abacef</groupId>
    <artifactId>pdfium</artifactId>
    <version>0.0.1</version>
</dependency>
```
or however you Gradle people do it. I am pretty sure you got used to translating by now.

```java
import io.github.abacef.pdfium.fpdf_view.FPDF_LIBRARY_CONFIG;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Main{
    public static void main(String[] args) {
        final Pdfium pdfium = Pdfium.newInstance();

        final FPDF_LIBRARY_CONFIG config = new FPDF_LIBRARY_CONFIG();
        config.version = 2;
        config.m_pUserFontPaths = Pointer.NULL;
        config.m_pIsolate = Pointer.NULL;
        config.m_v8EmbedderSlot = 0;
        config.m_pPlatform = Pointer.NULL;
        pdfium.FPDF_InitLibraryWithConfig(config);

        final byte[] bytes = Files.readAllBytes(Paths.get("path/to/a/pdf.pdf"));

        final Memory pdfBytesMemory = new Memory((long) bytes.length * Native.getNativeSize(Byte.TYPE));
        IntStream.range(0, bytes.length).forEach(i ->
                pdfBytesMemory.setByte((long) i * Native.getNativeSize(Byte.TYPE), bytes[i]));
        
        // Implement some logic, for example rendering a PDF like https://github.com/mara004/pypdfium-reboot
        // Also, use Java BufferedImage to convert the bitmap to an image: 
        // https://github.com/Pacman29/Pdfium.Java/blob/master/src/main/java/pdfium/PDFBitmap.java#L49
    }
}
```

## PDFium version
Pre-built binaries are taken from the releases of [bblanchon's builds](https://github.com/bblanchon/pdfium-binaries)
I am not sure which version is currently built with `0.0.1` of this package, but it is around [4619](https://github.com/bblanchon/pdfium-binaries/releases/tag/chromium%2F4619)

## Contributions
Feel free to submit pull requests to this repo, especially if you want to add public methods, since I only added the ones that I need to render a PDF the way I am doing it

## Why did I create this package
I wanted to be able to render PDFs in Java and I realized that no one had published PDFium java bindings on maven central. There is obviously [Pdfium.Java](https://github.com/Pacman29/Pdfium.Java) which I took some inspiration from, but it is not useable on the public repositories.

