package es.upm.fi.dia.oeg.morph.example;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.fi.dia.oeg.morph.base.engine.MorphBaseRunner;
import es.upm.fi.dia.oeg.morph.r2rml.rdb.engine.MorphRDBRunnerFactory;

public class Example_cmdb {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Options options = new Options();


		String configurationDirectory = System.getProperty("user.dir") + File.separator + "examples-cmdb";
		String configurationFile = "example1-batch-cmdb.properties";

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
	}

}
