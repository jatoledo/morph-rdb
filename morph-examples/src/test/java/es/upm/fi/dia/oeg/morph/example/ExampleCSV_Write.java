package es.upm.fi.dia.oeg.morph.example;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.fi.dia.oeg.morph.base.engine.MorphBaseRunner;
import es.upm.fi.dia.oeg.morph.r2rml.rdb.engine.MorphRDBRunnerFactory;

public class ExampleCSV_Write {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//private static final Logger logger = LoggerFactory.getLogger(Main.class);
	 

	public static void main(String[] args) {
		
		Options options = new Options();
		
		
		String configurationDirectory = System.getProperty("user.dir") + File.separator + "examples-mysql";
		String configurationFile = "example1-batch-mysql.morph.properties";
	
		try {
			MorphRDBRunnerFactory runnerFactory = new MorphRDBRunnerFactory();
			MorphBaseRunner runner = runnerFactory.createRunner(configurationDirectory, configurationFile);
			
			
			runner.run();
			//logger.info("Batch process DONE------\n\n");
		} catch (Exception e) {
			e.printStackTrace();
			//logger.info("Batch process FAILED------\n\n");
			assertTrue(e.getMessage(), false);
		}
		
		
	}//main
	
	
}//ExampleCSV_Write
