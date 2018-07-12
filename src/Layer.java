import java.awt.image.BufferedImage;

public class Layer {
    Node[][] nodeMat;
    int layerIndex;

    public Layer(int nodeNum, int layerIndex) {
        this.layerIndex = layerIndex;
        nodeMat = new Node[nodeNum][nodeNum];
        for (int i = 0; i < nodeNum; i++) {
            for (int j = 0; j < nodeNum; j++) {
                nodeMat[i][j] = new Node(i, j, layerIndex);
            }
        }
    }
    public Layer(int nodeNum, int layerIndex, BufferedImage bf) {
        this.layerIndex = layerIndex;
        nodeMat = new Node[nodeNum][nodeNum];
        for (int i = 0; i < nodeNum; i++) {
            for (int j = 0; j < nodeNum; j++) {
                nodeMat[i][j] = new Node(i, j, layerIndex);
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < nodeMat.length; i++) {
            for (int j = 0; j < nodeMat.length; j++) {
                s += String.format("%-5s", nodeMat[i][j].val);
            }
            s += "\n";
        }
        return s;
    }

    public String toCSV() {
        String s = "";
        for (int i = 0; i < nodeMat.length; i++) {
            for (int j = 0; j < nodeMat.length; j++) {
                s += nodeMat[i][j].toCSV();
            }
        }
        return s;
    }
}
