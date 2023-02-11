package com.kishore.templatespringsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoResource {
	
	private Logger logger =LoggerFactory.getLogger(getClass());
	
	@GetMapping("todos")
	public Todo retrieveTodo() {
		return new Todo("Learn Spring Security");
	}
	
	@PostMapping("todos")
	public void createTodo(@RequestBody Todo todo) {
		logger.info("Post->     {}",todo);
	}

}

record Todo(String description) {};
