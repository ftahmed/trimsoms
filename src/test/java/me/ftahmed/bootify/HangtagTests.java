package me.ftahmed.bootify;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class HangtagTests {

	final String HT_HEADER = "H                         1231-4002340000BD 10.08.2022GMS Composite Knitting             Ind. Ltd.                                                             House#110,Road#06                  1206                               Dhaka                              Vollgesch‰ft Strick                                         +88 01713141200                         NGMS Composite Knitting Ind Ltd                                                                           House#110,Road#6,New D.O.H.S       1206                               Dhaka                                                                                          +8801713141200                          213970";
	
	final String HT_DATA = "D000015100001  BB213970231454002342023024065931517085276714158841   5021                5021                36  F  I  GB US 38 42 10 8  01885087151231-4002340000001                                                                                                                                   I1           Collection           D         EUR 49,99A,B,NL    EUR 55,99GB        GBP 55,00GR        EUR 65,00CH        CHF 79,90                                                            MX 30      80  Produktionsauftrag VG         5021 ";
	final int[] HT_COL_LENGHT = { 1, 5, 9, 2, 6, 3, 2, 6, 6, 13, 4, 4, 4, 3, 20, 20, 4, 3, 3, 3, 3, 3, 3, 3, 3, 1, 9, 15, 3, 12, 6, 3, 10, 10, 4, 20, 20, 20, 20, 3, 3, 1, 12, 20, 1, 10, 3, 6, 10, 3, 6, 10, 3, 6, 10, 3, 6, 10, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 5, 4, 30, 5 };

	final String EAN13_CODE_12 = "100000001202";
	final int EAN13_CHECK_DIGIT_13 = 6;

	final String EAN13_CODE_9 = "188508715";
	final int EAN13_CHECK_DIGIT_9 = 5;

	@Test
    public void testParseHangtagHeader() throws Exception {
		// TODO
    }

	@Test
	public void testParseHangtagRow() throws Exception {
		// TODO
		char[] row = HT_DATA.toCharArray();
		List<String> cols = new ArrayList<>();
		for (int i=0, pos=0; i<HT_COL_LENGHT.length; i++) {
			cols.add(String.valueOf(row, pos, HT_COL_LENGHT[i]));
			pos += HT_COL_LENGHT[i];
		}
		log.info(cols.toString());
		assertThat(cols, hasSize(81));
	}

	@Test
	public void testEan12CheckDigit() {
		int digit = ean13CheckDigit(EAN13_CODE_12);
		log.info(String.format("EAN13 barcode: %s, check digit: %d", EAN13_CODE_12, digit));
		assertEquals(6, digit);
	}

	@Test
	public void testEan9CheckDigitLpad() {
		int digit = ean13CheckDigit("000"+EAN13_CODE_9);
		log.info(String.format("EAN13 barcode: %s, check digit: %d", EAN13_CODE_9, digit));
		assertEquals(5, digit);
	}

	@Test
	public void testEan9CheckDigitFormat() {
		int digit = ean13CheckDigit(String.format("%1$12s", EAN13_CODE_9).replace(' ', '0'));
		log.info(String.format("EAN13 barcode: %s, check digit: %d", EAN13_CODE_9, digit));
		assertEquals(5, digit);
	}

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
