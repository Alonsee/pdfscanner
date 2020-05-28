package com.myprojects.pdfscanner.application;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public interface PdfConverter {
    public void convert(String path)
    		throws InvalidPasswordException, IOException;
}
