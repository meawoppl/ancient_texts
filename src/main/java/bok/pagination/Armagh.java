package bok.pagination;

import bok.geometry.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Armagh extends AbstractPagination {
  @Override
  public String getUrlBase() {
    return "https://digitalcollections.tcd.ie/content/26/pages/MS52_%s/image_files/10/%d_%d.jpg";
  }

  private Integer FIRST = 1;
  private Integer LAST = 86;

  @Override
  public List<Page> pages() {
    List<Page> pages = new ArrayList<>();

    IntStream.range(FIRST, LAST)
        .forEach(
            (pageNumber) -> {
              String identifier = String.format("%02d", pageNumber);
              // NB: Page size is unstable!!!
              pages.add(new Page(identifier, templateForIdentifier(identifier)));
            });
    return pages;
  }
}
