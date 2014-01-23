import java.io.Serializable;

public class Message implements Serializable {
	
	String sourceName = "";
	String destName = "";
	int seqNum = 0;
	Boolean duplicate = false;
	String msgKind = "";
	Object payload = null;
	
	public Message(String dest, String kind, Object data) {
		destName = dest;
		msgKind = kind;
		payload = data;
	}

	// These settors are used by MessagePasser.send, not your app
	public void set_source(String source) {
		sourceName = source;
	}

	public void set_seqNum(int sequenceNumber) {
		seqNum = sequenceNumber;
	}

	public void set_duplicate(Boolean dupe) {
		duplicate = dupe;
	}
	// other accessors, toString, etc as needed

	public Object getData() {
		// TODO Auto-generated method stub
		return payload;
	}
}
