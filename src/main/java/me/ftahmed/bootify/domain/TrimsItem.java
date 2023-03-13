package me.ftahmed.bootify.domain;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ibm.icu.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TrimsItem {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false, unique = true)
    private String intexNumber;
    
    @Column(nullable = false)
    private String labelType;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String style;
    
    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String picture;

    // TODO: Use BigDecimal
    // @Column(nullable = false, precision=12, scale=2, columnDefinition = "DECIMAL(12,2)")
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
