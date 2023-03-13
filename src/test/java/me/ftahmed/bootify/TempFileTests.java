package me.ftahmed.bootify;

import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TempFileTests {

	private final String UPLOAD_DIR = "./uploads/";

	@Test
	public void testTempFilename() throws Exception {
		Path path = Files.createTempFile(Paths.get(UPLOAD_DIR), "", "");
		log.info(path.getFileName().toString());
		assertThat("Not null", path.getFileName().toString() != null);
	}
}
