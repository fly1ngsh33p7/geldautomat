package test.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.DataImportHelper;

public class DataImportHelperTest {
	File file;
	List<List<String>> data;
	
	@Before
	public void setUp() {
		file = new File("src/BankCSV_etwurf.csv");
	}
	
	@Test
	public void testGetDataByColumn() {
		data = DataImportHelper.getDataByColumn(file);
		
		assertEquals(data.get(0).get(0), "VR Bank Rhein-Neckar");	
		assertEquals(data.get(1).get(0), "MA2424");
		assertEquals(data.get(2).get(0), "4711");
		assertEquals(data.get(3).get(0), "1234");
		assertEquals(data.get(4).get(0), "50,14 €");
		assertEquals(data.get(5).get(0), "Sparkonto");
		assertEquals(data.get(6).get(0), "3%");
		assertEquals(data.get(7).get(0), "");
		assertEquals(data.get(8).get(0), "123456");
		assertEquals(data.get(9).get(0), "Mustermann");
		assertEquals(data.get(10).get(0), "Max");
		assertEquals(data.get(11).get(0), "Bahnhofstraße 1");
		assertEquals(data.get(12).get(0), "68159");
		assertEquals(data.get(13).get(0), "Mannheim");
	}
}
