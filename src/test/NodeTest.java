import network.node.ComputerNode;
import network.node.Node;
import network.node.RepeaterNode;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node node;
    @BeforeEach
    void setup(){
        node = new ComputerNode("C1");
    }

    @Test
    @DisplayName("Create a new computer")
    void testCreateComputer(){
        node = new ComputerNode("C1");
        assertEquals("C1", node.name);
        assertTrue(node.canBeDestination());
    }


    @Test
    @DisplayName("Create a new repeater")
    void testCreateRepeater(){
        node = new RepeaterNode("R1");
        assertEquals("R1", node.name);
        assertFalse(node.canBeDestination());
    }


    @Test
    @DisplayName("Change the strength of a device")
    void testSetStrength(){
        node.setStrength(20);
        assertEquals(20, node.getStrength());
    }


    @Test
    @DisplayName("Connect two computers")
    void testConnectDevice1() throws Exception {
        Node desNode = new ComputerNode("C2");
        node.makeConnectionTo(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.makeConnectionTo(desNode));
    }


    @Test
    @DisplayName("Connect a computer with repeater")
    void testConnectDevice2() throws Exception {
        Node desNode = new RepeaterNode("R1");
        node.makeConnectionTo(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.makeConnectionTo(desNode));
    }


    @Test
    @DisplayName("Connect two repeaters")
    void testConnectDevice3() throws Exception {
        node = new RepeaterNode("R1");
        Node desNode = new RepeaterNode("R2");
        node.makeConnectionTo(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.makeConnectionTo(desNode));
    }


    @Test
    @DisplayName("Connect a repeater with a computer")
    void testConnectDevice4() throws Exception {
        node = new RepeaterNode("R1");
        Node desNode = new ComputerNode("C1");
        node.makeConnectionTo(desNode);
        assertEquals(1, node.connectedNodes.size());
        assertTrue(node.connectedNodes.containsKey(desNode.name));
        assertEquals(0, desNode.connectedNodes.size());
        assertThrows(Exception.class, () -> node.makeConnectionTo(desNode));
    }


    @Test
    @DisplayName("Get the route between two devices")
    void testRouteInfo() throws Exception{
        Node node1 = new ComputerNode("C1");
        Node node2 = new ComputerNode("C2");
        Node node3 = new ComputerNode("C3");
        Node node4 = new ComputerNode("C4");
        Node node5 = new ComputerNode("C5");
        Node node6 = new ComputerNode("C6");
        Node node7 = new ComputerNode("C7");

        node1.makeConnectionTo(node2);
        node2.makeConnectionTo(node3);
        node3.makeConnectionTo(node4);
        node4.makeConnectionTo(node5);
        node5.makeConnectionTo(node6);
        node6.makeConnectionTo(node7);

        assertEquals(Arrays.asList("C1", "C2", "C3"), node1.getRoute(node3));
        assertEquals(Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6"), node1.getRoute(node6));
        assertThrows(Exception.class, () -> node1.getRoute(node7));  // due to no strength there will be no path
        node1.setStrength(10);
        assertEquals(Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6", "C7"), node1.getRoute(node7));


        node2.makeConnectionTo(node7);
        assertEquals(Arrays.asList("C1", "C2", "C7"), node1.getRoute(node7));
    }


    @Test
    @DisplayName("Disable commands")
    void testDisableCommands() throws Exception {
        Node desNode = new ComputerNode("C2");
        node.makeConnectionTo(desNode);

        assertTrue(node.canSend);
        assertTrue(node.canReceive);

        node.disableReceive();
        node.disableSend();


        assertFalse(node.canSend);
        assertFalse(node.canReceive);
    }


    @Test
    @DisplayName("Add to firewall")
    void testAddToFirewall() throws Exception {
        Node desNode = new ComputerNode("C2");
        node.makeConnectionTo(desNode);

        assertDoesNotThrow(() -> node.sendMessage(desNode, "Hello"));

        node.addToBlacklist(desNode.name);

        assertThrows(Exception.class, () -> node.sendMessage(desNode, "Hello"));
    }
}