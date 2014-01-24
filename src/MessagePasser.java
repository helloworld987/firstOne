import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

public class MessagePasser {
	Sender sender = null;
	Receiver receiver = null;
	String localName = null;
	int seqNum = 0;
	
	public static Parser parser = new Parser();
	public static long lastModified;
	public static String conf_filename ;
	
	public MessagePasser(String configuration_filename, String local_name) throws FileNotFoundException, IllegalArgumentException  {
	
		//Check lastModified date of file
		File file = new File(configuration_filename);
		lastModified = file.lastModified();
		
		this.conf_filename = configuration_filename;
		
		parser.parseConfig(configuration_filename);
		int port = 0; 
		
		if (parser.config.get(local_name) == null) {
			System.out.println("Node not present in config file..!");
			throw new IllegalArgumentException();
			
		}else {
			port = Integer.parseInt(parser.config.get(local_name).get(1));
		}
		
		localName = local_name;
		
		receiver = new Receiver();
		receiver.setup(port);
		new Thread(receiver).start();
		
		sender = new Sender();
		sender.setup();
	}
	
	void send(Message message) throws FileNotFoundException {
		message.set_source(localName);
		message.set_seqNum(seqNum++);
		
		Rules sendrules = new Rules();
		sendrules.checkSendRules(message);
			
		sender.send();
	} 

	Message receive() {
		//If there is delay, msg = null
		if(Rules.recv_delay_flag)
			return null;
		return receiver.getMessage();
	} // may block. Doesn't have to.
}