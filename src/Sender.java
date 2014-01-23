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
	Queue<Message> sendQueue = new ArrayDeque<Message>();

	public void addToQueue(Message message) {
		sendQueue.add(message);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!sendQueue.isEmpty()) {
			try {
				Message msg = sendQueue.poll();
				String destID = msg.dest;

				ByteArrayOutputStream byteStream = new ByteArrayOutputStream(
						500);
				ObjectOutputStream os;

				os = new ObjectOutputStream(
						new BufferedOutputStream(byteStream));

				os.writeObject(msg);

				os.flush();

				byte[] sendBuf = byteStream.toByteArray();

				DatagramPacket packet = new DatagramPacket(sendBuf,
						sendBuf.length, InetAddress.getByName(destID), 5050);

				DatagramSocket socket = new DatagramSocket(7000);
				socket.send(packet);
				socket.close();

				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
