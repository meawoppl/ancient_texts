package bok.pagination;

import bok.geometry.Page;
import bok.geometry.PageSize;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Kells extends AbstractPagination {
  private static final List<String> DEFAULT_SUFFIX = Lists.newArrayList("r", "v");
  private static final Integer FIRST = 1;
  private static final Integer LAST = 339;

  @Override
  public String getUrlBase() {
    return "https://digitalcollections.tcd.ie/content/14/pages/MS58_%s/image_files/12/%d_%d.jpg";
  }

  @Override
  public List<Page> pages() {
    // NB Only injects page/suffix here...
    final PageSize size = Page.probeSize(templateForIdentifier("001v"));

    List<Page> pages = new ArrayList<>();
    IntStream.range(FIRST, LAST)
        .forEach(
            (pageNumber) -> {
              // NOTE(meawoppl) - There are two sets of page 36
              List<String> suffixes = DEFAULT_SUFFIX;
              if (pageNumber == 36) {
                suffixes = Lists.newArrayList("ar", "av", "br", "bv");
              }

              suffixes.forEach(
                  (suffix) -> {
                    String identifier = String.format("%03d%s", pageNumber, suffix);
                    pages.add(new Page(identifier, size, templateForIdentifier(identifier)));
                  });
            });
    return pages;
  }
}
