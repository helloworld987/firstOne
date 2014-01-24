import java.io.FileNotFoundException;
import java.util.Scanner;


public class Application {
 
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		String fname;
		String processName;
		Scanner in;
		int choice;
		
//		System.out.println("Enter config file:");
//		in = new Scanner(System.in);
//		fname = in.next();
//		
//
		System.out.println("Enter process name:");
		in = new Scanner(System.in);
		processName = in.next();
		
		//Message msg = new Message ("bob", "Ack", "123");
		
		MessagePasser msgPasser = new MessagePasser ("/home/madhuri/DS/Lab0/firstOne/src/test4.yaml", processName);
		
		System.out.println("1.Send\n"  + "2.Receive\n");
		
		while(true) {
			System.out.println("Enter your choice::");
			in = new Scanner(System.in);
			choice = Integer.parseInt(in.next());
			
			switch(choice){
				case 1: Message msg = new Message ("bob", "Ack", "123");
						msgPasser.send(msg);
						break;
						
				case 2: Message msg1 = msgPasser.receive(); 
						if( msg1 == null)
							System.out.println("No message received..!!");
						else
							System.out.println(msg1.getData());
						break;
			}
		}
		//msgPasser.send(msg);
		//Thread.sleep(5000);
		
	}

}
