import java.util.Scanner;


public class Application {
 
	public static void main(String[] args) throws InterruptedException {
		String fname;
		String processName;
		Scanner in;
		
		System.out.println("Enter config file:");
		in = new Scanner(System.in);
		fname = in.next();
		

		System.out.println("Enter process name:");
		in = new Scanner(System.in);
		processName = in.next();
		
		Message msg = new Message ("bob", "Ack", "123");
		
		MessagePasser msgPasser = new MessagePasser (fname, processName);
		
		msgPasser.send(msg);
		Thread.sleep(5000);
		System.out.println(msgPasser.receive().getData());
	}

}
