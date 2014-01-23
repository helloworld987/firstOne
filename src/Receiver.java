import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Receiver implements Runnable {
	int port = 5050;
	Queue<Message> receiveQueue = null;
	Queue<Message> deliverQueue = null;
	
	public void setup(int portNumber) {
		this.port = portNumber;
		receiveQueue = new ArrayDeque<Message>();
		deliverQueue = new ArrayDeque<Message>();
		new Thread(new DeliverCheck()).start();
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket server = null;
		try {
			server = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ExecutorService exec = Executors.newCachedThreadPool();
		while (true) {
			try {
				byte[] recvBuf = new byte[512];
				DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
				server.receive(recvPacket);
				
				ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
				ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
				Object o = is.readObject();

				Message msg = (Message) o;
				System.out.print(msg.getData());
				receiveQueue.add(msg);
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
				Message msg= receiveQueue.poll();
				//check then
				
				
				deliverQueue.add(msg);
			}
		}
		
	}
}
