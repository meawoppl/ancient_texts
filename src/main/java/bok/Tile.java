package bok;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Tile {
    public static final String BASE_URL = "https://digitalcollections.tcd.ie/content/14/pages/MS58_%03d%s/image_files/12/%d_%d.jpg";

    public final int page;
    public final int x;
    public final int y;
    public final String suffix;

    public Tile(int page, int x, int y, String suffix){
        this.page = page;
        this.x = x;
        this.y = y;
        this.suffix = suffix;
    }

    public String getAddress(){
        return String.format(
            BASE_URL,
            page, suffix, x, y);
    }

    public URL getUrl() {
        try {
            return new URL(getAddress());
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getTile() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) getUrl().openConnection();
        connection.setRequestProperty("User-Agent","BOK Scraper");
        return ImageIO.read(connection.getInputStream());
    }
}
