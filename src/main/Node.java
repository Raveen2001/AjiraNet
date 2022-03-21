import java.util.*;

public class Node{
    final String name;
    final boolean isComputer;
    int strength;
    final Map<String, Node> connectedNodes;

    public Node(String name, String isComputer){
        this.name = name;
        this.isComputer = isComputer.equals("COMPUTER");
        this.strength = 5;
        this.connectedNodes = new HashMap<>();
    }

    public Node(Node node){
        name = node.name;
        isComputer = node.isComputer;
        strength = node.strength;
        connectedNodes = node.connectedNodes;
    }

    void setStrength(int strength) {
        this.strength = strength;
    }

    void connect(Node destination) throws Exception{
        if(isAlreadyConnected(destination.name)){
            throw new Exception("Devices are already connected.");
        }
        connectedNodes.put(destination.name, destination);
    }

    List<String> routeInfo(Node destination){
        RouteNode routeNode = new RouteNode(this, null, strength);

        Queue<RouteNode> pathNodes = new LinkedList<>();
        pathNodes.offer(routeNode);
        Set<String> visitedNodes = new HashSet<>();
        visitedNodes.add(name);

        List<String> route = routeInfo(destination, pathNodes, visitedNodes, strength);
        Collections.reverse(route);
        return route;
    }

    private ArrayList<String> routeInfo(Node destinationNode, Queue<RouteNode> pathNodes, Set<String> visitedNodes, int strength){
        if(pathNodes.isEmpty()) return new ArrayList<>();

        RouteNode curRouteNode = pathNodes.poll();
        int strengthTillNow = curRouteNode.strengthTillNow;

        if(curRouteNode.equals(destinationNode)){
            ArrayList<String> route = new ArrayList<>();
            route.add(curRouteNode.name);
            RouteNode previousNode = curRouteNode.previousNode;
            while(previousNode != null){
                route.add(previousNode.name);
                previousNode = previousNode.previousNode;
            }
            return  route;
        }

        for(Map.Entry<String, Node> entry: curRouteNode.connectedNodes.entrySet()){
            if(!visitedNodes.contains(entry.getKey())){
                Node child = entry.getValue();
                if (canNodeSurvive( child, destinationNode, strengthTillNow)){
                    RouteNode childRouteNode = new RouteNode(child, curRouteNode, getStrength(child, strengthTillNow));
                    pathNodes.offer(childRouteNode);
                    visitedNodes.add(child.name);
                }
            }
        }
        return routeInfo(destinationNode, pathNodes, visitedNodes, strength);
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