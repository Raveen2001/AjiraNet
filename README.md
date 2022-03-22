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
 
 > ADD COMPUTER A1
Successfully added A1
> ADD COMPUTER A2
Successfully added A2
> ADD 
Error: Invalid Command Syntax
> ADD REPEATER R1
Successfully added R1
> ADD DEVICE A1
Error: Invalid Command Syntax
> ADD COMPUTER A1
Error: That name already exists.
> ADD COMPUTER A3
Successfully added A3
> SET_DEVICE_STRENGTH A1 3
Successfully defined strength.
> CONNECT A1 A2
Successfully connected.
> CONNECT A2 A3
Successfully connected.
> INFO_ROUTE A1 A3
A1 -> A2 -> A3
> INFO_ROUTE A1 A2
A1 -> A2
> CONNECT A2 A6
Error: No source or destination node.
> INFO_ROUTE A3 A1
Error: No Route Found.
> CONNECT A3 A2
Successfully connected.
> CONNECT A2 A1
Successfully connected.
> INFO_ROUTE A3 A1
A3 -> A2 -> A1
 
 ```
