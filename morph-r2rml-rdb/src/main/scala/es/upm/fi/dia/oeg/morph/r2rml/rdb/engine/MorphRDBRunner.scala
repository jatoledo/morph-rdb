package es.upm.fi.dia.oeg.morph.r2rml.rdb.engine

//import es.upm.fi.dia.oeg.morph.base.MorphProperties
import es.upm.fi.dia.oeg.morph.r2rml.model.R2RMLMappingDocument
import es.upm.fi.dia.oeg.morph.base.engine.MorphBaseUnfolder
import es.upm.fi.dia.oeg.morph.base.engine.MorphBaseRunner
import es.upm.fi.dia.oeg.morph.base.model.MorphBaseMappingDocument
import es.upm.fi.dia.oeg.morph.base.engine.MorphBaseDataSourceReader
import es.upm.fi.dia.oeg.morph.base.engine.MorphBaseDataTranslator
import es.upm.fi.dia.oeg.morph.base.engine.IQueryTranslator
import es.upm.fi.dia.oeg.morph.base.engine.AbstractQueryResultTranslator
import es.upm.fi.dia.oeg.morph.base.materializer.MorphBaseMaterializer
import java.io.OutputStream
import java.io.Writer
import org.slf4j.LoggerFactory
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.Logger.ROOT_LOGGER_NAME
import es.upm.fi.dia.oeg.morph.r2rml.rdb.yaml.YmlDataInterpreter
import org.apache.commons.cli._
import java.io.File;
import org.apache.commons.cli.HelpFormatter



class MorphRDBRunner(mappingDocument:R2RMLMappingDocument
		, unfolder:MorphRDBUnfolder
		, dataTranslator:scala.Option[MorphRDBDataTranslator]
				, queryTranslator:scala.Option[IQueryTranslator]
						, resultProcessor:scala.Option[AbstractQueryResultTranslator]
								, outputStream:Writer
		) extends MorphBaseRunner(mappingDocument
				, unfolder
				, dataTranslator
				, queryTranslator
				, resultProcessor
				, outputStream
				) {


}

object MorphRDBRunner {
	val logger = LoggerFactory.getLogger(MorphRDBRunner.getClass());



	//	/**
	//	 * Contruct Cli Options 
	//	 */
	//	def apply(properties:MorphRDBProperties,mappingFile:String ) : MorphRDBRunner = {
	//
	//			val runnerFactory = new MorphRDBRunnerFactory();
	//			val runner = runnerFactory.createRunner(properties,mappingFile);
	//			runner.asInstanceOf[MorphRDBRunner];
	//	}
	//	def apply(properties:MorphRDBProperties ) : MorphRDBRunner = {
	//
	//			val runnerFactory = new MorphRDBRunnerFactory();
	//			val runner = runnerFactory.createRunner(properties);
	//			runner.asInstanceOf[MorphRDBRunner];
	//	}
	//
	//	def apply(configurationDirectory:String , configurationFile:String ) : MorphRDBRunner = {
	//
	//
	//			val configurationProperties = MorphRDBProperties.apply(configurationDirectory, configurationFile);
	//			val runner = MorphRDBRunner(configurationProperties)
	//					runner
	//	}
	/**
	 * Construc MorphRDBRunner with CLI option
	 */
	def apply(configurationDirectory:String , configurationFile:String ,mappingFile:String, query:String, outputFile:String) : MorphRDBRunner = {


			val configurationProperties = MorphRDBProperties.apply(configurationDirectory, configurationFile);
			val runnerFactory = new MorphRDBRunnerFactory();
			val runner = runnerFactory.createRunner(configurationProperties,mappingFile,query, outputFile);
			runner.asInstanceOf[MorphRDBRunner];
	}

	/**
	 * Morph-RDB: Main CLI
	 */
	def main(args:Array[String]) {


		var propertiesFile="";
		var mappingFile ="";
		var outputFile ="";
		var query="";
		
		val options = new Options();

		val propertiesOption = Option.builder("p")
				.longOpt("properties")
				.desc("Properties file")
				.hasArg()
				.build();
		val mappingdocOption = Option.builder("m")
				.longOpt("mappingfile")
				.hasArg()
				.desc("mapping file paths ")
				.build();
		val outputfileOption = Option.builder("o")
				.longOpt("outputfile")
				.hasArg()
				.desc("path to output file (default: stdout)")
				.build();
		val queryOption = Option.builder("q")
				.longOpt("query")
				.hasArg()
				.desc("Query file")
				.build();
		options.addOption(mappingdocOption);
		options.addOption(outputfileOption);
		options.addOption(propertiesOption);
		options.addOption(queryOption);
		val   parser = new DefaultParser();

		try {
			val lineArgs = parser.parse(options, args);

			if (lineArgs.hasOption("p")) { //Properties
				propertiesFile =lineArgs.getOptionValue("p");
			}
			if(lineArgs.hasOption("m")) {  // Mapping
				mappingFile =System.getProperty("user.dir")+File.separator+lineArgs.getOptionValue("m");
			}
			if(lineArgs.hasOption("q")) {  // query
				query =System.getProperty("user.dir")+File.separator+lineArgs.getOptionValue("q");
			}
			if(lineArgs.hasOption("o")) {  // Output File
				outputFile =System.getProperty("user.dir")+File.separator+lineArgs.getOptionValue("o");
			}

			logger.debug("propertiesFile: "+System.getProperty("user.dir")+File.separator+propertiesFile);
			logger.debug("mappingFile: "+System.getProperty("user.dir")+File.separator+mappingFile);
			logger.info(query);
			logger.info(outputFile);

			val configurationDirectory=System.getProperty("user.dir")
					val configurationFile = propertiesFile;
			val runner = MorphRDBRunner(configurationDirectory, configurationFile, mappingFile, query, outputFile );

			runner.run();
		} catch {

		case one: java.io.FileNotFoundException => {

			val formatter = new HelpFormatter();
			formatter.printHelp("java -jar morph.jar <options>\noptions:", options);
			//logger.error("Exception occured: " + one.getMessage());

			System.exit(1);
		}

		case e:Exception => {

//			val formatter = new HelpFormatter();
//			formatter.printHelp("java -jar morph.jar <options>\noptions:", options);
			logger.error("Exception occured: " + e.getMessage());
			logger.error("Exception occured: " + e);

			System.exit(1);
			//e.printStackTrace();
			//logger.error("✌");

			//throw e;
		}//catch


		}




	}//main


}//MorphRDBRunner


