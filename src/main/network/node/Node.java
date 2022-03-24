package network.node;

import java.util.*;


public abstract class Node{
    public final String name;
    public int strength;
    public boolean canSend = true;
    public boolean canReceive = true;
    public final Map<String, Node> connectedNodes =  new HashMap<>();
    public final List<String> blacklistedNodes = new ArrayList<>();


    public Node(String name){
        this.name = name;
    }

    public abstract int getUpdatedStrength(int strength);
    public abstract boolean canBeDestination();

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

    public void addToBlacklist(String name) throws Exception{
        if(this.hasBlacklisted(name)){
            throw new Exception("Node already blocked");
        }
        this.blacklistedNodes.add(name);
    }

    public void setCanSend(boolean val){
        this.canSend = val;
    }

    public void setCanReceive(boolean val){
        this.canReceive = val;
    }

    public boolean hasBlacklisted(String name){
        return blacklistedNodes.contains(name);
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