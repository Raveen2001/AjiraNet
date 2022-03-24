package network.utils;

import java.util.ArrayList;
import java.util.List;

public  class Path{
    private List<String> path = new ArrayList<>();
    public void addNodeNameToPath(String nodeName){
        this.path.add(nodeName);
    }

    public int getNumberOfHops(){
        return path.size() - 1;
    }

    public void printPath(){
        System.out.println(String.join(" -> ", this.path));
    }

    // for testing
    public List<String> getPath(){
        return path;
    }
}
