import java.util.ArrayDeque;
import java.util.Queue;


public class MessagePasser {
	Sender sender = new Sender();
	
	public MessagePasser(String configuration_filename, String local_name) {
		Thread receiver= new Thread(new Receiver());
		receiver.start();
		
		new Thread(sender).start();
	}
	void send(Message message) {
		sender.addToQueue(message);
	} 
	Message receive( ) {
		return null;
	} // may block. Doesn't have to.
}