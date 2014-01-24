import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class Sender implements Runnable {
	Queue<Message> sendQueue = null;
	HashMap<String, Socket> socketSet = null;

	public void addToQueue(Message message) {
		sendQueue.add(message);
	}
	
	public void setup(HashMap<String, Socket> socketSetPara) {
		sendQueue = new ArrayDeque<Message>();
		socketSet = socketSetPara;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (sendQueue.isEmpty()) continue;
			try {
				Message msg = sendQueue.poll();
				String destID = msg.destName;
				
				Socket socket = null;
				if (socketSet.containsKey(destID+5050)) {
					socket = socketSet.get(destID+5050);
				} else {
					socket = new  Socket(destID, 5050);
					socketSet.put(destID+5050, socket);
				}
  
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());  
				out.writeObject(msg); 
				out.flush(); 
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
