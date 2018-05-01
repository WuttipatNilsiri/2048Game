package SERVER;

import java.io.IOException;
import java.util.*;

import com.esotericsoftware.kryonet.*;



public class ScoreServer {
	
	public List<String> scoreLog;
	public Server server;
	public List<Connection> connection_ls;
	
	public ScoreServer() {
		scoreLog = new ArrayList<String>(); 
		connection_ls = new ArrayList<Connection>();
		server = new Server();
		server.getKryo().register(Message.class);
		server.addListener(new ServerListner());
	}
	
	public void start(int port) throws IOException {
		server.start();
		server.bind(port);
		System.out.println("SV Starting with " + port + " port");
	}
	
	class ServerListner extends Listener {
		
		@Override
		public void connected(Connection arg0) {
			super.connected(arg0);
			connection_ls.add(arg0);
			System.out.println(arg0.getRemoteAddressTCP().toString() + " has Connected");
		}
		
		@Override
		public void disconnected(Connection arg0) {
			connection_ls.remove(arg0);
			System.out.println(arg0.getID() + " has Disconnected");
			super.disconnected(arg0);
			
		}
		
		@Override
		public void received(Connection arg0, Object arg1) {
			
			super.received(arg0, arg1);
			
			if (arg1 instanceof Message) {
				Message msg = (Message) arg1;
				
				
				if (msg.getText().equalsIgnoreCase("REQSCORE")) {
					for (String score : scoreLog) {
						arg0.sendTCP(new Message(score));
					}
				}
				else {
				
					scoreLog.add(msg.getText());
			
				}
			}
		}
	}
	
	public static void main(String[] a) {
		try {
			ScoreServer _sv = new ScoreServer();
			_sv.start(54334);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
