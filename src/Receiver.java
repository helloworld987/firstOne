import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver implements Runnable {

	@SuppressWarnings("resource")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket server = null;
		try {
			server = new DatagramSocket(5050);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ExecutorService exec = Executors.newCachedThreadPool();
		while (true) {
			byte[] recvBuf = new byte[512];
			DatagramPacket recvPacket = new DatagramPacket(recvBuf,
					recvBuf.length);

			try {
				server.receive(recvPacket);

				ByteArrayInputStream byteStream = new ByteArrayInputStream(
						recvBuf);

				ObjectInputStream is = new ObjectInputStream(
						new BufferedInputStream(byteStream));

				Object o = is.readObject();

				Message msg = (Message) o;

				System.out.println("DataReceived:" + msg.getData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
