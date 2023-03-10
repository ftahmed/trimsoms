package me.ftahmed.bootify.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
// @Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "product", "poNumber" }) })
@Getter
@Setter
public class PurchaseOrder {
    
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String product;
    
    @Column
    private String type;
    
    @Column(nullable = false)
    private String status;

    @Column
    private Long totalQty;

    @Column
    private Long totalQtyOrig;
    
    @Column
    private String season;
    
    @Column
    private String brand;

    @Column(nullable = false)
    private String poNumber;

    @Column
    private String articleNumber;

    @Column
    private String factoryName1;
    
    @Column
    private String vendorCode;

    @Column(unique = true)
    private String orderFile;

    @Column(unique = true)
    private String orderOriginalFile;

    @Column
    private String layoutFile;

    @Column
    private String layoutOriginalFile;

    @Column
    private OffsetDateTime layoutDate;

    @Column
    private OffsetDateTime approvalDate;

    @Column
    private String rejectReason;

    @Column
    private String referenceOrder;
    
    @ManyToOne
    @JoinColumn(referencedColumnName = "code")
    private Ticket ticket;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> compositions = new ArrayList<>();

    @Column
    private String deliveryAddress;

    @Column
    private String orderBy;

    @Column
    private String vendorPo;

    @Column
    private String printerNotes;

    @Column
    private String price;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    private OffsetDateTime lastUpdated;

}
