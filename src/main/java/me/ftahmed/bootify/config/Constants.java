package me.ftahmed.bootify.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Constants {
    
    public static final String UPLOAD_DIR = "./uploads/";

    public static final String CARELABEL = "carelabel";
    public static final String HANGTAG = "hangtag";

    public static final Map<String, String> productValues = Map.of(
        CARELABEL, "Care label", 
        HANGTAG, "Hangtag"
    );

    public static final Map<String, String> orderTypeValues = Map.of(
        "SMS", "SMS",
        "BULK", "BULK"
    );

    public static final List<String> orderStatusValues = Arrays.asList(
        "New",
        "On hold",
        "Composition updated",
        "Layout approved",
        "Layout rejected",
        "Layout resubmitted",
        "Cancelled",
        "Confirmed",
        "Completed",
        "Delivered"
    );
}
