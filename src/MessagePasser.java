import java.io.FileNotFoundException;

public class MessagePasser {
	Sender sender = null;
	Receiver receiver = null;
	String localName = null;
	int seqNum = 0;
	
	parser parser = new parser();
	
	public MessagePasser(String configuration_filename, String local_name) throws FileNotFoundException {
		parser.parseConfig(configuration_filename);
		int port = Integer.parseInt(parser.config.get(local_name).get(1));
		
		localName = local_name;
		
		receiver = new Receiver();
		receiver.setup(port);
		new Thread(receiver).start();
		
		sender = new Sender();
		sender.setup();
	}
	
	void send(Message message) {
		message.set_source(localName);
		message.set_seqNum(seqNum++);
		
		Rules sendrules = new Rules();
		sendrules.checkSendRules(message);
		//sender.addToQueue(message);
		sender.send();
	} 

	Message receive() {
		//If there is delay, msg = null
		if(Rules.recv_delay_flag)
			return null;
		return receiver.getMessage();
	} // may block. Doesn't have to.
}