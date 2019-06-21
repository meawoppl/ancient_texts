package bok;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Page {
    public final int pageNumber;
    public final PageSize pageSize;
    public final String suffix;

    public Page(int p, int w, int h, String suffix){
        this(p, new PageSize(w, h), suffix);
    }

    public Page(int p, PageSize pageSize, String suffix) {
        this.pageNumber = p;
        this.pageSize = pageSize;
        this.suffix =suffix;
    }

    public List<Tile> getTiles(){
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int w = 0; w < this.pageSize.width; w++) {
            for (int h = 0; h < this.pageSize.height; h++) {
                tiles.add(new Tile(pageNumber, w, h, suffix));
            }
        }

        return tiles;
    }

    public int pageWidthPixels(){
        return pageSize.width * 256;
    }

    public int pageHeightPixels(){
        return pageSize.height * 256;
    }

    public static PageSize probeSize() {
        int maxProbe = 100;

        int detectedW = 0;
        for (int w = 0; w < maxProbe; w++) {
            Tile t = new Tile(1, w, 0, "v");
            try {
                t.getTile();
            } catch (IOException e){
                detectedW = w - 1;
                break;
            }
        }

        int detectedH = 0;
        for (int h = 0; h < maxProbe; h++) {
            Tile t = new Tile(1, 0, h, "v");
            try {
                t.getTile();
            } catch (IOException e){
                detectedH = h - 1;
                break;
            }
        }

        return new PageSize(detectedW, detectedH);
    }
}
