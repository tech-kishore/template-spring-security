package com.kishore.cors101.cors;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebResource {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("persons")
	public List<Person> getPersons() {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person(1, "John"));
		persons.add(new Person(2, "Mary"));
		return persons;
	}

	@PostMapping("persons")
	public void getPersons(@RequestBody Person person) {
		logger.info("CREATE -> {}", person);
	}

}

record Person(int id, String name) {
};
