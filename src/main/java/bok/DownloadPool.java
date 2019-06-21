package bok;

import com.intellij.util.ui.UIUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadPool {
    public static Map<Tile, BufferedImage> getTiles(List<Tile> tiles){
        Map<Tile, BufferedImage> results = new HashMap<>();

        tiles.stream().parallel().forEach(

            tile -> {
                BufferedImage bi;
                try {
                    bi = tile.getTile();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }

                results.put(tile, bi);
            }
        );

        return results;
    }

    public static BufferedImage downloadAndStitch(Page page){
        List<Tile> tiles = page.getTiles();

        Map<Tile, BufferedImage> tileData = getTiles(tiles);

        BufferedImage stiched = UIUtil.createImage(page.pageWidthPixels(), page.pageHeightPixels(), BufferedImage.TYPE_3BYTE_BGR);

        final Graphics2D g = stiched.createGraphics();
        tileData.forEach((tile, img)->{
            int w = tile.x * 256;
            int h = tile.y * 256;
            g.drawImage(img, w, h, w + 256, h + 256, null);
        });

        return stiched;
    }
}
