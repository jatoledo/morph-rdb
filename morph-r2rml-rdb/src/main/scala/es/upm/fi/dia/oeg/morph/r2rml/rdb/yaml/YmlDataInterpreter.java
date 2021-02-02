package es.upm.fi.dia.oeg.morph.r2rml.rdb.yaml;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class YmlDataInterpreter {

	public YmlStructure interpretYaml() {
		
		InputStream stream = this.getClass().getClassLoader()
				.getResourceAsStream("database.yml");

		Yaml yaml = new Yaml();
		YmlStructure result = yaml.loadAs(stream, YmlStructure.class);
		return result;
	}

}
