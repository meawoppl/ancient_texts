package bok.geometry;

import bok.PureFuncs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class Page {
  public static final Integer TILESIZE = 256;

  public final String identifier;
  public PageSize pageSize;
  public final String template;

  public Page(String identifier, int w, int h, String template) {
    this(identifier, new PageSize(w, h), template);

    // Make sure template is valid
    urlForTile(0, 0);
  }

  public Page(String identifier, @Nullable PageSize pageSize, String template) {
    this.identifier = identifier;
    this.pageSize = pageSize;
    this.template = template;
  }

  public Page(String identifier, String template) {
    this(identifier, null, template);
  }

  public String urlForTile(int w, int h) {
    return urlForTile(template, w, h);
  }

  public static String urlForTile(String template, int w, int h) {
    return String.format(template, w, h);
  }

  public Tile getTile(int w, int h) {
    String url = String.format(template, w, h);
    return new Tile(identifier, w, h, url);
  }

  public List<Tile> getTiles() {
    if (pageSize == null) {
      pageSize = probeSize(template);
    }

    ArrayList<Tile> tiles = new ArrayList<>();
    for (int w = 0; w < this.pageSize.width; w++) {
      for (int h = 0; h < this.pageSize.height; h++) {
        tiles.add(getTile(w, h));
      }
    }

    return tiles;
  }

  public String getFileName() {
    return String.format("%s.jpg", identifier);
  }

  public File getFile(String base) {
    return Paths.get(base, getFileName()).toFile();
  }

  public int pageWidthPixels() {
    return pageSize.width * TILESIZE;
  }

  public int pageHeightPixels() {
    return pageSize.height * TILESIZE;
  }

  public static PageSize probeSize(String template) {
    int maxProbe = 100;

    int h = 0;
    int w = 0;

    for (w = 0; w < maxProbe; w++) {
      try {
        PureFuncs.urlToImage(urlForTile(template, w, 0));
      } catch (IOException e) {
        w = w - 1;
        break;
      }
    }

    for (h = 0; h < maxProbe; h++) {
      try {
        PureFuncs.urlToImage(urlForTile(template, 0, h));
      } catch (IOException e) {
        h = h - 1;
        break;
      }
    }

    if (h == -1 || w == -1) {
      String msg = String.format("Template failed bounds search process: %s", template);
      throw new RuntimeException(msg);
    }

    return new PageSize(w, h);
  }
}
