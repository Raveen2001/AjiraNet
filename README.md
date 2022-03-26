# AjiraNetwork
An console based application that let us to develope a local network for communication-related problem

# Get Started

 ``` 
 cd .\src\main\
 javac .\Main.java
 java .\Main.java 
 ```
 
 # Sample Output
 
 ```
 
> ADD COMPUTER C1
Successfully added C1

> ADD COMPUTER C2
Successfully added C2

> ADD COMPUTER C3
Successfully added C3

> ADD COMPUTER C4
Successfully added C4

> ADD REPEATER R1
Successfully added R1

> CONNECT C1 R1
Successfully connected.

> CONNECT C1 C2
Successfully connected.

> CONNECT R1 C2
Successfully connected.

> CONNECT C2 C3
Successfully connected.

> CONNECT C3 C4
Successfully connected.

> INFO_ROUTE C1 C4
C1 -> R1 -> C2 -> C3 -> C4

> SET_DEVICE_STRENGTH C1 10
Successfully defined strength.

> ADD_TO_FIREWALL C1 C2
C2 added to C1â??s firewall.

> SEND C1 C4 HELLO
Received Message : HELLO
Hops: 4

> ADD_TO_FIREWALL C1 C4
C4 added to C1â??s firewall.

> SEND C1 C4 HELLO
Error: Message can't be sent since node is blocked.

> DISABLE SEND C2
Successfully disabled Send.

> DISABLE RECEIVE C2
Successfully disabled Receive.

> ENABLE SEND C1
Successfully enabled Send.

> ENABLE RECIEVE C2
Error: Invalid command Syntax.

> ENABLE RECEIVE C1
Successfully enabled Receive.

> EXIT
THANK YOU : )


 ```
