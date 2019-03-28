package com.dawn.maple.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageMergeTest {

    public static void main(String[] args) {

        try {
            String bigPath = "C:\\Users\\yuliming\\Desktop\\diy\\dilireba.jpg";

            String middlePath = "C:\\Users\\yuliming\\Desktop\\diy\\qq.jpg";

            String smallPath = "C:\\Users\\yuliming\\Desktop\\diy\\timg.jpg";

            String outFile = "C:\\Users\\yuliming\\Desktop\\diy\\complete.jpg";

            BufferedImage big = ImageIO.read(new File(bigPath));

            BufferedImage middle = ImageIO.read(new File(middlePath));

            BufferedImage small = ImageIO.read(new File(smallPath));

            Graphics2D g = middle.createGraphics();

            int x = (middle.getWidth() - small.getWidth()) / 2;

            int y = (middle.getHeight() - small.getHeight()) / 2;

            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);

            Graphics2D gb = big.createGraphics();

            int xb = (big.getWidth() - middle.getWidth()) / 2;

            int yb = (big.getHeight() - middle.getHeight()) / 2;

            gb.drawImage(middle, xb, yb, middle.getWidth(), middle.getHeight(),

                    null);

            gb.dispose();

            ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }


}
