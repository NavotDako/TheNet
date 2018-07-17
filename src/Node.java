import java.util.*;

public class Node {
    int layerIndex;
    double val;
    Coordinates coor;
    List<Connection> connectionList = new ArrayList<>();
    public boolean shouldFire = false;
    public double tempSum = 0;
    private double verge;

    public Node(int x, int y, int layerIndex) {
        this.layerIndex = layerIndex;
        coor = new Coordinates(x, y);
        val = 0.5;
        verge = 0.8;
        //toNDecimalPlaces((new Random()).nextDouble(), 2);
    }

    public Node(int x, int y, int layerIndex, int rgb) {
        this.layerIndex = layerIndex;
        coor = new Coordinates(x, y);
        val = (rgb < -1000000) ? 0 : 1;
        verge = 0.8;
    }


    public void createConnections(int connectionNum) {
        for (int i = 0; i < connectionNum; i++) {
            connectionList.add(new Connection(this, Net.getNodeFromTopLayer(this), layerIndex));
        }
    }


    public boolean isConnected(Node targetNode) {

        for (Connection c : connectionList) {
            if (c.topNode.equals(targetNode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (((Node) obj).layerIndex == this.layerIndex && ((Node) obj).coor.x_coor == this.coor.x_coor && ((Node) obj).coor.y_coor == this.coor.y_coor)
            return true;
        else return false;
    }

    @Override
    public String toString() {
        return "Node: " + layerIndex + "_" + coor.x_coor + "_" + coor.y_coor + " : " + tempSum;
    }

    public void createConnectionsToOutputLayer(int connectionLayerIndex) {

        connectionList.add(new Connection(this, Net.getNodeFromOutputLayer(this), connectionLayerIndex));


    }

    public String toCSV() {
        String s = "";
        for (int i = 0; i < connectionList.size(); i++) {
            s += String.format("%-5s,%-5s,%-5s,%-5s,%-5s,%-5s,%-5s,%-5s",
                    layerIndex,
                    coor.x_coor,
                    coor.y_coor,
                    val,
                    verge,
                    Main.toNDecimalPlaces(tempSum, 3),
                    connectionList.get(i).topNode.coor.x_coor + "_" + connectionList.get(i).topNode.coor.y_coor,
                    connectionList.get(i).VAL);

            s += "\n";
        }
        return s;
    }

    public void updateShouldFire() {
        if (tempSum > verge) shouldFire = true;
    }
}


