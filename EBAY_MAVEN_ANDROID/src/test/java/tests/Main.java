package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import utilities.ExcelLib;
import utilities.Log;

public class Main 
{
	public static void main(String[] args) throws Exception 
	{	
		
		ExcelLib Scenarios_data = new ExcelLib();
		Scenarios_data.setExcelFile("DataEngine/000_Setup.xls");		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		FileUtils.deleteDirectory(new File("screenshots"));
		FileUtils.write(new File("FailCases.txt"),"");
		Log.info("Creation of testng xml file started");

		Element suite = doc.createElement("suite");
		doc.appendChild(suite);
		suite.setAttribute("configfailurepolicy","continue");
		suite.setAttribute("name","Mobile Regression Test Suite");
		suite.setAttribute("verbose","2");
		
		Element listeners = doc.createElement("listeners");
		suite.appendChild(listeners);
		
		Element listener = doc.createElement("listener");
		listeners.appendChild(listener);
		listener.setAttribute("class-name", "utilities.CustomReportTest");
		
		
		Element test = doc.createElement("test");
		suite.appendChild(test);
		test.setAttribute("name","Functional test");

		Log.info("Reading file");

		Set<String> modules = new LinkedHashSet<String>();
		HashMap<String, String> params = new LinkedHashMap<String, String>();

		for(int i=1;i<Scenarios_data.getRowCount("Setup");i++)
		{
			params.put(Scenarios_data.getCellData(i, Scenarios_data.GetCellIndex("Parameter","Setup"),"Setup"),Scenarios_data.getCellData(i, Scenarios_data.GetCellIndex("Value","Setup"),"Setup"));
		}		

		for (Map.Entry<String, String> entry : params.entrySet()) 
		{

			Element parameters=doc.createElement("parameter");
			test.appendChild(parameters);
			parameters.setAttribute("name",entry.getKey());
			parameters.setAttribute("value",entry.getValue());

		}

		Element classes = doc.createElement("classes");
		test.appendChild(classes);

		for(int i=1;i<Scenarios_data.getRowCount("Test Scenarios");i++)
		{
			modules.add(Scenarios_data.getCellData(i, Scenarios_data.GetCellIndex("Module","Test Scenarios"),"Test Scenarios"));
		}

		int startModuleRow;
		int lastModuleRow;
		for (String module : modules) 
		{
			Element class_ = doc.createElement("class");
			Element methods = doc.createElement("methods");

			startModuleRow=0;
			lastModuleRow=0;

			startModuleRow = Scenarios_data.getRowContains(module, Scenarios_data.GetCellIndex("Module","Test Scenarios"),"Test Scenarios");
			lastModuleRow = Scenarios_data.getTestCaseCount("Test Scenarios", module, startModuleRow);

			for (;startModuleRow<lastModuleRow;startModuleRow++)
			{
				if(Scenarios_data.getCellData(startModuleRow, Scenarios_data.GetCellIndex("Runmode","Test Scenarios"),"Test Scenarios").equalsIgnoreCase("Yes"))
				{
					classes.appendChild(class_);
					class_.setAttribute("name", Scenarios_data.getCellData(startModuleRow, Scenarios_data.GetCellIndex("Classname","Test Scenarios"), "Test Scenarios"));
					break;
				}
			}

			if(startModuleRow<lastModuleRow)
			{
				class_.appendChild(methods);
				for(;startModuleRow<lastModuleRow;startModuleRow++)
				{
					if(Scenarios_data.getCellData(startModuleRow, Scenarios_data.GetCellIndex("Runmode","Test Scenarios"), "Test Scenarios").equalsIgnoreCase("Yes"))
					{
						Element include = doc.createElement("include");
						methods.appendChild(include);
						include.setAttribute("name",Scenarios_data.getCellData(startModuleRow, Scenarios_data.GetCellIndex("Test Scenario ID","Test Scenarios"), "Test Scenarios"));
					}
				}
			}
		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("testNg.xml"));

		transformer.transform(source, result);

		Log.info("Creation of testng xml file Completed");

		Log.info("Test Case execution started");

		List<String> suitesList = new ArrayList<String>();
		List<Class> listnerClasses = new ArrayList<Class>();

		TestListenerAdapter tla = new TestListenerAdapter();
		listnerClasses.add(utilities.CustomReport.class);

		TestNG testng = new TestNG();

		suitesList.add("testNg.xml");
		
		testng.setTestSuites(suitesList);
		testng.setListenerClasses(listnerClasses);
		testng.addListener(tla);
		
		testng.run();
		
		Log.info("Test Case execution Completed");
		
		System.exit(0);
		
		
	}

}