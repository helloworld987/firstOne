import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) throws InterruptedException,
			FileNotFoundException {
		String fname;
		String processName;
		Scanner in;
		int choice;

		String peerName;
		String message;
		String kind;
		
		System.out.println("Enter config file:");
		in = new Scanner(System.in);
		fname = in.next();

		System.out.println("Enter process name:");
		in = new Scanner(System.in);
		processName = in.next();
		

		MessagePasser msgPasser = new MessagePasser (fname, processName);

		
		System.out.println("1.Send\n"  + "2.Receive\n");
		
		while(true) {
			System.out.println("Enter your choice::");
			in = new Scanner(System.in);
			choice = Integer.parseInt(in.next());
			
			switch(choice){
			
				case 1: 
						System.out.println("Peer Name-");
						in = new Scanner(System.in);
						peerName = in.next();
						
						System.out.println("Kind -");
						in = new Scanner(System.in);
						kind = in.next();
						
						System.out.println("Message -");
						in = new Scanner(System.in);
						message = in.next();
						
						Message msg = new Message (peerName, kind, message);
						msgPasser.send(msg);
						break;
						
				case 2: Message msg1 = msgPasser.receive(); 
						if( msg1 == null)
							System.out.println("No message received..!!");
						else {
							System.out.println("Source of Message -" + msg1.sourceName);					
							System.out.println(msg1.getData());
						}
						break;
			}
		}
		// msgPasser.send(msg);
		// Thread.sleep(5000);

	}

}
