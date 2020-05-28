package com.myprojects.pdfscanner.application;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Класс конвертирует обнаруженные pdf файлы в jpg формат
 */
public class PdfConverterImpl implements PdfConverter{

    private static PdfConverterImpl INSTANCE= new PdfConverterImpl();
    
    private PdfConverterImpl() {}
    
    public static PdfConverterImpl getInstance() {
    	return INSTANCE;
    }
    
    @Override
    public void convert(String path) throws InvalidPasswordException, IOException {
    	
    	//Создаем объект файла по указанному пути
    	File file = new File(path);
    		
    	System.out.println("convert file to jpg " + file.getName());
    	
    	//Создаем объект pdf документа из файла
    	PDDocument document = PDDocument.load(file);
    	
    	//Создаем рендер для документа pdf
    	PDFRenderer pdfRenderer = new PDFRenderer(document);
    	
    	//Сохраняем изображение для каждой страницы
    	for (int page = 0; page < document.getNumberOfPages(); page++) {		
    		//Прописываем путь к каталогу IMG и название с расширением jpg
    		String imgName = file.getParentFile().getPath() 
    				+ "\\IMG\\" + file.getName().replace(".pdf", "") 
    				+  "_" + (page+1) + ".jpg" ;
    		//Если изображение еще не создано, создаем и сохраняем его
    		if (!existCheck(imgName)) {
    			//Изображение для текеущей страницы
    			BufferedImage img = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
    			System.out.println("Created image: " + imgName);
    			//Сохранение изображения
    			ImageIOUtil.writeImage(img, imgName, 150);
    		}
    	}
    	
    	document.close();
    }

    //Проверяем, создавалось ли изображение ранее
    private boolean existCheck(String imgPath) {
    	File file = new File(imgPath);
    	return file.exists() ? true : false;
    }
}
