
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message msg = new Message ("127.0.0.1", "Ack", "123");
		MessagePasser msgPasser = new MessagePasser (null, null);
		msgPasser.send(msg);
	}

}
