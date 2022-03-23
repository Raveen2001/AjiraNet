package network.node;

import java.util.*;


public class Node{
    public final String name;
    public final boolean isComputer;
    public int strength;
    public boolean canSend;
    public boolean canReceive;
    public final Map<String, Node> connectedNodes;
    public Map<String, Node> blacklistedNodes;


    public Node(String name, String nodeType){
        this.name = name;
        this.isComputer = nodeType.equals("COMPUTER");
        this.strength = 5;
        this.canSend = true;
        this.canReceive = true;
        this.connectedNodes = new HashMap<>();
        this.blacklistedNodes = new HashMap<>();
    }

    public Node(Node node){
        name = node.name;
        isComputer = node.isComputer;
        strength = node.strength;
        connectedNodes = node.connectedNodes;
        canReceive = node.canReceive;
        canSend = node.canSend;
        blacklistedNodes = node.blacklistedNodes;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void connect(Node destination) throws Exception{
        if(isAlreadyConnected(destination.name)){
            throw new Exception("Devices are already connected.");
        }
        connectedNodes.put(destination.name, destination);
    }

    public void printRoute(Node destination) throws Exception{
        if(!destination.isComputer ) throw new Exception("Route cannot be calculated with a repeater.");

        Queue<Node> pathNodes = new LinkedList<>();
        pathNodes.offer(this);
        Set<Node> visitedNodes = new HashSet<>();
        visitedNodes.add(this);

        Router route = getRoute(destination, pathNodes, visitedNodes, new Router());
        route.printRoute();
    }

    public Router getRoute(Node destinationNode, Queue<Node> pathNodes, Set<Node> visitedNodes, Router route) throws Exception{
        if(pathNodes.isEmpty()) throw new Exception("No Route Found.");

        Node curNode = pathNodes.poll();

        Router newRoute = new Router(route, curNode);
        if(curNode.equals(destinationNode)) return new Router(route, curNode);

        int strengthTillNow = newRoute.getStrengthTillNow();
        Map<String, Node> connectedNodes = curNode.connectedNodes;

        for(Map.Entry<String, Node> entry: connectedNodes.entrySet()){
            Node childNode = entry.getValue();
            if (canNodeSurvive(childNode, destinationNode, strengthTillNow) && !visitedNodes.contains(childNode)) {
                pathNodes.offer(childNode);
                visitedNodes.add(childNode);
            }
        }
        return getRoute(destinationNode, pathNodes, visitedNodes, newRoute);
    }




    public void sendMessage(Node destination, String message) throws Exception{
        if(!destination.isComputer ) throw new Exception("Route cannot be calculated with a repeater.");

        Queue<Node> pathNodes = new LinkedList<>();
        pathNodes.offer(this);
        Set<Node> visitedNodes = new HashSet<>();
        visitedNodes.add(this);

        Router sentRoute = getSentPath(destination, pathNodes, visitedNodes, new Router());
        int hops = sentRoute.getHops();
        if(hops < 0){
            throw new Exception("Can't send to destination.");
        }
        sentRoute.printRoute();
        System.out.println("Received Message : " + message);
        System.out.println("Hops: " + hops);
    }

    public Router getSentPath(Node destinationNode, Queue<Node> pathNodes, Set<Node> visitedNodes, Router route) throws Exception{
        if(pathNodes.isEmpty()) throw new Exception("No Route Found.");

        Node curNode = pathNodes.poll();
        Router newRoute = new Router(route, curNode);
        if(curNode.equals(destinationNode)) return new Router(route, curNode);

        int strengthTillNow = newRoute.getStrengthTillNow();
        Map<String, Node> connectedNodes = curNode.connectedNodes;

        for(Map.Entry<String, Node> entry: connectedNodes.entrySet()){

            Node childNode = entry.getValue();
            if (canNodeSurvive(childNode, destinationNode, strengthTillNow) && canSendMessageBetweenNode(curNode, childNode) && !visitedNodes.contains(childNode)) {
                pathNodes.offer(childNode);
                visitedNodes.add(childNode);
            }
        }
        return getSentPath(destinationNode, pathNodes, visitedNodes, newRoute);
    }

    private boolean canSendMessageBetweenNode(Node curNode, Node child) {
        return curNode.canSend && child.canReceive && !curNode.hasBlacklisted(child);
    }

    public void setCanSend(boolean canSend){
        this.canSend = canSend;
    }

    public void setCanReceive(boolean canReceive){
        this.canReceive = canReceive;
    }

    public boolean hasBlacklisted(Node node){
        return blacklistedNodes.containsKey(node.name);
    }

    private boolean isDestinationNode(Router curRouteNode, Node destinationNode){
        return curRouteNode.equals(destinationNode);
    }

    private boolean isAlreadyConnected(String name){
        return connectedNodes.containsKey(name);
    }

    private boolean canNodeSurvive(Node child, Node destination, int strengthTillNow){
        strengthTillNow = getStrength(child, strengthTillNow);
        return strengthTillNow != 0 || child.equals(destination);
    }


    private int getStrength(Node child, int strengthTillNow){
        return child.isComputer ? strengthTillNow - 1 : strengthTillNow * 2;
    }



    @Override
    public boolean equals(Object o) {
        Node node = (Node) o;
        return name.equals(node.name);
    }

    @Override
    public String toString() {
        return name;
    }
}