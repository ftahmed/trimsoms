package me.ftahmed.bootify.domain;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
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
    @CsvBindByName(column="1_Vp_Alpha") @CsvBindByPosition(position=0)
    @Column(name="Vp_Alpha") private String brand;
    @CsvBindByName(column="44_Prod_Betrieb") @CsvBindByPosition(position=43)
    @Column(name="Prod_Betrieb") private String VendorId;
    @CsvBindByName(column="2_Saison") @CsvBindByPosition(position=1)
    @Column(name="Saison") private String season;
    @CsvBindByName(column="3_Exf_Termin") @CsvBindByPosition(position=2)
    @Column(name="Exf_Termin") private String Exf_Termin;
    @CsvBindByName(column="4_Fa_Nr") @CsvBindByPosition(position=3)
    @Column(name="Fa_Nr") private String poNumber;
    @CsvBindByName(column="5_Kt_Woche") @CsvBindByPosition(position=4)
    @Column(name="Kt_Woche") private String Kt_Woche;
    @CsvBindByName(column="6_Ean_13") @CsvBindByPosition(position=5)
    @Column(name="Ean_13") private String Ean_13;
    @CsvBindByName(column="7_Modell") @CsvBindByPosition(position=6)
    @Column(name="Modell") private String Modell;
    @CsvBindByName(column="8_Stoff") @CsvBindByPosition(position=7)
    @Column(name="Stoff") private String Stoff;
    @CsvBindByName(column="9_Farbe") @CsvBindByPosition(position=8)
    @Column(name="Farbe") private String Farbe;
    @Column(name="SetnummerBlank") private String SetnummerBlank;
    @CsvBindByName(column="12_Programmtext_2") @CsvBindByPosition(position=11)
    @Column(name="Programmtext_1") private String Programmtext_1;
    @CsvBindByName(column="13_Programmtext_2") @CsvBindByPosition(position=12)
    @Column(name="Programmtext_2") private String Programmtext_2;
    @CsvBindByName(column="14_Grosse") @CsvBindByPosition(position=13)
    @Column(name="Grosse") private String Grosse;
    @CsvBindByName(column="15_Inter_Bez_1") @CsvBindByPosition(position=14)
    @Column(name="Inter_Bez_1") private String Inter_Bez_1;
    @CsvBindByName(column="16_Inter_Bez_2") @CsvBindByPosition(position=15)
    @Column(name="Inter_Bez_2") private String Inter_Bez_2;
    @CsvBindByName(column="17_Inter_Bez_3") @CsvBindByPosition(position=16)
    @Column(name="Inter_Bez_3") private String Inter_Bez_3;
    @CsvBindByName(column="18_Inter_Bez_4") @CsvBindByPosition(position=17)
    @Column(name="Inter_Bez_4") private String Inter_Bez_4;
    @CsvBindByName(column="20_Inter_Gr_1") @CsvBindByPosition(position=19)
    @Column(name="Inter_Gr_1") private String Inter_Gr_1;
    @CsvBindByName(column="21_Inter_Gr_2") @CsvBindByPosition(position=20)
    @Column(name="Inter_Gr_2") private String Inter_Gr_2;
    @CsvBindByName(column="22_Inter_Gr_3") @CsvBindByPosition(position=21)
    @Column(name="Inter_Gr_3") private String Inter_Gr_3;
    @CsvBindByName(column="23_Inter_Gr_4") @CsvBindByPosition(position=22)
    @Column(name="Inter_Gr_4") private String Inter_Gr_4;
    @Column(name="dummy") private String dummy;
    @Column(name="Einzelteil_Identcode") private String Einzelteil_Identcode;
    @Column(name="Referenceorder") private String referenceorder;
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
    @CsvBindByName(column="43_Sortnummer") @CsvBindByPosition(position=42)
    @Column(name="Sortnummer") private String Sortnummer;
    @Column(name="Lifestyle_Kollektion") private String Lifestyle_Kollektion;
    @CsvBindByName(column="11_Kurz_Aus_Ort") @CsvBindByPosition(position=10)
    @Column(name="Kurz_Aus_Ort") private String Kurz_Aus_Ort;
    @CsvBindByName(column="2_5Country_1") @CsvBindByPosition(position=24)
    @Column(name="Country_1") private String Country_1;
    @CsvBindByName(column="37_Currency_1") @CsvBindByPosition(position=36)
    @Column(name="Currency_1") private String Currency_1;
    @CsvBindByName(column="3_2Price_1") @CsvBindByPosition(position=31)
    @Column(name="Price_1") private String Price_1;
    @CsvBindByName(column="26_Country_2") @CsvBindByPosition(position=25)
    @Column(name="Country_2") private String Country_2;
    @CsvBindByName(column="38_Currency_2") @CsvBindByPosition(position=37)
    @Column(name="Currency_2") private String Currency_2;
    @CsvBindByName(column="33_Price_2") @CsvBindByPosition(position=32)
    @Column(name="Price_2") private String Price_2;
    @CsvBindByName(column="27_Country_3") @CsvBindByPosition(position=26)
    @Column(name="Country_3") private String Country_3;
    @CsvBindByName(column="39_Currency_3") @CsvBindByPosition(position=38)
    @Column(name="Currency_3") private String Currency_3;
    @CsvBindByName(column="34_Price_3") @CsvBindByPosition(position=33)
    @Column(name="Price_3") private String Price_3;
    @CsvBindByName(column="28_Country_4") @CsvBindByPosition(position=27)
    @Column(name="Country_4") private String Country_4;
    @CsvBindByName(column="40_Currency_4") @CsvBindByPosition(position=39)
    @Column(name="Currency_4") private String Currency_4;
    @CsvBindByName(column="35_Price_4") @CsvBindByPosition(position=34)
    @Column(name="Price_4") private String Price_4;
    @CsvBindByName(column="29_Country_5") @CsvBindByPosition(position=28)
    @Column(name="Country_5") private String Country_5;
    @CsvBindByName(column="41_Currency_5") @CsvBindByPosition(position=40)
    @Column(name="Currency_5") private String Currency_5;
    @CsvBindByName(column="36_Price_5") @CsvBindByPosition(position=35)
    @Column(name="Price_5") private String Price_5;
    @CsvBindByName(column="30_Country_6") @CsvBindByPosition(position=29)
    @Column(name="Country_6") private String Country_6;
    @Column(name="Currency_6") private String Currency_6;
    @Column(name="Price_6") private String Price_6;
    @CsvBindByName(column="31_Country_7") @CsvBindByPosition(position=30)
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
    @CsvBindByName(column="19_Inter_Bez_5") @CsvBindByPosition(position=18)
    @Column(name="Inter_Bez_5") private String Inter_Bez_5;
    @CsvBindByName(column="24_Inter_Gr_5") @CsvBindByPosition(position=23)
    @Column(name="Inter_Gr_5") private String Inter_Gr_5;
    @CsvBindByName(column="45_Ekpreis") @CsvBindByPosition(position=44)
    @Column(name="Ekpreis") private String Ekpreis;
    @Column(name="PaArt") private String PaArt;
    @Column(name="PaArtBez") private String PaArtBez;
    @Column(name="Setnummer") private String Setnummer;

        
    @CsvBindByName(column="10_Partie_No") @CsvBindByPosition(position=9)
    @Column(name="Partie_No") private String partieNo;

    @CsvBindByName(column="42_ET_Code") @CsvBindByPosition(position=41)
    @Column(name="etCode") private String etCode;

    public void setEinzelteil_Identcode(String value) {
        this.Einzelteil_Identcode = value;
        this.etCode = value + ean13CheckDigit("000"+value.trim());
    }

    // public String getEtCode() {
    //     this.etCode = this.Einzelteil_Identcode + ean13CheckDigit("000"+this.Einzelteil_Identcode);
    //     return this.etCode;
    // }

    public void setSortnummer(String value) {
        this.Sortnummer = value;
        // int row = (int) Math.ceil(Integer.parseInt(value) / 16);
        // int col = Integer.parseInt(value) % 16;
        // int seq = (row * 16) + col;
    }

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    @CsvIgnore
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamptz default CURRENT_TIMESTAMP")
    @CsvIgnore
    private OffsetDateTime lastUpdated;

    private int ean13CheckDigit(String input) {
		char[] barcode = input.toCharArray();
		int sum = 0;
		for (int i = 0; i < barcode.length; i++) {
			int c = Character.getNumericValue(barcode[i]);
			sum += c * ( i%2 == 0 ? 1 : 3 );
		}
		int digit = (10 - sum % 10) % 10;
		return digit;
	}
}
