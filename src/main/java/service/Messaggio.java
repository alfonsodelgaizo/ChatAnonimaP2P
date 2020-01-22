package service;

import java.io.Serializable;
import java.time.LocalDateTime;

import net.tomp2p.peers.PeerAddress;

public class Messaggio implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeChat;
	 private String contenuto;
	 private PeerAddress peerDestionazione;
	 private int ore;
	 private int minuti;
	 
	public Messaggio (String nomeChat, String contenuto) {
		 this.nomeChat = nomeChat;
		 this.contenuto = contenuto;
		 this.peerDestionazione = null;
		 this.setOre(LocalDateTime.now().getHour());
		 this.setMinuti(LocalDateTime.now().getMinute());

	}
	
	public String getnomeChat() {
		return this.nomeChat;
	}
	
	public void setnomeChat(String nomeChat) {
		this.nomeChat= nomeChat;
	}
	
	public String getContenuto() {
		return this.contenuto;
	}
	
	public void setContenuto(String contenuto) {
		this.contenuto= contenuto;
	}

	public PeerAddress getPeerDestinazione() {
		return this.peerDestionazione;
	}
	
	public void setPeerDestinazione(PeerAddress peerDestinazione) {
		this.peerDestionazione= peerDestinazione;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getMinuti() {
		return minuti;
	}

	public void setMinuti(int minuti) {
		this.minuti = minuti;
	}
	
	
	
}