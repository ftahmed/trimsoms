package me.ftahmed.bootify.domain;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TrimsOrder {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="vendorCode", referencedColumnName = "vendorCode")
    private Vendor vendor;
    
    @Column(unique = true, nullable = false)
    private String poNumber;

    @Column(unique = true, nullable = false)
    private String referenceorder;

    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private String intexNumber;
    
    @Column(nullable = false)
    private String labelType;

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String item;
    
    @Column(nullable = false)
    private Integer quantity;

    // TODO: Use BigDecimal
    // @Column(nullable = false, columnDefinition = "DECIMAL(12,2)")
    // private BigDecimal price;

    @Column(nullable = false)
    private String price;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    private OffsetDateTime lastUpdated;
}
