package me.ftahmed.bootify;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;


class JsonTests {

	final String HT_HEADER = "H                         1231-4002340000BD 10.08.2022GMS Composite Knitting             Ind. Ltd.                                                             House#110,Road#06                  1206                               Dhaka                              Vollgesch‰ft Strick                                         +88 01713141200                         NGMS Composite Knitting Ind Ltd                                                                           House#110,Road#6,New D.O.H.S       1206                               Dhaka                                                                                          +8801713141200                          213970";
	final String HT_ROW = "D000015100001  BB213970231454002342023024065931517085276714158841   5021                5021                36  F  I  GB US 38 42 10 8  01885087151231-4002340000001                                                                                                                                   I1           Collection           D         EUR 49,99A,B,NL    EUR 55,99GB        GBP 55,00GR        EUR 65,00CH        CHF 79,90                                                            MX 30      80  Produktionsauftrag VG         5021 ";

	@Test
    public void testParseHangtagHeader() throws Exception {
		// TODO
    }

	@Test
	public void testParseHangtagRow() throws Exception {
		// TODO
	}

}
