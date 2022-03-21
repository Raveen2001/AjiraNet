import java.util.*;

public class Node{
    private final String name;
    final boolean isComputer;
    private int strength;
    private final Map<String, Node> connectedNodes;


    private class RouteNode {
        Node node;
        int strengthTillNow;
        RouteNode previousNode;

        RouteNode(Node node, RouteNode previousNode, int strength){
            this.node = node;
            this.previousNode = previousNode;
            this.strengthTillNow = strength;
        }
    }


    public Node(String name, String isComputer){
        this.name = name;
        this.isComputer = isComputer.equals("COMPUTER");
        this.strength = 5;
        this.connectedNodes = new HashMap<>();
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
        RouteNode enhanced = new RouteNode(this, null, strength);

        Queue<RouteNode> pathNodes = new LinkedList<>();
        pathNodes.offer(enhanced);
        Set<String> visitedNodes = new HashSet<>();
        visitedNodes.add(name);

        List<String> route = routeInfo(destination, pathNodes, visitedNodes, strength);
        Collections.reverse(route);
        return route;
    }

    private ArrayList<String> routeInfo(Node destinationNode, Queue<RouteNode> pathNodes, Set<String> visitedNodes, int strength){
        if(pathNodes.isEmpty()) return new ArrayList<>();

        RouteNode enhancedNode = pathNodes.poll();
        Node curNode = enhancedNode.node;
        int strengthTillNow = enhancedNode.strengthTillNow;

        System.out.println(strengthTillNow);

        if(curNode.equals(destinationNode)){
            ArrayList<String> route = new ArrayList<>();
            route.add(curNode.name);
            RouteNode previousNode = enhancedNode.previousNode;
            while(previousNode != null){
                route.add(previousNode.node.name);
                previousNode = previousNode.previousNode;
            }
            return  route;
        }

        for(Map.Entry<String, Node> entry: curNode.connectedNodes.entrySet()){
            if(!visitedNodes.contains(entry.getKey())){
                Node child = entry.getValue();
                if (canNodeSurvive( child, destinationNode, strengthTillNow)){
                    RouteNode enhancedChild = new RouteNode(child, enhancedNode, getStrength(child, strengthTillNow));
                    pathNodes.offer(enhancedChild);
                    visitedNodes.add(child.name);
                }
            }
        }
        return routeInfo(destinationNode, pathNodes, visitedNodes, strength);
    }

    private boolean canNodeSurvive(Node child, Node destination, int strengthTillNow){
        strengthTillNow = getStrength(child, strengthTillNow);
        return strengthTillNow != 0 || child.equals(destination);
    }

    private int getStrength(Node child, int strengthTillNow){
        if(child.isComputer){
            strengthTillNow --;
        }else{
            strengthTillNow *= 2;
        }
        return strengthTillNow;
    }

    private boolean isAlreadyConnected(String name){
        return connectedNodes.containsKey(name);
    }

    @Override
    public boolean equals(Object o) {
        Node node = (Node) o;
        return name.equals(node.name);
    }
}