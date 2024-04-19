# IS23-AM17

## Nota
My Shelfie è un gioco da tavolo sviluppato ed edito da Cranio Creations Srl. I contenuti grafici di questo progetto riconducibili al prodotto editoriale da tavolo sono utilizzati previa approvazione di Cranio Creations Srl a solo scopo didattico. È vietata la distribuzione, la copia o la riproduzione dei contenuti e immagini in qualsiasi forma al di fuori del progetto, così come la redistribuzione e la pubblicazione dei contenuti e immagini a fini diversi da quello sopracitato. È inoltre vietato l'utilizzo commerciale di suddetti contenuti.


## Implemented functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| UML |🟢| 
| Basic rules |🟢|
| Complete rules |🟢|
| RMI |🟢|
| Socket |🟢|
| CLI |🟢|
| GUI |🟢|
| Multiple games |🔴|
| Chat |🟢|
| Persistence |🔴|
| Resilience |🟢|

🟢 : implemented
🔴 : not implemented

We implemented every functionality, we support connection with RMI/Socket and player can play using CLI/GUI.
We implemented "Chat" and "Disconnection resilience" as Advanced Functionalities.

## Deliverables content
Deliverables folder includes:
* High level UML describing interaction between main components of the application,
* Detailed UML containing in-depth details of all classes, methods and attributes,
* Peer reviews of model structure and connection protocol,
* Javadoc of methods used in project,
* Screenshot of test coverage (from IntelliJ coverage functionality),
* MyShelfie.jar, same for server and clients.

## Startup guide
Follow these steps to correctly run MyShelfie:
1. Download MyShelfie.jar from deliverables folder,
2. Run using `java -jar MyShelfie.jar` in command prompt (Windows/MacOS most recent command prompt will show ANSI color without any issue),
3. You will have to choose between two modes: server and CLI/GUI,
4. To start server simply digit 0 in prompt,
5. To start CLI/GUI digit 1/2 and follow the instructions on the screen.

## Short network guide
1. Be sure that server is running before trying to connect with any client,
2. Both in CLI/GUI socket port is 2540, with RMI port is not relevant and will be chosen by software,
3. You can play with server and client on the same PC, be sure to leave empty server address and port in CLI (to connect via RMI select "local RMI" option) or check "Local" option in GUI (values are read from JSON configuration file),
4. To reconnect to same match simply follow normal login procedure with values of the first login.

## Short command guide
* In CLI many commands are available, to know more about command digit @HELP (for example @HELP -> @GAME),
* To do a game move in CLI follow this syntax: @GAME (row1, col1),(row2, col2),(row3, col3),shelfieColumn -> order of selection is order of insertion in shelfie; you have to choose at least one tiles but less than three,
* To send a chat message in CLI follow this syntax: @CHAT 'nameOfReceiver' message.

## Troubleshooting
* If you have trouble connecting with RMI, disable network interfaces not used during connection (I.E. turn off WiFi if connected via ethernet) in server and client.
* If you have issues connecting with RMI using MacOS, run server with following command: java -Djava.rmi.server.hostname=serverIP -jar MyShelfie.jar
