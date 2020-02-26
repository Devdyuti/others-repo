package org.dev.excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dev.password.AES;

public class ExcelUtilImpl implements ExcelUtils{

	private final String PATH;
	private final  String FILE_PATH;
	
	
	public ExcelUtilImpl(String PATH, String FILE_PATH) {
		this.PATH=PATH;
		this.FILE_PATH=FILE_PATH;
		
	}
	
	
	
	public void writeExcel() throws IOException{
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet(SHEET_NAME);
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", COLUMN_HEADER);  
        data.put("2", new Object[]{ 1, "Devdyuti", "singhking5414@gmail.com",AES.encrypt("bazigar99"),"na","na" }); 
		
        Set<String> keyset=data.keySet();
		int rowno=0;
		for (String key : keyset) {
			Row row=sheet.createRow(rowno++);
			Object[] objArr=data.get(key);
			int cellno=0;
			
			for (Object obj : objArr) {
				Cell cell=row.createCell(cellno++);
				
				if(obj instanceof String) {
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}
			}
		}
	
        try {
			FileOutputStream out= new FileOutputStream(new File(PATH));
			workbook.write(out);
			out.close();
			System.out.println("File Generated Successfully!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void appendExcel(Scanner sr) throws IOException {
		
		System.out.print("Enter NAME: ");String name=sr.next();
		System.out.print("Enter : USERID:");String email=sr.next();
		System.out.print("Enter PASSWORD: ");String password=sr.next();
		System.out.print("Enter REGISTRED NUMBER: ");String regnum=sr.next();
		System.out.print("Enter AC NUMBER: ");String account=sr.next();
		
		String[] detailAr= {name,email,AES.encrypt(password),regnum,account};
		
		try {
			InputStream inp=new FileInputStream(PATH);
			XSSFWorkbook workbook = new XSSFWorkbook(inp); 
			Sheet sheet = workbook.getSheetAt(0); 
			
			int num = sheet.getLastRowNum();
			Row row = sheet.createRow(++num);
			
			int last_serial_no=getLastSerialNum(PATH);
			last_serial_no++;
		
			row.createCell(0).setCellValue(last_serial_no++);
			for(int i=0;i<detailAr.length;i++) {
				row.createCell(i+1).setCellValue(detailAr[i]);
			}
			FileOutputStream fileOut = new FileOutputStream(PATH); 
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Details added successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getLastSerialNum(String PATH) throws IOException {
		int serial_number=0;
		try {
			FileInputStream fis=new FileInputStream(new File(PATH));
			XSSFWorkbook workbook = new XSSFWorkbook(fis); 
			XSSFSheet sheet = workbook.getSheetAt(0); 
			Iterator<Row> rowIterator=sheet.iterator();
			
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					
					switch (cell.getCellType()) { 
                    case Cell.CELL_TYPE_NUMERIC: 
                        double sr=cell.getNumericCellValue();
                        serial_number=(int) sr;
                        break; 
                    case Cell.CELL_TYPE_STRING:  
                        break; 
                        
					}     
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return serial_number;
	}
	public String getPrintMessage(String message_no) throws IOException {
		FileReader fr=new FileReader(new File(FILE_PATH));
		int i;
		String str="";
		while((i=fr.read()) != -1) {
			str+=(char) i;
		}
		String[] srAr=str.split("~");
		for(int j=0;j<srAr.length;j++) {
			if(srAr[j].contains(message_no)) {
				return srAr[j];
			}
		}
		fr.close();
		return null;
	}
	public void readExcel() throws IOException {
		try {
			FileInputStream fis=new FileInputStream(new File(PATH));
			XSSFWorkbook workbook = new XSSFWorkbook(fis); 
			XSSFSheet sheet = workbook.getSheetAt(0); 
			
			Iterator<Row> rowIterator=sheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next(); 
					switch (cell.getCellType()) { 
                    case Cell.CELL_TYPE_NUMERIC: 
                    	int serial_no=(int) cell.getNumericCellValue();
                        System.out.print(serial_no+ "  "); 
                        break; 
                    case Cell.CELL_TYPE_STRING: 
                        System.out.print(cell.getStringCellValue() + "  "); 
                        break; 
                        
					}     
				}
				System.out.println(""); 
				
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void searchDetails(String account_name) throws IOException {
		try {
			FileInputStream fis=new FileInputStream(new File(PATH));
			XSSFWorkbook workbook = new XSSFWorkbook(fis); 
			XSSFSheet sheet = workbook.getSheetAt(0); 
			
			Iterator<Row> rowIterator=sheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next(); 
										
					switch (cell.getCellType()) { 
                    case Cell.CELL_TYPE_NUMERIC: 
                    	//int serial_no=(int) cell.getNumericCellValue();
                        //System.out.print(serial_no+ "  "); 
                        break; 
                    case Cell.CELL_TYPE_STRING:
                    	String name=cell.getStringCellValue();
                        if(name.equals(account_name)) {
                        	Row searchRow=cell.getRow();
                        	Iterator<Cell> cellSearchIterator = searchRow.cellIterator();
                        	while(cellSearchIterator.hasNext()) {
                        		Cell searchCell=cellSearchIterator.next();
                        		switch(searchCell.getCellType()) {
                        			case Cell.CELL_TYPE_NUMERIC:
                        				int serial_no=(int) searchCell.getNumericCellValue();
                        				System.out.print(serial_no+ "  ");
                        				break;
                        			case Cell.CELL_TYPE_STRING:
                        				String strCellValue=searchCell.getStringCellValue();
                        				if(strCellValue.contains("==")) {
                        					System.out.print(AES.decrypt(strCellValue)+ "  ");	
                        				}else {
                        					System.out.print(strCellValue+ "  ");
                        				}
                        				
                        				break;
                        		}
                        	}
                        	
                        }
                        break; 
                        
					}     
				}
				System.out.println(""); 
				
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
