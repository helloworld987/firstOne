import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;


public class Parser {


	public static Map<String, List<String>> config = new HashMap<String, List<String>>();
	//public static Map<String, List<Object>> sendRules = new HashMap<String, List<Object>>();
	//public static Map<String, List<Object>> receiveRules = new HashMap<String, List<Object>>();
	
	/* Store different sendRules acording to actions */
	public static List<Object> sendAction_drop = new ArrayList<Object>();
	public static List<Object> sendAction_duplicate = new ArrayList<Object>();
	public static List<Object> sendAction_delay = new ArrayList<Object>();	
	
	/* Store different receiveRules acording to actions */
	public static List<Object> receiveAction_drop = new ArrayList<Object>();
	public static List<Object> receiveAction_duplicate = new ArrayList<Object>();
	public static List<Object> receiveAction_delay = new ArrayList<Object>();	
	
	public Parser() {
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
		if(res == null)
			return;
		length = res.size();
		
		
		while(i != length) {
			Map<String,Object> sendrules = (Map<String,Object>) res.get(i);
			if (sendrules.get("action").equals("drop")){
				sendAction_drop.add(sendrules);
			}else if (sendrules.get("action").equals("delay")){
				sendAction_delay.add(sendrules);
			}else if (sendrules.get("action").equals("duplicate")){
				sendAction_duplicate.add(sendrules);
			}
			/*
			List <Object> rules = new ArrayList<Object>();
						
			rules.add(sendrules.get("src"));
			rules.add(sendrules.get("dest"));
			rules.add(sendrules.get("kind"));
			rules.add(sendrules.get("seqNum"));
			
			sendRules.put((String)sendrules.get("action"), rules);
			*/
			i++;
		}
	
	}
	
	public void get_receiveRules(Map<String,Object> result) {
		
		int length, i=0;
		
		List<Object> res = (List<Object>)result.get("receiveRules");
		
		if(res == null)
			return;
		
		length = res.size();
		
		
		while(i != length) {
			Map<String,Object> receiverules = (Map<String,Object>) res.get(i);
			
			if (receiverules.get("action").equals("drop")){
				receiveAction_drop.add(receiverules);
			}else if (receiverules.get("action").equals("delay")){
				receiveAction_delay.add(receiverules);
			}else if (receiverules.get("action").equals("duplicate")){
				receiveAction_duplicate.add(receiverules);
			}
			
			/*
			List <Object> rules = new ArrayList<Object>();
						
			rules.add(receiverules.get("src"));
			rules.add(receiverules.get("seqNum"));
			
			receiveRules.put((String)receiverules.get("action"), rules);
			*/
			i++;
		}
		//System.out.println(receiveAction_delay.get(0));
	}

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
public void parseConfig(String fname) throws FileNotFoundException {
		
		//Clear all lists
		config.clear();
		
		sendAction_drop.clear();
		sendAction_delay.clear();
		sendAction_duplicate.clear();
		
		receiveAction_delay.clear();
		receiveAction_drop.clear();
		receiveAction_duplicate.clear();
		
		Yaml yaml = new Yaml();
		InputStream ios = new FileInputStream(new File(fname));
	
		Map<String,Object> result = (Map<String,Object>)yaml.load(ios);
		
		get_config(result);
		get_sendRules(result);
		get_receiveRules(result);
		
	}


}
