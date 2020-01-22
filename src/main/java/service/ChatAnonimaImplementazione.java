package service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.BaseFutureAdapter;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.rpc.ObjectDataReply;
import net.tomp2p.storage.Data;

public class ChatAnonimaImplementazione implements ChatAnonima {
	
	final private Peer peer;
	final private PeerDHT dht;
	private ArrayList<String> listaChat;
	final private int DEFAULT_MASTER_PORT=4000;
	private MessageListener listener;


	
	public ChatAnonimaImplementazione (int id, String masterPeer, MessageListener _listener) throws Exception {
		
		 this.listener=_listener;
		 this.peer = new PeerBuilder(Number160.createHash(id)).ports(DEFAULT_MASTER_PORT + id).start();
	     this.dht = new PeerBuilderDHT(peer).start();
		 this.listaChat = new ArrayList <String>();
		
			FutureBootstrap fb = peer.bootstrap().inetAddress(InetAddress.getByName(masterPeer)).ports(DEFAULT_MASTER_PORT).start();
	        fb.awaitUninterruptibly();
	        if (fb.isSuccess()) {
	            peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
	        }
	       
	        peer.objectDataReply(new ObjectDataReply() {
	            public Object reply(PeerAddress sender, Object request) throws Exception {
	            	// Ottengo il messaggio
	                Messaggio message = (Messaggio) request;
	                if (!peer.peerID().equals(message.getPeerDestinazione().peerId())) {
	                    //Se non sono la destinazione devo inoltrare il messaggio ad un altro Peer
	                	inviaMessaggio(message, message.getPeerDestinazione());
	                    return null;
	                } else {
                        //Se sono la destinazione devo solo effettuare il Parse del messaggio
	                	return listener.parseMessage(message);
	                }

	            }

	        });
	      
	      
	} // fine costruttore
	

	public boolean createRoom(String _room_name, String password) {
		try {
			Chat chat = new Chat(_room_name, password);
	        FutureGet futureGet = dht.get(Number160.createHash(_room_name)).start();
	        futureGet.awaitUninterruptibly();
	        
	        //se non esiste nessuna Chat con lo stesso nome allora effettua l'inserimento
	        if (futureGet.isSuccess() && futureGet.isEmpty()) {
	
				dht.put(Number160.createHash(_room_name)).data(new Data(chat)).start().awaitUninterruptibly();
				joinRoom(_room_name,password);
	        	return true;
	        }

		}catch (Exception e) {
			    e.printStackTrace();
        }

		return false;
	
	}

	public boolean joinRoom(String _room_name,String password) {
		// TODO Auto-generated method stub
		 try {
	            FutureGet futureGet = dht.get(Number160.createHash(_room_name)).start();
	            futureGet.awaitUninterruptibly();
	            //Se la get ha successo e la risposta non è vuota
	            if (futureGet.isSuccess() && !futureGet.isEmpty()) {
	            	
	            	//Preleva l'oggetto ed ottieni la Chat richiesta
	                Chat chat = (Chat) futureGet.dataMap().values().iterator().next().object();
                    // Se la password inserita è corretta
	                if(chat.getPassword().equals(password)) {
	                //Se nella Chat il Peer non è contenuto, quindi non partecipa, aggiungilo.
	                if (!chat.getPeer().contains(peer.peerAddress())) {
	                     chat.aggiungiPeer(this.peer.peerAddress());
	                     dht.put(Number160.createHash(_room_name)).data(new Data(chat)).start()
	                            .awaitUninterruptibly();
	                     //Aggiungi alla lista della Chat in cui il Peer partecipa anche la nuova.
	                     this.listaChat.add(_room_name);
	                     return true;
	                }
                    } // fine if password
	                else {
                    	return false;
                    }
	            }
	        } catch (Exception e) {
	            return false;
	        }
	        return false;
	}

	public boolean leaveRoom(String _room_name) {
		// TODO Auto-generated method stub
		try {
			//Ottengo la Chat
            FutureGet futureGet = dht.get(Number160.createHash(_room_name)).start();
            futureGet.awaitUninterruptibly();
            if (futureGet.isSuccess()) {
            	Chat chat = (Chat) futureGet.dataMap().values().iterator().next().object();
            	//Se il Peer partecipa alla Chat allora eliminalo dall lista dei partecipanti
            	// e inserisce la Chat con la nuova lista dei Peer nella DHT
            	if (chat.getPeer().contains(peer.peerAddress())) {
            		chat.eliminaPeer(peer.peerAddress());
                    FuturePut futurePut = dht.put(Number160.createHash(_room_name)).data((new Data(chat))).start();
                    futurePut.awaitUninterruptibly();
                    
                    if (futurePut.isSuccess()){
                    	//elimina la Chat dalla lista delle Chat in cui il Peer partecipa
                        listaChat.remove(_room_name);
                        return true;
                    } 
                    else {
                    	return false;
                    }
            	}
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean sendMessage(String _room_name, String _text_message) {
		// TODO Auto-generated method stub
		
	
	
        Messaggio messaggio = new Messaggio(_room_name, _text_message);
       
        try {
        	
        	// Ottieni la Chat
            FutureGet futureGet = dht.get(Number160.createHash(_room_name)).start();
            futureGet.awaitUninterruptibly();
            
            if (futureGet.isSuccess()) {
            	
                Chat chat = (Chat) futureGet.dataMap().values().iterator().next().object();
                //se la chat non contiene il mio PeerAddress vuol dire che non sono entrato
                if (!chat.getPeer().contains(peer.peerAddress())) {
                    // Di conseguenza non posso inviare il messaggio
                    return false;
                }
                else 
                //Altrimenti sono all'interno della Chat e posso inoltrare il messaggio
                {
                	//Ottengo tutti i Peer che partecipano alla Chat
                    HashSet<PeerAddress> peers = chat.getPeer();
                    
                    for (PeerAddress destinazione : peers) {
                    	//Se nella Chat sono presenti più di 2 Peer
                        if (peers.size() > 2) {
                            // Se io non sono la destinazione allora devo trovare un Peer
                        	// per inoltrare il messaggio
                            if (!(peer.peerID().equals(destinazione.peerId()))) {
                                // Ottengo il PeerAddress del Peer dove inoltrare il messaggio
                                PeerAddress peerInoltro = getPeerInoltro(peer.peerAddress(), destinazione,chat);
                                // Setto la destinazione nel messaggio
                                messaggio.setPeerDestinazione(destinazione);
                                //Invio il messaggio a 'peerInoltro'
                                inviaMessaggio(messaggio, peerInoltro);
                            }
                        } 
                    }
                    return true;
                }
            }


        } catch (Exception e) {
            return false;
        }
        return false;
    }
	
	public ArrayList <String> getListaChat() {
		return this.listaChat;
	}
	
	
     public boolean leaveNetwork() {
		for(String nomeChat: new ArrayList<String>(this.listaChat)) leaveRoom(nomeChat);
		dht.peer().announceShutdown().start().awaitUninterruptibly();
		return true;
	}
	

	
	 public PeerAddress getPeerInoltro(PeerAddress sorgente, PeerAddress destinazione, Chat chat) {
		   
		    //Genero un nuovo set per gli Indirizzi
	        Set<PeerAddress> setPeer = new HashSet<PeerAddress>();
	        // Aggiungo tutti i Peer partecipanti della Chat nel nuovo Set inizializzato
	        setPeer.addAll(chat.getPeer());
	        
	        // Converto il set di Peer in un Array
	        PeerAddress[] arrayPeer =  setPeer.toArray(new PeerAddress[setPeer.size()]);
	        Random random = new Random();
	        
	         while (true) 
	         {
	        	//Genero un numero casuale che può essere massimo pari 
	        	//al numero di Peer nella Chat
	            int indice= random.nextInt(chat.getPeer().size());
	            //Vincolo fondamentale è che l'indirizzo non deve essere uguale a sorgente e destinazione
	            if(!(arrayPeer[indice].peerId().equals(destinazione.peerId()))&& !(arrayPeer[indice].peerId().equals(sorgente.peerId())))
	                return  arrayPeer[indice];
	         }
	    }
    
	  private void inviaMessaggio(Messaggio messaggio, final PeerAddress peerInoltro) {
	        FutureDirect futureDirect =  dht.peer().sendDirect(peerInoltro).object(messaggio).start();
	        futureDirect.addListener(new BaseFutureAdapter<FutureDirect>() {
	            public void operationComplete(FutureDirect future) throws Exception {
	                if (!future.isSuccess()) {
	                    System.out.println("ERRORE INVIO MESSAGGIO");
	                }
	            }
	        });
	    }
	  
	 
	  
	

}