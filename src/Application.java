
public class Application {
 
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Message msg = new Message ("127.0.0.1", "Ack", "123");
		MessagePasser msgPasser = new MessagePasser (null, null);
		msgPasser.send(msg);
		Thread.sleep(5000);
		System.out.println(msgPasser.receive().getData());
	}

}
