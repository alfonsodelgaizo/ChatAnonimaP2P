# Chat Anonima
Sviluppo di una Chat Anonima su di una rete P2P. Ogni Peer può inviare messaggi su di una chat specifica in maniera anonima.
Questo sistema permette agli utente di creare una nuova chat, entrare nella chat, abbandonare la chat e inviare messaggi nella chat stessa.

## Funzionalità base
- createRoom : crea una nuova chat
- joinRoom : entra nella nuova chat
- sendMessage : invia un messaggio nella chat in maniera anonima
- leaveRoom : abbandona la chat

## Funzionalità aggiuntive
- getListaChat : visualizza tutte le Chat in cui un Peer partecipa
- leaveNetwork : abbandona la rete 

## Tecnologie
- Java
- Apache Maven
- Tom P2P
- JUnit
- Eclipse
- Docker

## Struttura Progetto

- Chat, classe che contiene la definizione della chat in cui un Peer può partecipare.
- ChatAnonima, API che definisce tutte le funzionalità del progetto.
- ChatAnonimaImplementazione, implementazione API ChatAnonima
- Shell,interfaccia per comunicare con in sistema.
- Messaggio, classe che contiene la definizione del messaggio che verrà inoltrato.
- MessageListener, API che definisce tutte le funzionalità del listener per i messaggi.
- MessageListenerImplementazione, implementazione API MessageListener

- ChatAnonimaUnitTest, classe che definisce il test case JUnit.

# Build applicazione in un Docker Container

Come prima cosa devi effettuare build del docker container.

`docker build --no-cache -t chatp2p .`

### Lanciare Master Peer

Dopo aver affettuato build del docker container, si può lanciare il Master Peer

`docker run -i --name MASTER-PEER -e MASTERIP="127.0.0.1" -e ID=0 chatp2p `

la variabile MASTERIP è l'indirizzo IP del Master Peer e la variabile ID è l'ID unico del Peer.

### Lanciare un'altro Peer

Per lanciare un altro Peer devi verificare l'indirizzo IP del container creato

`docker inspect <containerID>`

per individuare il container ID 

`docker ps`

Ora puoi lanciare i Peer cambiando solo il Peer ID perché l'ID del Peer deve essere unico

`docker run -i --name PEER-1 -e MASTERIP="172.17.0.2" -e ID=1 chatp2p`
`docker run -i --name PEER-2 -e MASTERIP="172.17.0.2" -e ID=2 chatp2p`


### Developed by
Alfonso Del Gaizo (mat.0522500707)
