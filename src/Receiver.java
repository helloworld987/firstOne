import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class Receiver implements Runnable {
	int port = 5050;
	Queue<Message> receiveQueue = null;
	Queue<Message> deliverQueue = null;
	HashMap<String, Socket> socketSet = null;

	public void setup(int portNumber, HashMap<String, Socket> socketSetPara) {
		this.port = portNumber;
		receiveQueue = new ArrayDeque<Message>();
		deliverQueue = new ArrayDeque<Message>();
		socketSet = socketSetPara;
		new Thread(new DeliverCheck()).start();
		new Thread(new receiveContent()).start();
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket servSock = null;
		try {
			servSock = new ServerSocket(port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ExecutorService exec = Executors.newCachedThreadPool();
		while (true) {
			try {
				Socket cltSocket = servSock.accept();
				int remotePort = cltSocket.getPort();
				InetAddress remoteAddr = cltSocket.getInetAddress();
				String key = remoteAddr.toString() + remotePort;
				socketSet.put(key, cltSocket);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Message getMessage() {
		// TODO Auto-generated method stub
		return deliverQueue.poll();
	}
	
	private class receiveContent implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				for (Socket socket: socketSet.values()) {
					try {
						ObjectInputStream  in = new ObjectInputStream(socket.getInputStream()); 
						Message data = (Message)in.readObject();
						receiveQueue.add(data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}  
				}
			}
		}
		
	}

	private class DeliverCheck implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				if (receiveQueue.isEmpty()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}
				Message msg = receiveQueue.poll();
				// check then
				deliverQueue.add(msg);
			}
		}

	}
}
