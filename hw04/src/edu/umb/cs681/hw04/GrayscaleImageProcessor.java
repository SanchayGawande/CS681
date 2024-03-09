package edu.umb.cs681.hw04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GrayscaleImageProcessor {

    static class Color {
        private int redScale;
        private int greenScale;
        private int blueScale;

        public Color(int red, int green, int blue) {
            this.redScale = red;
            this.greenScale = green;
            this.blueScale = blue;
        }

        public int red() {
            return redScale;
        }

        public int green() {
            return greenScale;
        }

        public int blue() {
            return blueScale;
        }
    }

    static class Image {
        private int width;
        private int height;
        private List<List<Color>> pixels;

        public Image(int width, int height) {
            this.width = width;
            this.height = height;
            pixels = new ArrayList<>();
            for (int i = 0; i < height; i++) {
                ArrayList<Color> row = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    row.add(null);
                }
                pixels.add(row);
            }
        }

        public int height() {
            return height;
        }

        public int width() {
            return width;
        }

        public List<List<Color>> pixels() {
            return pixels;
        }

        public void setColor(int x, int y, Color color) {
            if (x < width && y < height) {
                pixels.get(y).set(x, color);
            }
        }

        public Color getColor(int x, int y) {
            return pixels.get(y).get(x);
        }
    }

    public static void main(String[] args) {
        Image origImg = new Image(4, 3);
        System.out.println("Old Image Pixels:");
        for (int i = 0; i < origImg.height(); i++) {
            for (int j = 0; j < origImg.width(); j++) {
                int blue = i * j;
                origImg.setColor(j, i, new Color(j, i, blue));
                System.out.print("{" + j + "," + i + "," + blue + "} ");
            }
            System.out.println();
        }

        List<List<Color>> newPixels = origImg.pixels().stream()
                .flatMap(List::stream)
                .map(pixColor -> {
                    int avg = (pixColor.red() + pixColor.green() + pixColor.blue()) / 3;
                    return new Color(avg, avg, avg);
                })
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            List<List<Color>> twoDList = new ArrayList<>();
                            for (int i = 0; i < origImg.height(); i++) {
                                int fromIndex = i * origImg.width();
                                int toIndex = (i + 1) * origImg.width();
                                twoDList.add(list.subList(fromIndex, toIndex));
                            }
                            return twoDList;
                        }
                ));

        Image newImgStream = new Image(origImg.width(), origImg.height());
        for (int i = 0; i < newImgStream.height(); i++) {
            for (int j = 0; j < newImgStream.width(); j++) {
                newImgStream.setColor(j, i, newPixels.get(i).get(j));
            }
        }

        System.out.println("\nStream Image Pixels:");
        for (int i = 0; i < newImgStream.height(); i++) {
            for (int j = 0; j < newImgStream.width(); j++) {
                Color color = newImgStream.getColor(j, i);
                System.out.print("{" + color.red() + "," + color.green() + "," + color.blue() + "} ");
            }
            System.out.println();
        }
    }
}
