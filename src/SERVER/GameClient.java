package SERVER;

import java.io.IOException;

import com.esotericsoftware.kryonet.*;

public class GameClient {

    
    private Client client; 
    public GameClient() throws IOException {
       
        
        client = new Client();
        client.addListener(new ClientLisntener() );
        client.start();
        client.connect(5000, "127.0.0.1", 54334);
        client.getKryo().register(Message.class);
    }

    class ClientLisntener extends Listener {
    	@Override
    	public void connected(Connection arg0) {
    		// TODO Auto-generated method stub
    		super.connected(arg0);
    		
    	}
    	@Override
    	public void received(Connection arg0, Object arg1) {
    		// TODO Auto-generated method stub
    		super.received(arg0, arg1);
    		if (arg1 instanceof Message) {
				Message message = (Message) arg1;
				System.out.println(message.getText());
			}
    	}
    }
    
    public void sendMessage(String text) {
    	Message msg = new Message();
    	msg.text = text;
    	client.sendTCP(msg);
    }
}

