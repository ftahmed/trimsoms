package me.ftahmed.bootify.config;

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

    public static final Map<String, String> orderStatusValues = Map.of(
        "New", "New", 
        "Confirmed", "Confirmed", 
        "Completed", "Completed", 
        "Canceled", "Canceled", 
        "All", "All"
    );
}
