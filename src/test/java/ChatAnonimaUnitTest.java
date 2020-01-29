import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import service.ChatAnonimaImplementazione;
import service.MessageListenerImplementazione;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChatAnonimaUnitTest {
	
	private MessageListenerImplementazione listener0,listener1,listener2,listener3;
	private ChatAnonimaImplementazione peer0, peer1, peer2,peer3;
	
	
	@Test	
	public void testCreate() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);
		    listener2 = new MessageListenerImplementazione(2);
		    listener3 = new MessageListenerImplementazione(3);

	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);
	        peer2 = new ChatAnonimaImplementazione(2, "127.0.0.1", listener2);
	        peer3 = new ChatAnonimaImplementazione(3, "127.0.0.1", listener3);
	        
	        //La Chat è creata correttamente perchè non esiste nella rete
	        assertTrue(peer0.createRoom("Chat1", "password"));
	        // La creazione della Chat fallisce in quanto la Chat già esiste
	        assertFalse(peer1.createRoom("Chat1", "password"));
	        
	        assertFalse(peer2.createRoom("-", "password2"));
	        assertFalse(peer2.createRoom(".", "password"));
	        
	        assertTrue(peer1.createRoom("Chat2", "password"));



	        
	        
	        
	}
	
	@Test	
	public void testJoin() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);
		    listener2 = new MessageListenerImplementazione(2);
		    listener3 = new MessageListenerImplementazione(3);

		    

	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);
	        peer2 = new ChatAnonimaImplementazione(2, "127.0.0.1", listener2);
	        peer3 = new ChatAnonimaImplementazione(3, "127.0.0.1", listener3);

	        
	        peer0.createRoom("Chat3", "password");
	        peer2.createRoom("Chat5", "chat");
	        
	        
	        // Il Peer non entra in quanto la Chat2 non esiste
	        assertFalse(peer2.joinRoom("Chat4", "password"));
	        
	        //Il Peer3 entra nella chatr 5 con password: chat
	        assertTrue(peer3.joinRoom("Chat5", "chat"));     
	        
	       
  
	}
	
	@Test	
	public void testSendMessage() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);
		    listener2 = new MessageListenerImplementazione(2);
		    listener3 = new MessageListenerImplementazione(3);

		    
		    
	        peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);
	        peer2 = new ChatAnonimaImplementazione(2, "127.0.0.1", listener2);
	        peer3 = new ChatAnonimaImplementazione(3, "127.0.0.1", listener3);

	        
	        
	        peer0.createRoom("Chat1", "password");
	        peer1.joinRoom("Chat1", "password");
	        peer2.joinRoom("Chat1", "password");
	        	        
	        // Il Peer invia il messaggio
	        assertTrue(peer1.sendMessage("Chat1", "Ciao sono il Peer1"));
	       
	        // L'invio del messaggio fallisce perchè il nome della Chat è sbagliato
	        assertFalse(peer1.sendMessage("Chat", "Ciao"));
	        
	        peer3.joinRoom("Chat1", "password");
	        assertTrue(peer3.sendMessage("Chat1", "Ciao, sono il Peer3"));
	        assertTrue(peer2.sendMessage("Chat1", "Ciao, sono il Peer2"));
	        assertTrue(peer0.sendMessage("Chat1", "Ciao, sono il Peer0"));

	        

  
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
	        assertTrue(peer1.leaveRoom("Chat1","password"));
	        
	        // Il risultato sarà false perché il Peer è già uscito dalla rete
	        assertFalse(peer1.leaveRoom("Chat1","password"));
       
	}

	@Test	
	public void leaveNetwork() throws Exception {
		    listener0 = new MessageListenerImplementazione(0);
		    listener1 = new MessageListenerImplementazione(1);
		    listener2 = new MessageListenerImplementazione(2);
		    listener3 = new MessageListenerImplementazione(3);


		    peer0 = new ChatAnonimaImplementazione(0, "127.0.0.1", listener0);
	        peer1 = new ChatAnonimaImplementazione(1, "127.0.0.1", listener1);
	        peer2 = new ChatAnonimaImplementazione(2, "127.0.0.1", listener2);
	        peer3 = new ChatAnonimaImplementazione(3, "127.0.0.1", listener3);

	        
	        peer0.createRoom("Chat1", "password");
	        peer1.joinRoom("Chat1", "password");
	        peer2.joinRoom("Chat1", "password");
	      

	        
	        // Il Peer0 abbandona la rete
	        assertTrue(peer0.leaveNetwork());
	        assertTrue(peer1.leaveNetwork()); 
	        assertTrue(peer2.leaveNetwork());
	        assertTrue(peer3.leaveNetwork());
	        
	}


}