import network.node.Node;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node node;
    @BeforeEach
    void setup(){
        node = new Node("C1", "COMPUTER");
    }

    @Test
    @DisplayName("Create a new computer")
    void testCreateComputer(){
        node = new Node("C1", "COMPUTER");
        assertEquals("C1", node.name);
        assertTrue(node.isComputer);
        assertEquals(5, node.strength);
        assertTrue(node.connectedNodes.isEmpty());
    }


    @Test
    @DisplayName("Create a new repeater")
    void testCreateRepeater(){
        node = new Node("R1", "REPEATER");
        assertEquals("R1", node.name);
        assertFalse(node.isComputer);
        assertEquals(5, node.strength);
        assertTrue(node.connectedNodes.isEmpty());
    }


    @Test
    @DisplayName("Change the strength of a device")
    void testSetStrength(){
        node.setStrength(20);
        assertEquals(20, node.strength);
    }


    @Test
    @DisplayName("Connect two computers")
    void testConnectDevice1() throws Exception {
        node = new Node("C1", "COMPUTER");
        Node desNode = new Node("C2" , "COMPUTER");
        node.connect(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.connect(desNode));
    }


    @Test
    @DisplayName("Connect a computer with repeater")
    void testConnectDevice2() throws Exception {
        node = new Node("C1", "COMPUTER");
        Node desNode = new Node("R1" , "REPEATER");
        node.connect(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.connect(desNode));
    }


    @Test
    @DisplayName("Connect two repeaters")
    void testConnectDevice3() throws Exception {
        node = new Node("R1" , "REPEATER");
        Node desNode = new Node("R2", "REPEATER");
        node.connect(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.connect(desNode));
    }


    @Test
    @DisplayName("Connect a repeater with a computer")
    void testConnectDevice4() throws Exception {
        node = new Node("R1" , "REPEATER");
        Node desNode = new Node("C1", "COMPUTER");
        node.connect(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.connect(desNode));
    }


    @Test
    @DisplayName("Get the route between two devices")
    void testRouteInfo() throws Exception{
        Node node1 = new Node("C1", "COMPUTER");
        Node node2 = new Node("C2", "COMPUTER");
        Node node3 = new Node("C3", "COMPUTER");
        Node node4 = new Node("C4", "COMPUTER");
        Node node5 = new Node("C5", "COMPUTER");
        Node node6 = new Node("C6", "COMPUTER");
        Node node7 = new Node("C7", "COMPUTER");

        node.connect(node1);
        node1.connect(node2);
        node2.connect(node3);
        node3.connect(node4);
        node4.connect(node5);
        node5.connect(node6);
        node6.connect(node7);

        assertEquals(Arrays.asList("C1", "C2", "C3"), node1.routeInfo(node3));
        assertEquals(Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6"), node1.routeInfo(node6));
        assertTrue(node1.routeInfo(node7).isEmpty());  // due to no strength there will be no path
        node1.setStrength(10);
        assertEquals(Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6", "C7"), node1.routeInfo(node7));

        node2.connect(node7);
        assertEquals(Arrays.asList("C1", "C2", "C7"), node1.routeInfo(node7));
    }
}