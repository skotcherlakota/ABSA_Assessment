package utilities;

import java.io.FileReader;
import java.util.Properties;

public class ReadPropertiesFile {
	public String getValuFromPropertiesFile(String key) {
		try {
			FileReader reader=new FileReader("config.properties");
			Properties p=new Properties();  
			p.load(reader);
			return p.getProperty(key);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
