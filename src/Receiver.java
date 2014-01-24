import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class Receiver implements Runnable {
	int port = 5050;
	public static Queue<Message> receiveQueue = null;
	Queue<Message> deliverQueue = null;
	HashSet<Socket> recSockets = null;

	public void setup(int portNumber) {
		this.port = portNumber;
		receiveQueue = new ArrayDeque<Message>();
		deliverQueue = new ArrayDeque<Message>();
		recSockets = new HashSet<Socket>();
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
				recSockets.add(cltSocket);
				
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
				for (Socket socket: recSockets) {
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
