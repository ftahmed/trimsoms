package me.ftahmed.bootify.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class ByteUtils {

    public static Charset detectEncoding(InputStream in) throws IOException {
        final CharsetDetector detector = new CharsetDetector();
        byte[] b = new byte[256];
        in.read(b, 0, 256);
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
