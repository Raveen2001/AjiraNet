package network.utils;

import network.node.Node;

import java.util.*;



public class PathFinder {
    private Node fromNode;
    private Node toNode;
    boolean isFirewallApplied;

    public PathFinder(Node fromNode, Node toNode, boolean isFirewallApplied){
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.isFirewallApplied = isFirewallApplied;
    }

    public Path getPath() throws Exception{
        if(!toNode.canBeDestination()) throw new Exception("Route cannot be calculated with a repeater.");

        Node curNode = fromNode;

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
                return getPath(previousNodes, toNode);
            }

            int strengthTillNow = strengthTillNode.get(curNode);
            for(var connectedNode: curNode.connectedNodes.values()){
                if(!visitedNodes.contains(connectedNode) && canNodeSurvive(connectedNode, strengthTillNow)){

                    // either firewall is to be applied
                    // or curNode can send message to the connected node
                    boolean canGoToConnectedNode = !isFirewallApplied || canSendMessageBetween(curNode, connectedNode);

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

    private Path getPath(Map<Node, Node> previousNodes, Node destinationNode){
        Stack<String> stack = new Stack<>();
        stack.push(destinationNode.name);

        Node previous = previousNodes.get(destinationNode);
        while (previous != null){
            stack.push(previous.name);
            previous = previousNodes.get(previous);
        }

        Path path = new Path();
        while (!stack.isEmpty()){
            path.addNodeNameToPath(stack.pop());
        }
        return path;
    }

    private boolean canNodeSurvive(Node toNode, int strengthTillNow) {
        return toNode.getUpdatedStrength(strengthTillNow) >= 0;
    }

    private boolean canSendMessageBetween(Node fromNode, Node toNode) {
        return (fromNode.canSend && toNode.canReceive && !fromNode.hasBlacklisted(toNode.name));
    }
}
