import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;


public class parser {


	public static Map<String, List<String>> config = new HashMap<String, List<String>>();
	public static Map<String, List<Object>> sendRules = new HashMap<String, List<Object>>();
	public static Map<String, List<Object>> receiveRules = new HashMap<String, List<Object>>();
	
	
	public parser() {
		// TODO Auto-generated constructor stub
	}
	
	public void get_config(Map<String,Object> result) {
		
		int length, i=0;
		
		List<Object> res = (List<Object>)result.get("configuration");
		length = res.size();
		
		while(i != length) {
			Map<String,Object> mapping = (Map<String,Object>) res.get(i);
			List <String> address = new ArrayList<String>();
						
			address.add(mapping.get("ip").toString());
			address.add(mapping.get("port").toString());
			
			config.put(mapping.get("name").toString(), address);
			
			i++;
		}
		//System.out.println(config.get("daphnie"));
	}
	
	public void get_sendRules(Map<String,Object> result) {
		
		int length, i=0;
		
		List<Object> res = (List<Object>)result.get("sendRules");
		length = res.size();
		
		while(i != length) {
			Map<String,Object> sendrules = (Map<String,Object>) res.get(i);
			List <Object> rules = new ArrayList<Object>();
						
			rules.add(sendrules.get("src"));
			rules.add(sendrules.get("dest"));
			rules.add(sendrules.get("kind"));
			rules.add(sendrules.get("seqNum"));
			
			sendRules.put((String)sendrules.get("action"), rules);
			
			i++;
		}
		//System.out.println(sendRules.get("delay"));
	}
	
	public void get_receiveRules(Map<String,Object> result) {
		
		int length, i=0;
		
		List<Object> res = (List<Object>)result.get("receiveRules");
		length = res.size();
		
		while(i != length) {
			Map<String,Object> receiverules = (Map<String,Object>) res.get(i);
			List <Object> rules = new ArrayList<Object>();
						
			rules.add(receiverules.get("src"));
			rules.add(receiverules.get("seqNum"));
			
			receiveRules.put((String)receiverules.get("action"), rules);
			
			i++;
		}
		//System.out.println(receiveRules.get("duplicate"));
	}

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		parser parse = new parser();
		//datatype conf = new datatype();
		
		Yaml yaml = new Yaml();
		InputStream ios = new FileInputStream(new File("/home/madhuri/workspace/Lab0_DS/src/test3.yaml"));
	
		Map<String,Object> result = (Map<String,Object>)yaml.load(ios);
    		
		//System.out.println(result.get("sendRules"));
		
		parse.get_config(result);
		parse.get_sendRules(result);
		parse.get_receiveRules(result);
		
	}


}
