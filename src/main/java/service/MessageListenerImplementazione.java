package service;

import org.beryx.textio.TextTerminal;



public class MessageListenerImplementazione implements MessageListener {
	   
	    private TextTerminal<?> shell= null;
	    private int peerID;
	    private Messaggio messaggio = null;
	    private boolean bool = false;

	    public  MessageListenerImplementazione(int peerID) {
	        this.peerID = peerID;
	    }
	    

		public Object parseMessage(Object obj) {
			  this.messaggio = (Messaggio) obj;
		        bool = true;
		        shell.printf("( MESSAGGIO ["+ messaggio.getOre()+":"+messaggio.getMinuti()+"], CHAT :"+ messaggio.getnomeChat()+" ) \n");
		        shell.printf(messaggio.getContenuto()+"\n\n");
		        return "success";
		}


	    
	    public void setTerminal(TextTerminal<?> terminal) {
	        this.shell = terminal;
	    }

	    public Messaggio getMessaggio() {
	        return messaggio;
	    }

	    public void setMsg(Messaggio messaggio) {
	        this.messaggio = messaggio;
	    }

	    public int getPeerID() {
	        return peerID;
	    }

	    public void setPeerID(int peerID) {
	        this.peerID = peerID;
	    }

	    public boolean getBool(){
	        return this.bool;
	    }



}