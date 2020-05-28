package com.myprojects.pdfscanner.application;

public class Application {
    public static void main(String[] args) {
    	//Создаем экземпляр SearchForPdf и запускаем метод startPathScan
    	//Передаем в него конвертер и путь, считанный с консоли
    	SearchForPdf searchForPdf = new SearchForPdfImpl();
    	searchForPdf.startPathScan(PdfConverterImpl.getInstance(), FilePathScannerImpl.getInstance().getFilePath());
    }
}
