package com.project.love_data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@SpringBootTest
class LoveDataApplicationTests {

	@Test
	void contextLoads() {
		Set<String> stringSet = new HashSet<>();

		stringSet.add("asdf");

		System.out.println(stringSet.size());
	}

	@Test
	public void TempTest() {
		String userDir = System.getProperty("user.dir");
		String userHome = System.getProperty("user.home");
		String javaHome = System.getProperty("java.home");

		System.out.println("(User dir) : " + userDir);
		System.out.println("(User home) : " + userHome);
		System.out.println("(java home) : " + javaHome);

		URI userDirURI = URI.create(userDir);
		URI userHomeURI = URI.create(userHome);
		URI javaHomeURI = URI.create(javaHome);

		Path userDirPath = Paths.get(userDirURI);
		Path userHomePath = Paths.get(userHomeURI);
		Path javaHomePath = Paths.get(javaHomeURI);

		System.out.println(userDirPath);
		System.out.println(userHomePath);
		System.out.println(javaHomePath);
	}

	@Test
	public void SetTest() {
		Set<String> stringSet = new HashSet<>();

		stringSet.add("temp");
		stringSet.add("tired");
		stringSet.add("help");

		System.out.println();
	}
}
