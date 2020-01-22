import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import service.ChatAnonimaImplementazione;
import service.MessageListenerImplementazione;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChatAnonimaUnitTest {
	
	private MessageListenerImplementazione listener0,listener1,listener2;
	private ChatAnonimaImplementazione peer0, peer1, peer2;
	
	
	@Test	
	public void testCreate() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);

	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);
	        
	        //La Chat è creata correttamente perchè non esiste nella rete
	        assertTrue(peer0.createRoom("Chat1", "password"));
	        // La creazione della Chat fallisce in quanto la Chat già esiste
	        assertFalse(peer1.createRoom("Chat1", "password"));
	        
	}
	
	@Test	
	public void testJoin() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);
		    listener2 = new MessageListenerImplementazione(2);
		    
		    

	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);
	        peer2 = new ChatAnonimaImplementazione(2, "127.0.0.1", listener2);
	        
	       peer0.createRoom("Chat1", "password");
	        
	        //Il Peer entra nella Chat
	        assertTrue(peer2.joinRoom("Chat1", "password"));
	        
	        // Il Peer non entra in quanto la Chat2 non esiste
	        assertFalse(peer2.joinRoom("Chat2", "password"));
	        
	        // Il Peer non entra nella Chat in quanto già è entrato
	        assertFalse(peer2.joinRoom("Chat1", "password"));
	        
	       
  
	}
	
	@Test	
	public void testSendMessage() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);
		    listener2 = new MessageListenerImplementazione(2);
		    
		    
	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);
	        peer2 = new ChatAnonimaImplementazione(2, "127.0.0.1", listener2);
	        
	        
	        peer0.createRoom("Chat1", "password");
	        peer1.joinRoom("Chat1", "password");
	        peer2.joinRoom("Chat1", "password");
	        	        
	        // Il Peer invia il messaggio
	        assertTrue(peer1.sendMessage("Chat1", "Ciao"));
	       
	        // L'invio del messaggio fallisce perchè il nome della Chat è sbagliato
	        assertFalse(peer1.sendMessage("Chat", "Ciao"));

  
	}
	
	@Test	
	public void testLeaveRoom() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);
		    listener2 = new MessageListenerImplementazione(2);


	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);

	        
	        peer0.createRoom("Chat1", "password");
	        peer1.joinRoom("Chat1", "password");
	        
	        // Il Peer abbandona la Chat
	        assertTrue(peer1.leaveRoom("Chat1"));
	        
	        // Il risultato sarà false perché il Peer è già uscito dalla rete
	        assertFalse(peer1.leaveRoom("Chat1"));
       
	}

	@Test	
	public void leaveNetwork() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		   

	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);

	        
	        peer0.createRoom("Chat1", "password");
	        
	        // Il Peer abbandona la rete
	        assertTrue(peer0.leaveNetwork());
	        
	}


}