package service;

import java.util.ArrayList;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;



public class Shell {
	
	@Option(name="-m", aliases="--masterip", usage="the master peer ip address", required=true)
	private static String master;

	@Option(name="-id", aliases="--identifierpeer", usage="the unique identifier for this peer", required=true)
    private static int id;
	
	//private static String master = "localhost";

	//private static int id=2;

	public static void main(String[] args)  throws Exception {
		 
		 ArrayList<String> listaChat;
		 listaChat = new ArrayList <String>();
	
		    Shell terminalIstance = new Shell();
			final CmdLineParser parser = new CmdLineParser(terminalIstance);
			try {
				parser.parseArgument(args);

				TextIO textIO = TextIoFactory.getTextIO();
				TextTerminal<?> terminal = textIO.getTextTerminal();

				MessageListenerImplementazione listener =  new MessageListenerImplementazione(id);
				listener.setTerminal(terminal);
				
				ChatAnonimaImplementazione peer = new ChatAnonimaImplementazione(id,master,listener);
				terminal.printf("\nStarting peer id: %d on master node: %s\n",id, master);
			
			while (true) {
				
				printMenu(terminal);
				int option = textIO.newIntInputReader()
						.withMaxVal(6)
						.withMinVal(1)
						.read("\n Seleziona un opzione : \n");
				
                switch (option) {
                
	                case 1:
							terminal.printf("\n Crea una nuova Chat\n");
							String name = textIO.newStringInputReader()
									.read("Imposta il nome della Chat: ");
							String password = textIO.newStringInputReader()
									.read("Imposta la password della Chat:");
							if(peer.createRoom(name,password))
								terminal.printf("\n LA CHAT -- %s -- E' STATA CREATA SUCCESSO \n\n",name);
							else
								terminal.printf("\n ERRORE CREAZIONE CHAT \n");
							break;
							
	                case 2:
						terminal.printf("\nEntra nella Chat\n");
						String sname = textIO.newStringInputReader()
								.read("Inserisci il nome della Chat: ");
						String j_password = textIO.newStringInputReader()
								.read("Inserisci la password : ");
						if(peer.joinRoom(sname,j_password))
							terminal.printf(" \n BENVENUTO NELLA CHAT -- %s -- \n",sname);
						else
							terminal.printf("\n ERRORE ENTRATA CHAT \n");
						break;
					
	                case 3:
	                	terminal.printf("\n Invia un messaggio \n");
						String tname = textIO.newStringInputReader()
								.read(" Inserisci il nome della Chat:");
						String message = textIO.newStringInputReader()
								.read(" \n Scrivi Messaggio : ");
						if(peer.sendMessage(tname,message))
							terminal.printf("\n MESSAGGIO INVIATO CORRETTAMENTE NELLA STANZA -- %s --\n\n",tname);
						else
							terminal.printf("\n ERRORE INVIO MESSAGGIO\n");

						break;

						
	            	case 4:
						terminal.printf("\nAbbandona la chat\n");
						String nomeChat = textIO.newStringInputReader()
								.read("Inserisci il nome della Chat :");
						if(peer.leaveRoom(nomeChat))
							terminal.printf("\n HAI ABBANDONATO LA CHAT  -- %s -- \n\n",nomeChat);
						else
							terminal.printf("\n ERRORE ABBANDONO CHAT \n");
						break;
					
	            	case 5:
						terminal.printf("\n Riepilogo chat\n");
						listaChat=peer.getListaChat();
						if(listaChat.size() == 0) {
					        terminal.printf(" NON HAI EFFETTUATO L'ACCESSO A NESSUNA CHAT \n");

						}
				        for (int i = 0; i < listaChat.size(); i++) {   
				        	 nomeChat=listaChat.get(i);
				        	 int indice = i+1;
							 terminal.printf("\n(%d) %s \n",indice,nomeChat);

						}
				        terminal.printf("---------------- \n");
						break;
					
	            	case 6:
	            		  peer.leaveNetwork();
					      terminal.printf(" CIAO !! \n");
						  System.exit(0);
						break;
							
	            	default:
	            			break;
            
                }
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	
	
	public static void printMenu(TextTerminal terminal) {
		terminal.println("\n");
		terminal.println("MENU");

		terminal.println("\n 1 - CREA CHAT");
		terminal.println("\n 2 - ENTRA NELLA CHAT");
		terminal.println("\n 3 - INVIA MESSAGGIO");
		terminal.println("\n 4 - ABBANDONA CHAT");
		terminal.println("\n 5 - VISUALIZZA ELENCO CHAT");
		terminal.println("\n 6 - CHIUDI APPLICAZIONE");


	}
}
