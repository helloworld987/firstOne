import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;

public class Rules {

	//Parser parser = new Parser();
	public static boolean send_delay_flag = false;
	public static boolean recv_delay_flag = false;

	public void isModified() throws FileNotFoundException {
		// Check if config file has changed
		File file = new File(MessagePasser.conf_filename);
		long Modified = file.lastModified();
		if (MessagePasser.lastModified != Modified) {
			MessagePasser.parser.parseConfig(MessagePasser.conf_filename);
			MessagePasser.lastModified = Modified;
		}
	}
	public void checkSendRules(Message msg) throws FileNotFoundException {
	

		int length = 0;

		//Check if config file is updated
		isModified();
				
		// Rules for action drop
		length = MessagePasser.parser.sendAction_drop.size();
		if (length != 0) {

			Iterator<Object> it = MessagePasser.parser.sendAction_drop.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, Object> rules = (Map<String, Object>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").toString().equals(String.valueOf(msg.seqNum))) {
								return;
							}

			}
		}

		// Rules for action delay
		length = MessagePasser.parser.sendAction_delay.size();
		if (length != 0) {

			Iterator<Object> it = MessagePasser.parser.sendAction_delay.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, Object> rules = (Map<String, Object>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").toString().equals(String.valueOf(msg.seqNum))) {
								send_delay_flag = true;
								Sender.sendQueue.add(msg);
								
								return;
							}

			}
		}
		// Rules for action duplicate
		length = MessagePasser.parser.sendAction_duplicate.size();
		if (length != 0) {

			Iterator<Object> it = MessagePasser.parser.sendAction_duplicate.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, Object> rules = (Map<String, Object>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").toString().equals(Integer.toString(msg.seqNum))) {

								Message msg_dup = new Message(msg.destName, msg.msgKind, msg.getData());
								msg_dup.set_duplicate(true);
								msg_dup.set_seqNum(msg.seqNum);
								msg_dup.set_source(msg.sourceName);
								
								send_delay_flag = false;
								Sender.sendQueue.add(msg);
								Sender.sendQueue.add(msg_dup);
								
								
								return;
							}

			}
		}
		
		//If no action matches, then send the msg
		send_delay_flag = false;
		Sender.sendQueue.add(msg);
		
	}
	
	public void checkReceiveRules(Message msg) throws FileNotFoundException {

		int length = 0;

		//Check if config file is updated
		isModified();
		
		// Rules for action drop
		length = MessagePasser.parser.receiveAction_drop.size();
		if (length != 0) {

			Iterator<Object> it = MessagePasser.parser.receiveAction_drop.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, Object> rules = (Map<String, Object>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").toString().equals(String.valueOf(msg.seqNum))) {
								return;
							}

			}
		}

		// Rules for action delay
		length = MessagePasser.parser.receiveAction_delay.size();
		if (length != 0) {

			Iterator<Object> it = MessagePasser.parser.receiveAction_delay.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, Object> rules = (Map<String, Object>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").toString().equals(String.valueOf(msg.seqNum))) {
								recv_delay_flag = true;
								Receiver.receiveQueue.add(msg);
								
								return;
							}

			}
		}
		// Rules for action duplicate
		length = MessagePasser.parser.receiveAction_duplicate.size();
		if (length != 0) {

			Iterator<Object> it = MessagePasser.parser.receiveAction_duplicate.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, Object> rules = (Map<String, Object>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").toString().equals(String.valueOf(msg.seqNum))) {
								Message msg_dup = new Message(msg.destName, msg.msgKind, msg.getData());
								msg_dup.set_duplicate(true);
								msg_dup.set_seqNum(msg.seqNum);
								msg_dup.set_source(msg.sourceName);
								
								recv_delay_flag = false;
								Receiver.receiveQueue.add(msg_dup);
								Receiver.receiveQueue.add(msg);
								
								
								return;
							}

			}
		}
		
		//If no action matches, then send the msg
		recv_delay_flag = false;
		Receiver.receiveQueue.add(msg);
		
	}
}
