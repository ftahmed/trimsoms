package me.ftahmed.bootify.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Constants {
    
    public static final Map<String, String> products = Map.of(
        "carelabel", "Care label", 
        "hangtag", "Hangtag"
    );

    public static final Map<String, String> orderTypes = Map.of(
        "SMS", "SMS",
        "BULK", "BULK"
    );

    public static final List<String> orderStatus = Arrays.asList(
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
