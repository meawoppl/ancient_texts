package bok;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.awt.image.BufferedImage;


public class TileTest {
    @Test
    public void getAddress() throws Exception {
        Tile tile = new Tile(1, 2,3, "v");
        tile.getAddress();
    }

    @Test
    public void getUrl() throws Exception {
        Tile tile = new Tile(1, 2,3, "v");
        tile.getUrl();
    }

    /**
     * NB: Naughty, does net IO in test
     */
    @Test
    public void testGetTile() throws Exception {
        Tile tile = new Tile(1, 0,0, "v");
        System.out.println(tile.getUrl());
        BufferedImage img = tile.getTile();
        Assertions.assertThat(img.getWidth()).isEqualTo(255);
        Assertions.assertThat(img.getHeight()).isEqualTo(255);
    }
}
