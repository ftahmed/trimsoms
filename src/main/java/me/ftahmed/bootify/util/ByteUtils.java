package me.ftahmed.bootify.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class ByteUtils {

    public static final int PEEK_LENGTH = 1024;

    public static Charset detectEncoding(InputStream in) throws IOException {
        final CharsetDetector detector = new CharsetDetector();
        byte[] b = new byte[PEEK_LENGTH];
        in.read(b, 0, PEEK_LENGTH);
        detector.setText(b);

        final CharsetMatch charsetMatch = detector.detect();
        if (charsetMatch == null) {
            // log.info("Cannot detect source charset.");
            return null;
        }
        //This is an integer from 0 to 100. The higher the value, the more confidence
        int confidence = charsetMatch.getConfidence();
        final String name = charsetMatch.getName();
        // log.info("CharsetMatch: {} with {}% confidene (<50% means unreliable)", name, confidence);
        CharsetMatch[] matches = detector.detectAll();
        // System.out.println("All possibilities : " + Arrays.asList(matches));
        return Charset.forName(name);
    }
}
