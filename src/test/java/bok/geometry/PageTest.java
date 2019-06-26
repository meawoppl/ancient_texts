package bok.geometry;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PageTest {

  @Test
  public void testPageList() {
    Page p = new Page("foo", 2, 2, "%d_%d.jpg");
    Assertions.assertThat(p.getTiles()).size().isEqualTo(4);
  }

  @Test
  public void testProbeSize() {
    Page.probeSize("%d_%d");
  }
}
