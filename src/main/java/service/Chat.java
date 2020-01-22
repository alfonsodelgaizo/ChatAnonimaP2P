package service;

import java.io.Serializable;
import java.util.HashSet;


import net.tomp2p.peers.PeerAddress;



public class Chat  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String nomeChat;
	public String password;
	public HashSet<PeerAddress> listaPeer;
	
	public Chat (String nomeChat,String password) {
		this.nomeChat = nomeChat;
		this.listaPeer = new HashSet<PeerAddress>();
		this.password = password;
	}
	
	public HashSet<PeerAddress> getPeer(){
		return listaPeer;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPeer(HashSet<PeerAddress> peer) {
		this.listaPeer =peer;
	}
	
	public String getNomeChat() {
		return this.nomeChat;
	}
	
	public void setNomeChat(String new_nomeChat) {
		this.nomeChat = new_nomeChat;
	}
	
	public boolean aggiungiPeer(PeerAddress indirizzoPeer) {
		return this.listaPeer.add(indirizzoPeer);
	}
	
	public boolean eliminaPeer (PeerAddress indirizzoPeer) {
		return this.listaPeer.remove(indirizzoPeer);
	}
	
}