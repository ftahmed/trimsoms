package me.ftahmed.bootify.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"order\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Order {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvIgnore
    private Long id;
    
    @Column(name="type") @CsvIgnore private String orderType;
    @Column(name="status") @CsvIgnore private String orderStatus;

    @Transient @CsvBindByName(column = "druckdatum") private String printDateStr;
    @Column(name="druckdatum") @CsvIgnore private LocalDateTime printDate;
    @Column(name="druckmenge") @CsvBindByName(column = "druckmenge") private Long quantity;
    @Column(name="druckmengeOrig") @CsvIgnore private Long quantityOrig;
    @Column(name="pagr") @CsvBindByName(column = "pagr") private String size;
    @Column(name="pakokey") @CsvBindByName(column = "pakokey") private String key;
    @Column(name="pakokeyFirma") @CsvBindByName(column = "pakokey_firma") private String company;
    @Column(name="pakokeySaison") @CsvBindByName(column = "pakokey_saison") private String season;
    @Column(name="pakokeyVp") @CsvBindByName(column = "pakokey_vp") private String brand;
    @Column(name="pakonr") @CsvBindByName(column = "pakonr") private String poNumber;
    @Column(name="paposFarbbez") @CsvBindByName(column = "papos_farbbez") private String colourDescription;
    @Column(name="paposFarbnr") @CsvBindByName(column = "papos_farbnr") private String colourNumber;
    @Column(name="paposMatnr") @CsvBindByName(column = "papos_matnr") private String materialGroup;
    @Column(name="paposVkabez") @CsvBindByName(column = "papos_vkabez") private String articleDescription;
    @Column(name="paposVkanr") @CsvBindByName(column = "papos_vkanr") private String articleNumber;
    @Column(name="paposVkarkey") @CsvBindByName(column = "papos_vkarkey") private String keyArticle;
    @Column(name="paposVkarkeyFirma") @CsvBindByName(column = "papos_vkarkey_firma") private String keyCompany;
    @Column(name="paposVkarkeySaison") @CsvBindByName(column = "papos_vkarkey_saison") private String keySeason;
    @Column(name="paposVkarkeyVp") @CsvBindByName(column = "papos_vkarkey_vp") private String keyBrand;
    @Column(name="pbetrmatch") @CsvBindByName(column = "pbetrmatch") private String factoryId;
    @Column(name="pbetrname1") @CsvBindByName(column = "pbetrname1") private String factoryName1;
    @Column(name="pbetrnr") @CsvBindByName(column = "pbetrnr") private String vendorId;
    @Column(name="paArt") @CsvBindByName(column = "paart") private String poType;
    @Column(name="paArtBez") @CsvBindByName(column = "paart_bez") private String poTypeDescription;
    @Column(name="designedin") @CsvBindByName(column = "designedin") private String designedIn;
    @Column(name="grbez1") @CsvBindByName(column = "grbez1") private String size1;
    @Column(name="grbez2") @CsvBindByName(column = "grbez2") private String size2;
    @Column(name="grbez3") @CsvBindByName(column = "grbez3") private String size3;
    @Column(name="grbez4") @CsvBindByName(column = "grbez4") private String size4;
    @Column(name="grbez5") @CsvBindByName(column = "grbez5") private String size5;
    @Column(name="grbez6") @CsvBindByName(column = "grbez6") private String size6;
    @Column(name="grbezUeb1") @CsvBindByName(column = "grbez_ueb1") private String sizeHeader1;
    @Column(name="grbezUeb2") @CsvBindByName(column = "grbez_ueb2") private String sizeHeader2;
    @Column(name="grbezUeb3") @CsvBindByName(column = "grbez_ueb3") private String sizeHeader3;
    @Column(name="grbezUeb4") @CsvBindByName(column = "grbez_ueb4") private String sizeHeader4;
    @Column(name="grbezUeb5") @CsvBindByName(column = "grbez_ueb5") private String sizeHeader5;
    @Column(name="grbezUeb6") @CsvBindByName(column = "grbez_ueb6") private String sizeHeader6;
    @Column(name="pflegehinweis", columnDefinition = "text") @CsvBindByName(column = "pflegehinweis") private String additionalWordings;
    @Column(name="pflegehinweis2") @CsvBindByName(column = "pflegehinweis2") private String additionalWordingsP2;
    @Column(name="pflegehinw01") @CsvBindByName(column = "pflegehinw01") private String additionalWording01;
    @Column(name="pflegehinw02") @CsvBindByName(column = "pflegehinw02") private String additionalWording02;
    @Column(name="pflegehinw03") @CsvBindByName(column = "pflegehinw03") private String additionalWording03;
    @Column(name="pflegehinw04") @CsvBindByName(column = "pflegehinw04") private String additionalWording04;
    @Column(name="pflegehinw05") @CsvBindByName(column = "pflegehinw05") private String additionalWording05;
    @Column(name="pflegehinw06") @CsvBindByName(column = "pflegehinw06") private String additionalWording06;
    @Column(name="pflegehinw07") @CsvBindByName(column = "pflegehinw07") private String additionalWording07;
    @Column(name="pflegehinw08") @CsvBindByName(column = "pflegehinw08") private String additionalWording08;
    @Column(name="pflegehinw09") @CsvBindByName(column = "pflegehinw09") private String additionalWording09;
    @Column(name="pflegehinw10") @CsvBindByName(column = "pflegehinw10") private String additionalWording10;
    @Column(name="pflegehinw11") @CsvBindByName(column = "pflegehinw11") private String additionalWording11;
    @Column(name="pflegehinw12") @CsvBindByName(column = "pflegehinw12") private String additionalWording12;
    @Column(name="pflegehinw13") @CsvBindByName(column = "pflegehinw13") private String additionalWording13;
    @Column(name="pflegehinw14") @CsvBindByName(column = "pflegehinw14") private String additionalWording14;
    @Column(name="pflegehinw15") @CsvBindByName(column = "pflegehinw15") private String additionalWording15;
    @Column(name="pflegehinw16") @CsvBindByName(column = "pflegehinw16") private String additionalWording16;
    @Column(name="pflegehinw17") @CsvBindByName(column = "pflegehinw17") private String additionalWording17;
    @Column(name="pflegehinw18") @CsvBindByName(column = "pflegehinw18") private String additionalWording18;
    @Column(name="pflegehinw19") @CsvBindByName(column = "pflegehinw19") private String additionalWording19;
    @Column(name="pflegehinw20") @CsvBindByName(column = "pflegehinw20") private String additionalWording20;
    @Column(name="pflegehinw21") @CsvBindByName(column = "pflegehinw21") private String additionalWording21;
    @Column(name="pflegehinw22") @CsvBindByName(column = "pflegehinw22") private String additionalWording22;
    @Column(name="pflegehinw23") @CsvBindByName(column = "pflegehinw23") private String additionalWording23;
    @Column(name="pflegehinw24") @CsvBindByName(column = "pflegehinw24") private String additionalWording24;
    @Column(name="pflegehinw25") @CsvBindByName(column = "pflegehinw25") private String additionalWording25;
    @Column(name="pflegehinw26") @CsvBindByName(column = "pflegehinw26") private String additionalWording26;
    @Column(name="pflegehinw27") @CsvBindByName(column = "pflegehinw27") private String additionalWording27;
    @Column(name="absenderName") @CsvBindByName(column = "absender_name") private String divisionName;
    @Column(name="absenderStrasse") @CsvBindByName(column = "absender_strasse") private String divisionStreet;
    @Column(name="absenderOrt") @CsvBindByName(column = "absender_ort") private String divisionCity;
    @Column(name="absenderWww") @CsvBindByName(column = "absender_www") private String divisionWebsite;
    @Column(name="madein") @CsvBindByName(column = "madein") private String madeIn;
    @Column(name="canummer") @CsvBindByName(column = "canummer") private String caNumber;
    @Column(name="pflegesym") @CsvBindByName(column = "pflegesym") private String careSymbols;
    @Column(name="peterHahnNr") @CsvBindByName(column = "peter_hahn_nr") private String phNumber;
    @Column(name="tkgZusatz2") @CsvBindByName(column = "tkg_zusatz_2") private String animalOriginFiber;
    @Column(name="paposVkaname") @CsvBindByName(column = "papos_vkaname") private String articleName;
    @Column(name="prodDescription") @CsvBindByName(column = "prod_description") private String productGroup;

    public void setPrintDateStr(String dateString) {
        // This method can parse the dateString and set date object as well
        this.printDateStr = dateString;
        setPrintDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
        if (getQuantityOrig() == null) {
            setQuantityOrig(quantity);
        }
    }

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    @CsvIgnore
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    @CsvIgnore
    private OffsetDateTime lastUpdated;
}
