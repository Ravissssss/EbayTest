package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FailCase 
{	
	public static void failresult(String failfilepath, String tc_details, String err) throws IOException
	{	
		DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		Writer output;
		output = new BufferedWriter(new FileWriter(failfilepath,true));
		output.append("\n"+tc_details+"\t Timestamp: "+dateformat.format(cal.getTime())+"\nReason: "+err+"\n");
		output.close();
	}
}