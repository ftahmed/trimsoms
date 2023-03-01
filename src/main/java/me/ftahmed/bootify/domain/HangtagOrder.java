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
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class HangtagOrder {
    
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvIgnore
    private Long id;

    @Column(name="record_id") private String record_id;
    @Column(name="Line_Quantity") private String Line_Quantity;
    @Column(name="Ticket_Type") private String Ticket_Type;
    @Column(name="Vp_Alpha") private String brand;
    @Column(name="Prod_Betrieb") private String Prod_Betrieb;
    @Column(name="Saison") private String season;
    @Column(name="Exf_Termin") private String Exf_Termin;
    @Column(name="Fa_Nr") private String poNumber;
    @Column(name="Kt_Woche") private String Kt_Woche;
    @Column(name="Ean_13") private String Ean_13;
    @Column(name="Modell") private String Modell;
    @Column(name="Stoff") private String Stoff;
    @Column(name="Farbe") private String Farbe;
    @Column(name="SetnummerBlank") private String SetnummerBlank;
    @Column(name="Programmtext_1") private String Programmtext_1;
    @Column(name="Programmtext_2") private String Programmtext_2;
    @Column(name="Grosse") private String Grosse;
    @Column(name="Inter_Bez_1") private String Inter_Bez_1;
    @Column(name="Inter_Bez_2") private String Inter_Bez_2;
    @Column(name="Inter_Bez_3") private String Inter_Bez_3;
    @Column(name="Inter_Bez_4") private String Inter_Bez_4;
    @Column(name="Inter_Gr_1") private String Inter_Gr_1;
    @Column(name="Inter_Gr_2") private String Inter_Gr_2;
    @Column(name="Inter_Gr_3") private String Inter_Gr_3;
    @Column(name="Inter_Gr_4") private String Inter_Gr_4;
    @Column(name="dummy") private String dummy;
    @Column(name="Einzelteil_Identcode") private String Einzelteil_Identcode;
    @Column(name="Referenceorder") private String Referenceorder;
    @Column(name="Vp_Numeric") private String Vp_Numeric;
    @Column(name="Variante") private String Variante;
    @Column(name="Preis") private String Preis;
    @Column(name="Wahrung") private String Wahrung;
    @Column(name="Logo") private String Logo;
    @Column(name="Kundennummer") private String Kundennummer;
    @Column(name="Kundenfilialnummer") private String Kundenfilialnummer;
    @Column(name="Stoffzusammensetzung_1") private String Stoffzusammensetzung_1;
    @Column(name="Stoffzusammensetzung_2") private String Stoffzusammensetzung_2;
    @Column(name="Stoffzusammensetzung_3") private String Stoffzusammensetzung_3;
    @Column(name="Stoffzusammensetzung_4") private String Stoffzusammensetzung_4;
    @Column(name="Sprache") private String Sprache;
    @Column(name="Folienart") private String Folienart;
    @Column(name="Etikettenart") private String Etikettenart;
    @Column(name="Sortnummer") private String Sortnummer;
    @Column(name="Lifestyle_Kollektion") private String Lifestyle_Kollektion;
    @Column(name="Kurz_Aus_Ort") private String Kurz_Aus_Ort;
    @Column(name="Country_1") private String Country_1;
    @Column(name="Currency_1") private String Currency_1;
    @Column(name="Price_1") private String Price_1;
    @Column(name="Country_2") private String Country_2;
    @Column(name="Currency_2") private String Currency_2;
    @Column(name="Price_2") private String Price_2;
    @Column(name="Country_3") private String Country_3;
    @Column(name="Currency_3") private String Currency_3;
    @Column(name="Price_3") private String Price_3;
    @Column(name="Country_4") private String Country_4;
    @Column(name="Currency_4") private String Currency_4;
    @Column(name="Price_4") private String Price_4;
    @Column(name="Country_5") private String Country_5;
    @Column(name="Currency_5") private String Currency_5;
    @Column(name="Price_5") private String Price_5;
    @Column(name="Country_6") private String Country_6;
    @Column(name="Currency_6") private String Currency_6;
    @Column(name="Price_6") private String Price_6;
    @Column(name="Country_7") private String Country_7;
    @Column(name="Currency_7") private String Currency_7;
    @Column(name="Price_7") private String Price_7;
    @Column(name="Country_8") private String Country_8;
    @Column(name="Currency_8") private String Currency_8;
    @Column(name="Price_8") private String Price_8;
    @Column(name="Country_9") private String Country_9;
    @Column(name="Currency_9") private String Currency_9;
    @Column(name="Price_9") private String Price_9;
    @Column(name="Country_10") private String Country_10;
    @Column(name="Currency_10") private String Currency_10;
    @Column(name="Price_10") private String Price_10;
    @Column(name="Inter_Bez_5") private String Inter_Bez_5;
    @Column(name="Inter_Gr_5") private String Inter_Gr_5;
    @Column(name="Ekpreis") private String Ekpreis;
    @Column(name="PaArt") private String PaArt;
    @Column(name="PaArtBez") private String PaArtBez;
    @Column(name="Setnummer") private String Setnummer;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    @CsvIgnore
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    @CsvIgnore
    private OffsetDateTime lastUpdated;
}
