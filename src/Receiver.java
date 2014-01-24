import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Receiver implements Runnable {
	int port = 5050;
	public static Queue<Message> receiveQueue = null;
	private Semaphore mutex = new Semaphore(1);

	Rules recvRules = new Rules();

	HashSet<Socket> recSockets = null;
	
	
	private void addSocket(Socket socket){
		try {
			mutex.acquire();
			recSockets.add(socket);
			mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			mutex.release();
		}
	}
	
	private HashSet<Socket> copySockets(){
		try {
			mutex.acquire();
			HashSet<Socket> copy = new HashSet<Socket>(recSockets);
			mutex.release();
			return copy;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			mutex.release();
			return null;
		}
	}

	public void setup(int portNumber) {

		this.port = portNumber;
		receiveQueue = new ArrayDeque<Message>();
		recSockets = new HashSet<Socket>();
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
				addSocket(cltSocket);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Message getMessage() {
		// TODO Auto-generated method stub
		return receiveQueue.poll();
	}
	
	private class receiveContent implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				HashSet<Socket> copySockets = copySockets();
				for (Socket socket: copySockets) {
					try {
						ObjectInputStream  in = new ObjectInputStream(socket.getInputStream()); 
						Message data = (Message)in.readObject();
						recvRules.checkReceiveRules(data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}  
				}
			}
		}
		
	}
}
