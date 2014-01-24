import java.util.Iterator;
import java.util.Map;

public class Rules {

	parser parser = new parser();
	public static boolean send_delay_flag = false;
	public static boolean recv_delay_flag = false;

	public void checkSendRules(Message msg) {

		int length = 0;

		// Rules for action drop
		length = parser.sendAction_drop.size();
		if (length != 0) {

			Iterator<Object> it = parser.sendAction_drop.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, String> rules = (Map<String, String>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").equals(msg.seqNum)) {
								return;
							}

			}
		}

		// Rules for action delay
		length = parser.sendAction_delay.size();
		if (length != 0) {

			Iterator<Object> it = parser.sendAction_delay.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, String> rules = (Map<String, String>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").equals(msg.seqNum)) {
								Sender.sendQueue.add(msg);
								send_delay_flag = true;
								return;
							}

			}
		}
		// Rules for action duplicate
		length = parser.sendAction_duplicate.size();
		if (length != 0) {

			Iterator<Object> it = parser.sendAction_duplicate.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, String> rules = (Map<String, String>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").equals(msg.seqNum)) {
								Message msg_dup = new Message(msg.destName, msg.msgKind, msg.getData());
								msg_dup.set_duplicate(true);
								msg_dup.set_seqNum(msg.seqNum);
								msg_dup.set_source(msg.sourceName);
								
								Sender.sendQueue.add(msg);
								Sender.sendQueue.add(msg_dup);
								
								send_delay_flag = false;
								return;
							}

			}
		}
		
		//If no action matches, then send the msg
		Sender.sendQueue.add(msg);
	}
	
	public void checkReceiveRules(Message msg) {

		int length = 0;

		// Rules for action drop
		length = parser.receiveAction_drop.size();
		if (length != 0) {

			Iterator<Object> it = parser.receiveAction_drop.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, String> rules = (Map<String, String>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").equals(msg.seqNum)) {
								return;
							}

			}
		}

		// Rules for action delay
		length = parser.receiveAction_delay.size();
		if (length != 0) {

			Iterator<Object> it = parser.receiveAction_delay.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, String> rules = (Map<String, String>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").equals(msg.seqNum)) {
								Receiver.receiveQueue.add(msg);
								recv_delay_flag = true;
								return;
							}

			}
		}
		// Rules for action duplicate
		length = parser.receiveAction_duplicate.size();
		if (length != 0) {

			Iterator<Object> it = parser.receiveAction_duplicate.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Map<String, String> rules = (Map<String, String>) obj;

				if (rules.get("src") == null
						|| rules.get("src").equals(msg.sourceName))
					if (rules.get("dest") == null
							|| rules.get("dest").equals(msg.destName))
						if (rules.get("kind") == null
								|| rules.get("kind").equals(msg.msgKind))
							if (rules.get("seqNum") == null
									|| rules.get("seqNum").equals(msg.seqNum)) {
								Message msg_dup = new Message(msg.destName, msg.msgKind, msg.getData());
								msg_dup.set_duplicate(true);
								msg_dup.set_seqNum(msg.seqNum);
								msg_dup.set_source(msg.sourceName);
								
								Receiver.receiveQueue.add(msg_dup);
								Receiver.receiveQueue.add(msg);
								
								recv_delay_flag = false;
								return;
							}

			}
		}
		
		//If no action matches, then send the msg
		Receiver.receiveQueue.add(msg);
	}
}
