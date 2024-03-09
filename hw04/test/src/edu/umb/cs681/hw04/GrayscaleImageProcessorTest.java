package edu.umb.cs681.hw04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GrayscaleImageProcessorTest {

    private GrayscaleImageProcessor.Image image;
    private final int width = 4;
    private final int height = 3;

    @BeforeEach
    void setUp() {
        image = new GrayscaleImageProcessor.Image(width, height);
    }

    @Test
    void VerifyImageDimensions() {
        assertEquals(width, image.width());
        assertEquals(height, image.height());
    }

    @Test
    void VerifySetAndGetColor() {
        GrayscaleImageProcessor.Color color = new GrayscaleImageProcessor.Color(10, 20, 30);
        image.setColor(1, 1, color);
        assertEquals(color, image.getColor(1, 1));
    }

    @Test
    void VerifyColorValues() {
        GrayscaleImageProcessor.Color color = new GrayscaleImageProcessor.Color(10, 20, 30);
        assertEquals(10, color.red());
        assertEquals(20, color.green());
        assertEquals(30, color.blue());
    }

    @Test
    void VerifyGrayscaleConversion() {
        image.setColor(0, 0, new GrayscaleImageProcessor.Color(60, 70, 80));
        GrayscaleImageProcessor.Color color = image.getColor(0, 0);

        int avg = (color.red() + color.green() + color.blue()) / 3;
        GrayscaleImageProcessor.Color grayColor = new GrayscaleImageProcessor.Color(avg, avg, avg);

        assertEquals(avg, grayColor.red());
        assertEquals(avg, grayColor.green());
        assertEquals(avg, grayColor.blue());
    }
}
