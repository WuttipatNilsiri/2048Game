package SERVER;

public class Message {
	
	String text;
	
	public Message(String text){
		this.text = text;
	}
	
	public Message(){
		
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
