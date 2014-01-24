import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class Sender {

	public static Queue<Message> sendQueue = null;
	HashMap<String, Socket> socketSet = null;


	public void addToQueue(Message message) {
		sendQueue.add(message);
	}
	
	public void setup(HashMap<String, Socket> socketSetPara) {
		sendQueue = new ArrayDeque<Message>();
		socketSet = socketSetPara;
	}
	public void send(){
		try {
			if(Rules.send_delay_flag)
				return;
			while(!sendQueue.isEmpty()) {
			Message msg = sendQueue.remove();
		
			String destID = msg.destName;
			
			Socket socket = null;
			if (socketSet.containsKey(destID)) {
				socket = socketSet.get(destID);
			} else {
				String ipAddr = parser.config.get(msg.destName).get(0);
				int port = Integer.parseInt(parser.config.get(msg.destName).get(1));
				socket = new Socket(ipAddr, port);
				socketSet.put(destID, socket);
			}

			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					
			out.writeObject(msg);
			out.flush();
			out.close();
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
