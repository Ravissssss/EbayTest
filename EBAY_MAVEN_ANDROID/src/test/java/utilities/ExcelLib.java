package utilities;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelLib
{
	HSSFSheet ExcelWSheet;
	HSSFWorkbook ExcelWBook;
	org.apache.poi.ss.usermodel.Cell Cell;
	HSSFRow Row;
	HashMap<String, Integer> dict = new HashMap<String, Integer>();

	public void setExcelFile(String Path) throws Exception 
	{
		try 
		{
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new HSSFWorkbook(ExcelFile);
			
		} 
		catch (Exception e)
		{
			Log.error("Class ExcelLib | Method setExcelFile | Exception desc : "+e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public String getCellData(int RowNum, int ColNum, String SheetName) throws Exception
	{
		try
		{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum,Row.CREATE_NULL_AS_BLANK);
			String CellData=null;
			Cell.setCellType(Cell.CELL_TYPE_STRING);
			CellData = Cell.getStringCellValue();
			return CellData;
		}
		catch (Exception e)
		{
			Log.error("Class ExcelLib  | Method getCellData | Exception desc : "+e.getMessage());
			e.printStackTrace();
			return"";
		}
	}

	public  int getRowCount(String SheetName)
	{
		int iNumber=0;
		try 
		{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber=ExcelWSheet.getLastRowNum()+1;
		} 
		catch (Exception e)
		{
			Log.error("Class ExcelLib  | Method getRowCount | Exception desc : "+e.getMessage());
			e.printStackTrace();
		}
		return iNumber;
	}

	public void ColumnDictionary(String SheetName) throws Exception
	{
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		for(int col=0;col< ExcelWSheet.getRow(0).getLastCellNum();col++)
		{
			dict.put(getCellData(0,col,SheetName), col);			
		}
	}

	public String getCellData(String colName, int rowNumber,String SheetName) throws Exception
	{
		ColumnDictionary(SheetName);
		return getCellData(rowNumber,GetCellIndex(colName),SheetName);
	}

	public int GetCellIndex(String colName,String SheetName) throws Exception
	{
		try 
		{
			int value;
			ColumnDictionary(SheetName);
			value = ((Integer) dict.get(colName)).intValue();
			return value;
		} 
		catch (NullPointerException e) 
		{
			return (0);
		}
	}

	public int GetCellIndex(String colName)
	{
		try 
		{
			int value;
			value = ((Integer) dict.get(colName)).intValue();
			return value;
		} 
		catch (NullPointerException e) 
		{
			return (0);
		}
	}

	public int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception
	{
		int iRowNum=0;	
		try 
		{
			int rowCount = getRowCount(SheetName);
			for (; iRowNum<rowCount; iRowNum++)
			{
				if(getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName))
				{
					break;
				}
			}       			
		} 
		catch (Exception e)
		{
			Log.error("Class ExcelLib  | Method getRowContains | Exception desc : "+e.getMessage());
		}
		return iRowNum;
	}

	public  int getTestCaseCount(String SheetName, String sTestScenID, int iTestCaseStart) throws Exception
	{
		try 
		{
			for(int i=iTestCaseStart;i<getRowCount(SheetName);i++)
			{
				if(!sTestScenID.equals(getCellData(i,0, SheetName)))
				{
					int number = i;
					return number;      				
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number=ExcelWSheet.getLastRowNum()+1;
			return number;
		} 
		catch (Exception e)
		{
			 Log.error("Class ExcelLib  | Method getRowContains | Exception desc : "+e.getMessage());
			return 0;
		}
	}

	public Object[][] getTestcases(String Scenario_name) throws Exception
	{
		int ci=0;
		int startCaseRow= getRowContains(Scenario_name, GetCellIndex("Test Scenario ID","Test Cases"),"Test Cases");
		int lastCaseRow= getTestCaseCount("Test Cases", Scenario_name, startCaseRow);		
		ArrayList<String> al = new ArrayList<String>();
		for (;startCaseRow<lastCaseRow;startCaseRow++)
		{
			if(getCellData(startCaseRow, GetCellIndex("Runmode","Test Cases"),"Test Cases").equals("Yes"))
			{
				al.add(getCellData(startCaseRow, GetCellIndex("Test Case","Test Cases"),"Test Cases"));
			}
		}
		String[][] tabArray = new String[al.size()][1] ;
		for (String testcase : al) 
		{
			tabArray[ci][0]=testcase;
			ci++;
		}
		return tabArray;
	}

	public HashMap<String, String> getDatafortc(String TCtype,String SheetName) throws Exception 
	{
		HashMap<String, String> dataset = new HashMap<String, String>();
		int tcrownum=getRowContains(TCtype, GetCellIndex("Test Case",SheetName), SheetName);
		for(int colnum=1;colnum<ExcelWSheet.getRow(tcrownum).getLastCellNum();colnum++)
		{
			dataset.put(getCellData(0, colnum,SheetName), getCellData(tcrownum, colnum,SheetName));
		}
		return dataset;
	}

}