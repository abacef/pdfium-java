# PDFium Java
I am going to make a wrapper around PDFium using Java. I have heard of automated ways of doing this like JNAerator, but I was running ingo a wall trying to get it to work, so I am going to do it manually.

## Build
Currently, to test the jar I have to do `mvn clean compile assembly:single` and then run the jar, that executes the main function where I put some test code. I need to figure out how to do this so run this so that when I run tests, it just works, and does not say it could not find the JNA file.