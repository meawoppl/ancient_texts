package bok;

public class DownloadEntryPoint {
    public static void main(String[] args) {
        PageSize size = Page.probeSize();
        Page first = new Page(1, size, "v");
        DownloadPool.downloadAndStitch(first);
    }
}
