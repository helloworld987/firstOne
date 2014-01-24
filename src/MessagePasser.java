import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.HashMap;

public class MessagePasser {
	Sender sender = null;
	Receiver receiver = null;
	String localName = null;
	int seqNum = 0;
	HashMap<String, Socket> socketSet = null;
	parser parser = new parser();
	
	public MessagePasser(String configuration_filename, String local_name) throws FileNotFoundException {
		parser.parseConfig(configuration_filename);
		int port = Integer.parseInt(parser.config.get(local_name).get(1));
		
		localName = local_name;
		socketSet = new HashMap<String, Socket>();
		
		receiver = new Receiver();
		receiver.setup(port, socketSet);
		new Thread(receiver).start();
		
		sender = new Sender();
		sender.setup(socketSet);
		new Thread(sender).start();
	}
	
	void send(Message message) {
		message.set_source(localName);
		message.set_seqNum(seqNum++);
		
		Rules sendrules = new Rules();
		sendrules.checkSendRules(message);
		//sender.addToQueue(message);
	} 

	Message receive() {
		//If there is delay, msg = null
		if(Rules.recv_delay_flag)
			return null;
		return receiver.getMessage();
	} // may block. Doesn't have to.
}