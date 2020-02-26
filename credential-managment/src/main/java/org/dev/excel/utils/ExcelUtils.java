package org.dev.excel.utils;

import java.io.IOException;
import java.util.Scanner;

public interface ExcelUtils {
	final String SHEET_NAME="login_details";
	final Object[] COLUMN_HEADER= {"Sr","Name","Userid","password","RegNumber","AC number"};
	
	public void writeExcel()throws IOException;
	public void appendExcel(Scanner sr)throws IOException;
	public int getLastSerialNum(String PATH)throws IOException;
	public String getPrintMessage(String message_no)throws IOException;
	public void readExcel()throws IOException;
	public void searchDetails(String account_name)throws IOException;
}
