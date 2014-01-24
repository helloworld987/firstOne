import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.Queue;

public class Sender implements Runnable {
	public static Queue<Message> sendQueue = null;

	public void addToQueue(Message message) {
		sendQueue.add(message);
	}
	
	public void setup() {
		sendQueue = new ArrayDeque<Message>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (sendQueue.isEmpty()) continue;
			try {
				Message msg = sendQueue.poll();
				String destID = msg.destName;

				ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
				ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
				os.writeObject(msg);
				os.flush();
				os.close();
				byte[] sendBuf = byteStream.toByteArray();

				DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(destID), 5050);
				DatagramSocket socket = new DatagramSocket();
				socket.send(packet);
				socket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
