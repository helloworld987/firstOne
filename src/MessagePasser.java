import java.util.ArrayDeque;
import java.util.Queue;


public class MessagePasser {
	Sender sender = null;
	Receiver receiver = null;
	String localName = null;
	int seqNum = 0;
	
	
	public MessagePasser(String configuration_filename, String local_name) {
		localName = local_name;
		
		receiver = new Receiver();
		receiver.setup(5050);
		new Thread(receiver).start();
		
		sender = new Sender();
		sender.setup();
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
		return receiver.getMessage();
	} // may block. Doesn't have to.
}