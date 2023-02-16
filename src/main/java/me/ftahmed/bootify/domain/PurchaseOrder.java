package me.ftahmed.bootify.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseOrder {
    
    private String orderType;
    private String orderStatus;
    
    private String brand;
    private String season;
    private String poNumber;
    private String articleNumber;
    private String vendorId;
    private String factoryName1;
}
