package bok;

import bok.geometry.Page;
import bok.geometry.Tile;
import me.tongfei.progressbar.ProgressBar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

public class PureFuncs {
  private PureFuncs() {}

  public static BufferedImage urlToImage(URL url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("User-Agent", "Book Scraper");
    return ImageIO.read(connection.getInputStream());
  }

  public static BufferedImage urlToImage(String url) throws IOException {
    return urlToImage(urlFromString(url));
  }

  public static URL urlFromString(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void writeJPEG(BufferedImage bufferedImage, File export) {
    try {
      ImageIO.write(bufferedImage, "jpg", export);
    } catch (IOException e) {
      System.err.print(String.format("Can not save file %s", export));
      throw new RuntimeException(e);
    }
  }

    public static Map<Tile, BufferedImage> getTiles(List<Tile> tiles) {
      return tiles
          .stream()
          .parallel()
          .collect(Collectors.toConcurrentMap(Function.identity(), Tile::getTileUnsafe));
    }

    public static BufferedImage downloadAndStitch(Page page) {
      List<Tile> tiles = page.getTiles();

      Map<Tile, BufferedImage> tileData = getTiles(tiles);

      BufferedImage stitched =
          new BufferedImage(
              page.pageWidthPixels(), page.pageHeightPixels(), BufferedImage.TYPE_3BYTE_BGR);

      final Graphics2D g = stitched.createGraphics();
      tileData.forEach(
          (tile, img) -> {
            int w = tile.x * 255;
            int h = tile.y * 255;
            g.drawImage(img, w, h, null);
          });

      return stitched;
    }

    public static void downloadPages(String folder, List<Page> pages) {
      File theDir = new File(folder);
      if (!theDir.exists()) {
        theDir.mkdir();
      }

      try (ProgressBar pb = new ProgressBar("Pages", pages.size(), 1000)) {
        pages
            .stream()
            .parallel()
            .peek((page) -> pb.step())
            .filter((page) -> !page.getFile(folder).exists())
            .forEach(
                (page) -> {
                  File export = page.getFile(folder);
                  BufferedImage image = downloadAndStitch(page);
                  writeJPEG(image, export);
                });
      }
    }
}
