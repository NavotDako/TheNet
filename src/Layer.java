import java.awt.image.BufferedImage;

public class Layer {
    Node[][] nodeMat;
    int layerIndex;

    public Layer(int nodeNum, int layerIndex) {
        this.layerIndex = layerIndex;
        nodeMat = new Node[nodeNum][nodeNum];
        for (int x = 0; x < nodeNum; x++) {
            for (int y = 0; y < nodeNum; y++) {
                nodeMat[y][x] = new Node(x, y, layerIndex);
            }
        }
    }

    public Layer(int nodeNum, int layerIndex, BufferedImage bf) {
        this.layerIndex = layerIndex;
        nodeMat = new Node[nodeNum][nodeNum];
        for (int x = 0; x < bf.getWidth(); x++) {
            for (int y = 0; y < bf.getHeight(); y++) {
                nodeMat[y][x] = new Node(x, y, layerIndex, bf.getRGB(x, y));
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < nodeMat.length; i++) {
            for (int j = 0; j < nodeMat.length; j++) {
                s += String.format("%-2s", nodeMat[i][j].val);
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

    public void fireInputLayer(Layer layer) {
        for (int x = 0; x < nodeMat.length; x++) {
            for (int y = 0; y < nodeMat.length; y++) {
                if (this.nodeMat[x][y].val > 0) {
                    layer.nodeMat[x][y].shouldFire = true;
                    layer.nodeMat[x][y].tempSum = 1;
                }
            }
        }
    }

    public void fire() {
        for (int x = 0; x < nodeMat.length; x++) {
            for (int y = 0; y < nodeMat.length; y++) {
                if (this.nodeMat[x][y].shouldFire) {
                    for (int i = 0; i < this.nodeMat[x][y].connectionList.size(); i++) {
                        this.nodeMat[x][y].connectionList.get(i).topNode.tempSum +=
                                this.nodeMat[x][y].connectionList.get(i).VAL * this.nodeMat[x][y].val;
                        this.nodeMat[x][y].connectionList.get(i).topNode.updateShouldFire();
                    }
                }

            }
        }
    }
}
