package network.node;

import java.util.ArrayList;
import java.util.List;

public class Router {
    List<Node> route = new ArrayList<>();

    public Router(){}

    public Router(Router routeTillNow, Node curNode){
        route.addAll(routeTillNow.route);
        route.add(curNode);
    }

    int getStrengthTillNow(){
        if(route.isEmpty()) return Integer.MAX_VALUE;
        int strength = route.get(0).strength;
        for(int i = 1; i < route.size(); i++){
            Node pathNode = route.get(i);
            strength = pathNode.isComputer? strength -1 : strength*2;
        }
        return strength;
    }

    void printRoute(){
        System.out.println(route);
    }

    int getHops(){
        return route.size() - 1;
    }
}