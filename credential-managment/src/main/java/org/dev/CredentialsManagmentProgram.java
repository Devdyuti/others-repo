package org.dev;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.dev.enums.Message;
import org.dev.enums.TaskName;
import org.dev.excel.utils.ExcelUtilImpl;

public class CredentialsManagmentProgram {
	
	private static String PATH_WORKBOOK;
	private static String PATH_MESSAGE_TEXT;
	public static int methodcount=0;
	
	private static final String messageTxt="MS1\r\n" + 
			"************************************\r\n" + 
			"*  1. Create New Excel File        *\r\n" + 
			"*  2. Add details in Excel File    *\r\n" + 
			"*  3. Update details               *\r\n" + 
			"*  4. Delete details               *\r\n" + 
			"*  5. Show all details             *\r\n" + 
			"*  6. Search a details             *\r\n" + 
			"*  7. Exit                         *\r\n" + 
			"************************************\r\n" + 
			"Enter Task Number\r\n" + 
			"~\r\n" + 
			"MS2\r\n" + 
			"Enter details as below\r\n" + 
			"	\r\n" + 
			"Name, Userid, password, RegNumber, AC number\r\n" + 
			"************************************************\r\n" + 
			"* *Note: Seperate the details with space       *\r\n" + 
			"*  space & put NA if details not avlaible      *\r\n" + 
			"*  ex: 'Dev' 'singh@gmail.com' '1234' 'na' 'na'*\r\n" + 
			"************************************************\r\n" + 
			"~\r\n" + 
			"MS3\r\n" + 
			"dfdsfdsfsdfsdf";
	
	public static void environmentSetup(String dir) throws IOException, InterruptedException {
		File file=new File(dir+":/cmp/data");
		if(file.exists()) {
			System.out.println("Directory Already exist");
		}else {
			Scanner sr=new Scanner(System.in);
			boolean b=file.mkdirs();	
			if(b) { 
				System.out.println("Directory created Successfully!");
				File f=new File(dir+":/cmp/data/configuration.properties");
				f.createNewFile();
				
				Thread.sleep(2000);
				System.out.println("Successfully created the configuration.properties file");
				FileWriter fw=new FileWriter(f);
				fw.write("cmp.path="+dir+":/cmp/data/workbook.xlsx");
				fw.write("\n");
				fw.write("cmp.file_path="+dir+":/cmp/data/message.txt");
				fw.close();
				
				Thread.sleep(2000);
				System.out.println("Properties successfully write in configuration.properties file");
				
				//creating message.properties file
				File messageFile=new File(dir+":/cmp/data/message.txt");
				messageFile.createNewFile();
				
				Thread.sleep(2000);
				System.out.println("Message.txt file Created successfully!");
				FileWriter messageWriter=new FileWriter(messageFile);
				messageWriter.write(messageTxt);
				messageWriter.close();
				
				Thread.sleep(2000);
				System.out.println("Message Writed Successfully!");
			}
			else
				System.out.println("Sorry Couldn't create specified directory!");
		}	
		
		loadProperties(dir+":/cmp/data/configuration.properties");
		
	}
	public static void loadProperties(String path) throws IOException {
		 FileReader reader=new FileReader(path); 
		 Properties p=new Properties(); 
		 p.load(reader);
		 PATH_WORKBOOK=p.getProperty("cmp.path");
		 PATH_MESSAGE_TEXT=p.getProperty("cmp.file_path");
		 reader.close();
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sr=new Scanner(System.in);
		if(methodcount==0) {
			System.out.print("Enter directory: ");
			String dir=sr.next();
			environmentSetup(dir.trim());
			methodcount++;
		}
		
		
		ExcelUtilImpl exutil=new ExcelUtilImpl(PATH_WORKBOOK, PATH_MESSAGE_TEXT);
		String MS1=exutil.getPrintMessage(String.valueOf(Message.MS1));
		String MS2=exutil.getPrintMessage(String.valueOf(Message.MS2));
		
		System.out.println(MS1);
		int task_id=sr.nextInt();
		TaskName task=null;
		
		switch (task_id) {
		case 1:task=TaskName.CREATE;
			break;
		case 2:task=TaskName.ADD;
			break;
		case 3:task=TaskName.UPDATE;
			break;
		case 4:task=TaskName.DELETE;
			break;	
		case 5:task=TaskName.SHOW_ALL;
			break;
		case 6:task=TaskName.SEARCH;
			break;
		case 7:task=TaskName.EXIT;
			System.out.println("You choose Exit option!");
			break;	
		default:
			break;
		}
		
		if(task.equals(TaskName.CREATE)) {
			exutil.writeExcel();
			main(args);
		}
		else if(task.equals(TaskName.ADD)) {
			System.out.println(MS2);
			
			exutil.appendExcel(sr);
			

			for(int i=0;i<5;i++) {
				System.out.print("Want to add again y/n: ");
				String confirmation=sr.next();
				if(confirmation.equals("y")) {
					exutil.appendExcel(sr);
				}else {
					i=4;
				}
			}
			main(args);
			
		}
		else if(task.equals(TaskName.SHOW_ALL)) {
			exutil.readExcel();
			main(args);
		}
		else if(task.equals(TaskName.SEARCH)) {
			System.out.print("Enter Account Name: ");String account=sr.next();
			exutil.searchDetails(account);
			System.out.println("\n");
			main(args);
		}
		else {
			System.out.println("No choice");
		}
		
		

	}
}
