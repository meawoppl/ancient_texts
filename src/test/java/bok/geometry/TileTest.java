package bok.geometry;

import java.awt.image.BufferedImage;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TileTest {
  private String TEST_URL = "https://via.placeholder.com/%dx%d.png";

  @Test
  public void getAddress() throws Exception {
    Tile tile = new Tile("foo", 2, 3, TEST_URL);

    Assertions.assertThat(tile.getAddress()).isEqualTo(TEST_URL);
  }

  /** NB: Naughty, does internet IO in test */
  @Test
  public void testGetTile() throws Exception {
    int h = 101;
    int w = 107;
    Tile tile = new Tile("foo", 101, 107, String.format(TEST_URL, 101, 107));
    System.out.println(tile.url);
    BufferedImage img = tile.getTile();
    Assertions.assertThat(img.getWidth()).isEqualTo(101);
    Assertions.assertThat(img.getHeight()).isEqualTo(107);
  }
}
