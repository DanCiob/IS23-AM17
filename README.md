# IS23-AM17

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
| Multiple games |🟡|
| Chat |🟢|
| Persistence |🔴|
| Resilience |🟢|

## Deliveries content
Deliveries folder includes:
* High level UML describing interaction between main components of the application,
* Detailed UML containing in-depth description of all classes, methods and attributes (in both PNG and SVG format),
* Peer reviews of model structure and connection protocol,
* Javadoc of methods used in project,
* Screenshot of test coverage (from IntelliJ coverage functionality),
* MyShelfie.jar, same for server and clients.

## Startup guide
Follow these steps to correctly run MyShelfie:
1. Download MyShelfie.jar from deliveries folder
2. Run using `java -jar MyShelfie.jar` in command prompt (Windows/MacOS most recent command prompt will show ANSI color without any issue)
3. You will have to choose between two modes: server and CLI/GUI. 
4. To start server simply digit 0 in prompt
5. To start CLI/GUI digit 1/2 and follow the instructions on the screen

## Short network guide
1. Be sure that server is running before trying to connect with any client
2. Both in CLI/GUI socket port is 2540, with RMI port is not relevant and will be chosen by software
3. You can play with server and client on the same PC, be sure to leave empty server address and port in CLI or check "Local" option in GUI (values are read from JSON configuration file)
4. To reconnect to same match simply follow normal login procedure with values of the first login

## Short command guide
* In CLI many commands are available, to know more about command digit @HELP (for example @HELP -> @GAME)
