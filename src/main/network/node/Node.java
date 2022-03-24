package network.node;

import java.util.*;


public abstract class Node{
    public final String name;
    protected int strength;
    public boolean canSend;
    public boolean canReceive;
    public final Map<String, Node> connectedNodes;
    public List<String> blacklistedNodes;


    public Node(String name){
        this.name = name;
        this.canSend = true;
        this.canReceive = true;
        this.connectedNodes = new HashMap<>();
        this.blacklistedNodes = new ArrayList<>();
    }

    public abstract int getUpdatedStrength(int strength);
    public abstract boolean canBeDestination();


    public int getStrength(){
        return this.strength;
    }


    public void setStrength(int strength){
        this.strength = strength;
    }


    public void makeConnectionTo(Node destination) throws Exception{
        boolean isSameSourceAndDestination = this.equals(destination);
        if(isSameSourceAndDestination){
            throw new Exception("Cannot connect device to itself.");
        }

        if(connectedNodes.containsKey(destination.name)){
            throw new Exception("Devices are already connected.");
        }
        connectedNodes.put(destination.name, destination);
    }

    public List<String> getRoute(Node toNode) throws Exception{
        if(!toNode.canBeDestination()) throw new Exception("Route cannot be calculated with a repeater.");

        return getRoute(toNode, false);
    }

    private List<String> getRoute(Node toNode, boolean isFirewallApplied) throws Exception{
        Node curNode = this;

        // queue is used for bfs.
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(curNode);

        // visitedNodes is used to keep track of the visited nodes,so we don't run into infinite loop
        Set<Node> visitedNodes = new HashSet<>();

        Map<Node, Node> previousNodes = new HashMap<>();
        Map<Node, Integer> strengthTillNode = new HashMap<>();

        previousNodes.put(curNode, null);
        strengthTillNode.put(curNode, curNode.strength);

        while(!queue.isEmpty()){
            curNode = queue.poll();

            boolean isDestinationNode = curNode.equals(toNode);
            if(isDestinationNode){
                return getRoute(previousNodes, toNode);
            }

            int strengthTillNow = strengthTillNode.get(curNode);
            for(var connectedNode: curNode.connectedNodes.values()){
                if(!visitedNodes.contains(connectedNode) && canNodeSurvive(connectedNode, strengthTillNow)){

                    // either firewall is to be applied
                    // or curNode can send message to the connected node
                    boolean canGoToConnectedNode = !isFirewallApplied || curNode.canSendMessageTo(connectedNode);

                    if(canGoToConnectedNode){
                        queue.offer(connectedNode);
                        previousNodes.put(connectedNode, curNode);
                        strengthTillNode.put(connectedNode, connectedNode.getUpdatedStrength(strengthTillNow));
                    }
                }
            }
            visitedNodes.add(curNode);
        }

        throw new Exception("No route found.");
    }


    public void sendMessage(Node toNode, String message) throws Exception{
        if(this.hasBlacklisted(toNode.name) || toNode.hasBlacklisted(this.name)){
            throw new Exception("Message can't be sent since node is blocked.");
        }

        List<String> path = getRoute(toNode, true);
        int hops = path.size() - 1;
        System.out.println("Received Message : " + message);
        System.out.println("Hops: " + hops);
    }


    public void enableSend(){
        this.canSend = true;
    }

    public void disableSend(){
        this.canSend = false;
    }

    public void enableReceive(){
        this.canReceive = true;
    }

    public void disableReceive(){
        this.canReceive = false;
    }


    // utility functions

    private boolean canNodeSurvive(Node toNode, int strengthTillNow) {
        return toNode.getUpdatedStrength(strengthTillNow) >= 0;
    }

    private boolean canSendMessageTo(Node node) {
        return (this.canSend && node.canReceive && !this.hasBlacklisted(node.name));
    }

    public void addToBlacklist(String name){
        blacklistedNodes.add(name);
    }

    public boolean hasBlacklisted(String name){
        return blacklistedNodes.contains(name);
    }

    private boolean isAlreadyConnected(String name){
        return connectedNodes.containsKey(name);
    }

    private List<String> getRoute(Map<Node, Node> previousNodes, Node destinationNode){
        List<String> path = new ArrayList<>();
        path.add(destinationNode.name);

        Node previous = previousNodes.get(destinationNode);
        while (previous != null){
            path.add(previous.name);
            previous = previousNodes.get(previous);
        }

        Collections.reverse(path);
        return path;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || getClass() != o.getClass()) return false;
        if(o == this) return true;

        Node node = (Node) o;
        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
    
}