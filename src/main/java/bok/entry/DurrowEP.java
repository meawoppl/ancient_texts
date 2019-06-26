package bok.entry;

import bok.PureFuncs;
import bok.pagination.Durrow;

public class DurrowEP {
  public static void main(String[] args) {
    PureFuncs.downloadPages("bod_downloaded", new Durrow().pages());
  }
}
