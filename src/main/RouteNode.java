public class RouteNode  extends Node{
    int strengthTillNow;
    RouteNode previousNode;

    RouteNode(Node node, RouteNode previousNode, int strength){
        super(node);
        this.previousNode = previousNode;
        this.strengthTillNow = strength;
    }
}