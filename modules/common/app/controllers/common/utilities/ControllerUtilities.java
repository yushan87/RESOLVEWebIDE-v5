/*
 *  ---------------------------------
 *  Copyright (c) 2017
 *  RESOLVE Software Research Group
 *  School of Computing
 *  Clemson University
 *  All rights reserved.
 *  ---------------------------------
 *  This file is subject to the terms and conditions defined in
 *  file 'LICENSE.txt', which is part of this source code package.
 */

package controllers.common.utilities;

/* Libraries */
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: Add JavaDocs for this class.
 */
public class ControllerUtilities {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Decode the message by replacing all the %20 with white spaces.</p>
     *
     * @param raw Raw data from the Web message.
     *
     * @return The actual text in regular ASCII form.
     */
    public static String decode(String raw) {
        String encoded = null;

        try {
            encoded = URLDecoder.decode(raw.replaceAll("%20", " "), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encoded;
    }

    /**
     * <p>Decode the message by replacing all the %2B with the "+" symbol.</p>
     *
     * @param input Input data from the WebIDE.
     *
     * @return The actual text in regular ASCII form.
     */
    public static String decodePlusSign(String input) {
        String target = "\\%2B";
        String replace = "+";
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll(replace);
    }

    /**
     * <p>Encode the message by replacing all the white spaces with %20.</p>
     *
     * @param raw Output data from the RESOLVE compiler.
     *
     * @return The encoded data for WebIDE.
     */
    public static String encode(String raw) {
        String encoded = null;

        try {
            encoded = URLEncoder.encode(raw.replaceAll(" ", "%20"), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encoded;
    }
}
