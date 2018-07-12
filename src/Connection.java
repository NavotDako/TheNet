import java.util.Random;

public class Connection {
    public Node baseNode;
    public Node topNode;
    public int connectionId;
    int connectionLayerIndex;
    double VAL;
    public Connection(Node baseNode, Node topNode,int connectionLayerIndex) {
        this.topNode = topNode;
        this.baseNode = baseNode;
        this.connectionLayerIndex = connectionLayerIndex;
        VAL = Main.round((new Random().nextDouble()),2);
        System.out.println("new connection: " + baseNode.toString()+ " to: "+topNode.toString() + " with VAL: "+VAL);

    }
}
