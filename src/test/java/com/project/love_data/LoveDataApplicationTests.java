package com.project.love_data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class LoveDataApplicationTests {

	@Test
	void contextLoads() {
		Set<String> stringSet = new HashSet<>();

		stringSet.add("asdf");

		System.out.println(stringSet.size());
	}

}
