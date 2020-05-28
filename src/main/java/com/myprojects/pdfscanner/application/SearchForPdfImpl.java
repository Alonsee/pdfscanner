package com.myprojects.pdfscanner.application;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Класс запускает сканирование папок и поиск в них файлов pdf
 */
public class SearchForPdfImpl implements SearchForPdf{

	private PdfConverter converter;
	
	private ScheduledExecutorService executorService;
	
    public SearchForPdfImpl(){}
	
    //Метод запускает сканирование заданного каталога
    //и конвертирование файлов с помощью класса PdfConverter
    //с заданной переодичностью
    public void startPathScan(PdfConverter converter, String path) {
    	
        setConverter(converter);
        //Создаем исполнитель для одного потока
        executorService = Executors.newScheduledThreadPool(1);
        //Запускаем исполнитель
        //В выражении запускаеться метод searchPdf
        executorService.scheduleAtFixedRate(()->{
	        File file = new File(path);
	        //Проверяем что каталог существует и запускаем метод searchPdf
	        if(file.exists()) {
	        	try {
	    			searchPdf(file);
	    		} catch (InvalidPasswordException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        }
	    }, 
        0, 300, TimeUnit.SECONDS);
    }
    
    //Метод проверяет каждый файл в каталоге.
    private void searchPdf(File file) throws InvalidPasswordException, IOException {
    	System.out.println("search pdf in directory: " + file.getName());
    	
    	//Получаем содержимое каталога
    	File[] childFiles = file.listFiles();
    	for (File f: childFiles) {
    		
    		//Если файл является каталогом рекурсивно вызываем метод searchPdf
    		if (f.isDirectory()) {
    			searchPdf(f);
    		}
    		
    	    //Если файл имеет расширение pdf вызываем метод convert
    		if (f.getName().contains(".pdf")) {
    			//Если подкаталога IMG нету, создаем его
    			File imgFile = new File(f.getParentFile().getAbsolutePath() + "\\IMG");
    			if (!imgFile.exists()) {
    				imgFile.mkdir();
    			}
    			converter.convert(f.getAbsolutePath());
    		}
    	}
    }

	private void setConverter(PdfConverter converter) {
		this.converter = converter;
	}
}
