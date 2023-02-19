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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * See: https://www.baeldung.com/junit-5-extensions
 */
class JsonTests {

	final String CARS_JSON = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"Ferrari\" }]";
	final String CI_JSON = "{\"part1\":{\"ci1\":\"pct1%\",\"ci2\":\"pct2%\"},\"part2\":{\"ci3\":\"pct3%\"}}";

	@Test
    public void testWriteJavaToJson_thenAssert() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
		final Map<String, String> cars = new HashMap<>();
        cars.put("Yellow", "Renault");
        final String carAsString = objectMapper.writeValueAsString(cars);
        assertThat(carAsString, containsString("Yellow"));
        assertThat(carAsString, not(containsString("Ferrari")));
    }

	@Test
	public void testReadJsonToJava_thenAssert() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<Map<String, String>>> typeRef = new TypeReference<List<Map<String, String>>>() {};
		List<Map<String, String>> list = objectMapper.readValue(CARS_JSON, typeRef);
		assertThat(list, hasSize(2));
		assertThat(list.get(0), hasKey("color"));
	}

	@Test
    public void testWriteJavaToJson() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
		final Map<String, Map<String, String>> cilist = new HashMap<>();

		cilist.put("part1", new HashMap<String, String>());
		Map<String, String> p1 = cilist.get("part1");
		p1.put("ci1", "pct1%");
		p1.put("ci2", "pct2%");

		cilist.put("part2", new HashMap<String, String>());
		Map<String, String> p2 = cilist.get("part2");
		p2.put("ci3", "pct3%");

        final String json = objectMapper.writeValueAsString(cilist);
		System.out.println(json);
		assertThat(json, containsString("part1"));
    }

	@Test
	public void testReadJsonToJava() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<Map<String, Map<String, String>>> typeRef = new TypeReference<Map<String, Map<String, String>>>() {};
		Map<String, Map<String, String>> cilist = objectMapper.readValue(CI_JSON, typeRef);
		System.out.println(cilist);
		assertThat(cilist, aMapWithSize(2));
		assertThat(cilist, hasKey("part1"));
	}

	@Test
    public void testWriteJavaToJson2() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		final List<Map<String, Map<String, String>>> cilist = new ArrayList<>();

		Map<String, Map<String, String>> part1 = new HashMap<>();
		Map<String, String> comp1 = new HashMap<>();
		part1.put("Partname 1", comp1);
		comp1.put("Comp 1", "P1%");
		comp1.put("Comp 2", "P2%");
		cilist.add(part1);

		Map<String, Map<String, String>> part2 = new HashMap<>();
		Map<String, String> comp2 = new HashMap<>();
		part2.put("Partname 2", comp2);
		comp2.put("Comp 3", "P3%");
		comp2.put("Comp 4", "P4%");
		cilist.add(part1);

		final String json = objectMapper.writeValueAsString(cilist);
		System.out.println(json);
		assertThat(json, containsString("Partname 1"));
	}
}
