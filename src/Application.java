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
		MessagePasser msgPasser=null;
		
		System.out.println("Enter config file:");
		in = new Scanner(System.in);
		fname = in.next();

		System.out.println("Enter process name:");
		in = new Scanner(System.in);
		processName = in.nextLine();

		try{
			msgPasser = new MessagePasser (fname, processName);
		}catch (IllegalArgumentException e){
			return;
		}
		
		System.out.println("1.Send\n"  + "2.Receive\n");
		
		while(true) {
			System.out.println("Enter your choice::");
			in = new Scanner(System.in);
			try{
				choice = Integer.parseInt(in.nextLine());
			} catch (Exception e){
				System.out.println("Input is wrong!");
				continue;
			}
			
			
			switch(choice){
			
				case 1: 
						System.out.println("Peer Name-");
						in = new Scanner(System.in);
						peerName = in.nextLine();
						
						System.out.println("Kind -");
						in = new Scanner(System.in);
						kind = in.nextLine();
						
						System.out.println("Message -");
						in = new Scanner(System.in);
						message = in.nextLine();
						
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
				default:
					
			}
		}
	
	}

}
