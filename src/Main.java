import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private static int layerNum = 3;
    private static int nodeNum = 24;
    private static int outputLayerNodeNum = 2;
    private static int conNum = 4;
    private static Net net;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Building The Net..");
       net = new Net(layerNum, nodeNum, outputLayerNodeNum, conNum);
       // net.saveNetToCSV("savedNets");
//        Net newNet = new Net("savedNets/1531807192005.csv");
//        System.out.println(net);

        int result = getResultForImage("images/01.png");
        System.out.println("result: " + result);

    }

    private static int getResultForImage(String pathToImage) throws IOException {

        Layer inputLayer = createInputLayer(pathToImage);


        //System.out.println(inputLayer);

        inputLayer.fireInputLayer(net.layerList.get(0));

        net.fireUp();

        System.out.println(net.printAllConnection());

        return calcOutput();
    }

    private static int calcOutput() {

        return 0;
    }

    private static Layer createInputLayer(String pathToFile) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(new File(pathToFile));
        return new Layer(bufferedImage.getHeight(), layerNum, bufferedImage);
    }

    public static double toNDecimalPlaces(double value, int places) {
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