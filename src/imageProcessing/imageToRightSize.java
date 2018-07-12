package imageProcessing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class imageToRightSize {

    public static void main(String[] args) {
        File rawImageDir = new File("rawImages");
        File[] fileList = rawImageDir.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            doImage(fileList[i]);
        }
    }

    private static void doImage(File file) {
        String inputImagePath = file.getAbsolutePath();

        try {
            // resize to a fixed width (not proportional)
            int scaledWidth = 24;
            int scaledHeight = 24;

            toBlackAndWhite(ImageResizer.resize(inputImagePath, scaledWidth, scaledHeight), file.getName());

        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }
    }

    private static void toBlackAndWhite(BufferedImage myColorImage, String name) throws IOException {

        BufferedImage myBWImage = new BufferedImage(myColorImage.getWidth(), myColorImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < myColorImage.getWidth(); x++) {
            for (int y = 0; y < myColorImage.getHeight(); y++) {
                if (myColorImage.getRGB(y, x) < -10000000) {
                    //System.out.print(String.format("%-3s", 0));
                    myBWImage.setRGB(y, x, 0xffffffff);
                } else {
                  //  System.out.print(String.format("%-3s", 1));
                    myBWImage.setRGB(y, x, 0x00000000);
                }
            }
            System.out.println();

        }
        ImageIO.write(myBWImage, "bmp", new File("images/"+name));
    }
}

class ImageResizer {

    public static BufferedImage resize(String inputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return outputImage;
    }

}
