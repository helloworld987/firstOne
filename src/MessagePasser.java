import java.net.Socket;
import java.util.HashMap;

public class MessagePasser {
	Sender sender = null;
	Receiver receiver = null;
	String localName = null;
	int seqNum = 0;
	HashMap<String, Socket> socketSet = null;
	
	public MessagePasser(String configuration_filename, String local_name) {
		localName = local_name;
		socketSet = new HashMap<String, Socket>();
		
		receiver = new Receiver();
		receiver.setup(5050, socketSet);
		new Thread(receiver).start();
		
		sender = new Sender();
		sender.setup(socketSet);
		new Thread(sender).start();
	}
	
	void send(Message message) {
		message.set_source(localName);
		message.set_seqNum(seqNum++);
		
		sender.addToQueue(message);
	}
	
	Message receive() {
		return receiver.getMessage();
	} // may block. Doesn't have to.
}