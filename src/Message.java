import java.io.Serializable;

public class Message implements Serializable {
	String content = "111";
	String dest = "";
	
	public Message(String dest, String kind, Object data) {
		content = (String)data;
	}

	// These settors are used by MessagePasser.send, not your app
	public void set_source(String source) {
		
	}

	public void set_seqNum(int sequenceNumber) {
		
	}

	public void set_duplicate(Boolean dupe) {
		
	}
	// other accessors, toString, etc as needed

	public String getData() {
		// TODO Auto-generated method stub
		return content;
	}
}
