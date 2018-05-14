package SERVER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.*;

public class GameClient {

    
    private Client client; 
    List<String> scorelist = new ArrayList<String>();
    /**
     * initial Game Client
     */
    public GameClient() {
      
        client = new Client();
        client.addListener(new ClientLisntener() );
        client.getKryo().register(Message.class);
        client.getKryo().register(ArrayList.class);
    }
    /**
     * ClientL Listener if have event with client 
     */
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
    		if (arg1 instanceof List) {
    			scorelist.clear();
    			List<String> ls = (List<String>) arg1;
    			for (String s : ls) {
    				scorelist.add(s);
    			}
    		}
    	}
    }
    /**
     * send Message to Server
     * @param text
     */
    public void sendMessage(String text) {
    	Message msg = new Message();
    	msg.text = text;
    	client.sendTCP(msg);
    }
    /**
     * get Score List out of Client
     * @return
     */
    public List<String> getScoreList(){
    	return scorelist;
    }
    /**
     * connect to ip with port
     * @param ip
     * @param port
     * @throws IOException
     */
    public void connect(String ip,int port) throws IOException {
    	client.start();
        client.connect(5000, ip, port);
    }
}