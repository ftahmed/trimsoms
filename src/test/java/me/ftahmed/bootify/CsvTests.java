package me.ftahmed.bootify;

import me.ftahmed.bootify.util.CsvNameAndPositionMappingStrategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import com.opencsv.CSVWriter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class CsvTests {

	@Test
    public void testBeanToCsvNameAndPositionStrategy() throws Exception {
		CsvNameAndPositionMappingStrategy<Pojo> mappingStrategy = new CsvNameAndPositionMappingStrategy<>();
		mappingStrategy.setType(Pojo.class);

		Path tempFile = Files.createTempFile("pojo-export-", ".csv");
		log.info("Temp file: " + tempFile.toString());

		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(tempFile.toFile()))) {
			StatefulBeanToCsv<Pojo> beanToCsv = new StatefulBeanToCsvBuilder<Pojo>(csvWriter)
					.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
					.withMappingStrategy(mappingStrategy)
					.build();

			List<Pojo> pojoList = new ArrayList<>();
			
			Pojo pojo1 = new Pojo();
			pojo1.setVoucherSeries("v1");
			pojo1.setSalePurchaseType("sp1");
			pojoList.add(pojo1);
			
			Pojo pojo2 = new Pojo();
			pojo2.setVoucherSeries("v2");
			pojo2.setSalePurchaseType("sp2");
			pojoList.add(pojo2);

			beanToCsv.write(pojoList);
		} catch (Exception e) {
			log.error("Failed to write CSV", e);
		}
    }

	@Getter
	@Setter
	public static class Pojo {
		@CsvBindByName(column="Voucher Series") // header: "Voucher Series"
		@CsvBindByPosition(position=1)
		private String voucherSeries;

		@CsvBindByPosition(position=0) // header: "salePurchaseType"
		private String salePurchaseType;

		@CsvBindByName(column="Third Column") // header: Third Column
		@CsvBindByPosition(position=2)
		private String thirdColumn;

		private String ignoredColumn;
	}
}
