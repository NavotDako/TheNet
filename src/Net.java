import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Net {
    public static List<Layer> layerList;
    static Layer outputLayer;
    static int nodeNum;
    static int conNum;

    public Net(int layerNum, int nodeNum, int outputLayerNodeNum, int conNum) {
        this.nodeNum = nodeNum;
        this.conNum = conNum;
        layerList = new ArrayList<>();
        for (int i = 0; i < layerNum; i++) {
            layerList.add(new Layer(nodeNum, i));
        }

        CreateConnections(conNum);
        CreateOutputLayer(outputLayerNodeNum);
    }

    public Net(String pathToCSV) throws FileNotFoundException {
        FileReader fr = new FileReader(new File(pathToCSV));

    }

    private List<String> readFile(String filename) {
        List<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            reader.close();
            return records;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }

    private void CreateOutputLayer(int outputLayerNodeNum) {
        outputLayer = new Layer(outputLayerNodeNum, layerList.size());

        for (int j = 0; j < layerList.get(layerList.size() - 1).nodeMat.length; j++) {
            for (int k = 0; k < layerList.get(layerList.size() - 1).nodeMat.length; k++) {
                layerList.get(layerList.size() - 1).nodeMat[j][k].createConnectionsToOutputLayer(layerList.size());
            }
        }
    }

    private void CreateConnections(int conNum) {
        for (int i = 0; i < layerList.size() - 1; i++) {
            for (int j = 0; j < layerList.get(i).nodeMat.length; j++) {
                for (int k = 0; k < layerList.get(i).nodeMat.length; k++) {
                    layerList.get(i).nodeMat[j][k].createConnections(conNum);
                }
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < layerList.size(); i++) {
            s += "layer " + i + "\n";
            s += layerList.get(i).toString();
        }
        s += "Output Layer\n" + outputLayer.toString();
        return s;
    }

    public static Node getNodeFromTopLayer(Node baseNode) {
        boolean found = false;
        Node targetNode = null;
        Random randomeElement = new Random();
        int x_change = 0, y_change = 0;
        while (!found) {
            try {
                targetNode = layerList.get(baseNode.layerIndex + 1).nodeMat[baseNode.coor.x_coor + x_change][baseNode.coor.y_coor + y_change];
                if (!baseNode.isConnected(targetNode)) {
                    found = true;
                } else {
                    x_change = (randomeElement.nextInt(3)) - 1;
                    y_change = (randomeElement.nextInt(3)) - 1;

                }
            } catch (Exception e) {
                x_change = (randomeElement.nextInt(3)) - 1;
                y_change = (randomeElement.nextInt(3)) - 1;
            }


        }

        return targetNode;
    }

    public static Node getNodeFromOutputLayer(Node node) {
        if (node.coor.x_coor < (nodeNum / 2) && node.coor.y_coor < (nodeNum / 2))
            return outputLayer.nodeMat[0][0];

        if (node.coor.x_coor >= (nodeNum / 2) && node.coor.y_coor < (nodeNum / 2))
            return outputLayer.nodeMat[1][0];

        if (node.coor.x_coor < (nodeNum / 2) && node.coor.y_coor >= (nodeNum / 2))
            return outputLayer.nodeMat[0][1];

        return outputLayer.nodeMat[1][1];
    }

    public void saveNetToCSV(String savedNets) throws IOException {
        File csvFile = new File(savedNets + "/" + System.currentTimeMillis() + ".csv");
        FileWriter fw = new FileWriter(csvFile);

        String s = String.format("%-5s,%-5s,%-5s,%-5s,%-5s,%-5s", "L", "X", "Y", "val", "top", "c_val") + "\n";

        for (int i = 0; i < layerList.size(); i++) {
            s += layerList.get(i).toCSV();
        }
        fw.append(s);
        fw.append("\n");
        fw.append(outputLayer.toCSV());
        fw.flush();
        fw.close();

    }

    public String printAllConnection() {
        String s = String.format("%-5s,%-5s,%-5s,%-5s,%-5s,%-5s,%-5s,%-5s", "L", "X", "Y", "val", "verge", "tempSum", "top", "c_val") + "\n";

        for (int i = 0; i < layerList.size(); i++) {
            s += layerList.get(i).toCSV();
        }

        s += outputLayer.toCSV();

        return s;
    }

    public void fireUp() {
        for (int i = 0; i < layerList.size(); i++) {
            layerList.get(i).fire();
            //  System.out.println(printAllConnection());
        }
    }
}
