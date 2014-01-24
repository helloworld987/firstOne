import java.io.FileNotFoundException;
import java.util.Scanner;


public class Application {
 
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		String fname;
		String processName;
		Scanner in;
		
//		System.out.println("Enter config file:");
//		in = new Scanner(System.in);
//		fname = in.next();
//		
//
//		System.out.println("Enter process name:");
//		in = new Scanner(System.in);
//		processName = in.next();
		
		Message msg = new Message ("alice", "Ack", "123");
		
		MessagePasser msgPasser = new MessagePasser ("C:\\Java_Workspace\\GitHub\\firstOne\\src\\test4.yaml", "alice");
		
		msgPasser.send(msg);
		Thread.sleep(5000);
		System.out.println(msgPasser.receive().getData());
	}

}
