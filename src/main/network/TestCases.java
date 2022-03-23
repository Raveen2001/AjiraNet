package network;

public class TestCases {
    static final String[] inputs = {
            "ADD COMPUTER A1",
            "ADD COMPUTER A2",
            "ADD COMPUTER A3",
            "ADD COMPUTER A4",
            "ADD COMPUTER A5",

            "CONNECT A1 A2",
            "CONNECT A1 A3",
            "CONNECT A2 A3",
            "CONNECT A3 A4",

            "SEND A1 A3 HELLO",
            "ADD_TO_FIREWALL A1 A3",
            "SEND A1 A3 HELLO",

            "SEND A1 A4 HELLO"

//            "DISABLE SEND A1",
//            "DISABLE RECEIVE A1",
//            "SEND A1 A2 HELLO",
//
//            "ENABLE SEND A1",
//            "ENABLE RECEIVE A1",
//            "SEND A1 A2 HELLO",
    };
}
