package com.tai.project4.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberManager {
    private static NumberFormat nf;

    public static NumberFormat getInstance() {
        if (nf == null)
            nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf;
    }

}
