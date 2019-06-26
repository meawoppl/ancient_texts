package bok.pagination;

import bok.geometry.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Durrow extends AbstractPagination {
  private static final Integer FIRST = 1;
  private static final Integer LAST = 510;

  @Override
  public String getUrlBase() {
    return "https://digitalcollections.tcd.ie/content/1845/pages/MS57_%s/image_files/13/%d_%d.jpg";
  }

  @Override
  public List<Page> pages() {
    List<Page> pages = new ArrayList<>();

    IntStream.range(FIRST, LAST)
        .forEach(
            (pageNumber) -> {
              String identifier = String.format("%03d", pageNumber);
              // NB: Page size is unstable!!!
              pages.add(new Page(identifier, templateForIdentifier(identifier)));
            });
    return pages;
  }
}
