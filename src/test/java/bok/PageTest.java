package bok;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class PageTest {

    @Test
    public void testPageList(){
        Page p = new Page(1, 2, 2, "v");
        Assertions.assertThat(p.getTiles()).size().isEqualTo(4);
    }

    @Test
    public void testProbeSize(){
        Page.probeSize();
    }
}
