package com.myprojects.pdfscanner.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс считывает путь к сканируемому каталогу с консоли
 */
public class FilePathScannerImpl implements FilePathScanner{
	
    private static FilePathScannerImpl INSTANCE = new FilePathScannerImpl();

    private FilePathScannerImpl() {}

    public static FilePathScannerImpl getInstance() {
    	return INSTANCE;
    }
    
    @Override
    public String getFilePath() {
    	
    	String filePath = "";

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
        	System.out.println("Enter path to scan:");
        	while (true) {
	        	filePath = br.readLine();
	            File file = new File(filePath);
	            // Проверка существования каталога
	            if (file.exists()) {
	            	return filePath;
	            }
	            else {
	            	//Если каталог не найден, происходит повторный запрос ввода
	            	System.out.println("File does not exists!");
	            	System.out.println("Enter path to scan:");
	            }
        	}
    	} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
}
