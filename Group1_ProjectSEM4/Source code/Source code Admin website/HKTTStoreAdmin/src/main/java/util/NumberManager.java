/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author ASUS
 */
public class NumberManager {
    private static NumberFormat nf;

    public static NumberFormat getInstance() {
        if (nf == null)
            nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf;
    }
    
}
