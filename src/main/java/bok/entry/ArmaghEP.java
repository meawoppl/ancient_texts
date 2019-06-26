package bok.entry;

import bok.PureFuncs;
import bok.pagination.Armagh;

public class ArmaghEP {
    public static void main(String[] args) {
        PureFuncs.downloadPages("boa_download", new bok.pagination.Armagh().pages());
    }
}
