import java.util.*;

public class Node{
    final String name;
    final boolean isComputer;
    int strength;
    final Map<String, Node> connectedNodes;

    public Node(String name, String nodeType){
        this.name = name;
        this.isComputer = nodeType.equals("COMPUTER");
        this.strength = 5;
        this.connectedNodes = new HashMap<>();
    }

    public Node(Node node){
        name = node.name;
        isComputer = node.isComputer;
        strength = node.strength;
        connectedNodes = node.connectedNodes;
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

    List<String> routeInfo(Node destination){
        RouteNode routeNode = new RouteNode(this, null, strength);
        Queue<RouteNode> pathNodes = new LinkedList<>();
        pathNodes.offer(routeNode);
        Set<RouteNode> visitedNodes = new HashSet<>();
        visitedNodes.add(routeNode);

        return routeInfo(destination, pathNodes, visitedNodes);
    }

    private List<String> routeInfo(Node destinationNode, Queue<RouteNode> pathNodes, Set<RouteNode> visitedNodes){
        if(pathNodes.isEmpty()) return new ArrayList<>();

        RouteNode curRouteNode = pathNodes.poll();
        if(isDestinationNode(curRouteNode, destinationNode)) return getRoute(curRouteNode);

        int strengthTillNow = curRouteNode.strengthTillNow;
        Map<String, Node> connectedNodes = curRouteNode.connectedNodes;

        for(Map.Entry<String, Node> entry: connectedNodes.entrySet()){
            Node child = entry.getValue();
            RouteNode childRouteNode = new RouteNode(child, curRouteNode, getStrength(child, strengthTillNow));

            if (canNodeSurvive(child, destinationNode, strengthTillNow) && !visitedNodes.contains(childRouteNode)) {
                pathNodes.offer(childRouteNode);
                visitedNodes.add(childRouteNode);
            }
        }
        return routeInfo(destinationNode, pathNodes, visitedNodes);
    }

    private List<String> getRoute(RouteNode routeNode){
        ArrayList<String> route = new ArrayList<>();
        route.add(routeNode.name);
        RouteNode previousNode = routeNode.previousNode;
        while(previousNode != null){
            route.add(previousNode.name);
            previousNode = previousNode.previousNode;
        }
        Collections.reverse(route);
        return route;
    }

    private boolean isDestinationNode(RouteNode curRouteNode, Node destinationNode){
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
}