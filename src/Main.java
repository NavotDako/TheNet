import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private static int layerNum = 3;
    private static int nodeNum = 10;
    private static int outputLayerNodeNum = 2;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Building The Net..");
        Net net = CreateNet(layerNum, nodeNum, outputLayerNodeNum);
        net.saveNetToCSV("savedNets");
//        System.out.println(net);

//        int result = getResultForImage("");
//        System.out.println("result: " +result);

    }

    private static int getResultForImage(String pathToFile) throws IOException {

        Layer inputLayer = createInputLayer(pathToFile);

        return calcOutput();
    }

    private static int calcOutput() {
        return 0;
    }

    private static Layer createInputLayer(String pathToFile) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(new File(pathToFile));
        return new Layer(bufferedImage.getHeight(), layerNum, bufferedImage);
    }

    private static Net CreateNet(int layerNum, int nodeNum, int outputLayerNodeNum) {

        return new Net(layerNum, nodeNum, outputLayerNodeNum);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}

class Coordinates {
    int x_coor, y_coor;

    public Coordinates(int x, int y) {
        x_coor = x;
        y_coor = y;
    }
}