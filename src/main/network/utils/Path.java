package network.utils;

import java.util.List;

public  class Path{
    private List<String> path;
    public void addNodeNameToPath(String nodeName){
        this.path.add(nodeName);
    }

    public int getNumberOfHops(){
        return path.size() - 1;
    }

    public void printPath(){
        System.out.println(String.join(" -> ", this.path));
    }
}
