package bok.geometry;

import bok.PureFuncs;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Tile {
  public final String identifier;
  public final int x;
  public final int y;
  public final URL url;

  public Tile(String identifier, int x, int y, URL url) {
    this.identifier = identifier;
    this.x = x;
    this.y = y;
    this.url = url;
  }

  public Tile(String identifier, int x, int y, String url) {
    this(identifier, x, y, PureFuncs.urlFromString(url));
  }

  public String getAddress() {
    return this.url.getPath();
  }
  
  public BufferedImage getTile() throws IOException {
    return PureFuncs.urlToImage(this.url);
  }

  public BufferedImage getTileUnsafe() {
    try {
      return getTile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
