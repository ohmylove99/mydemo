package org.octopus.api.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.octopus.api.model.Department;
import org.octopus.api.model.Greeting;
import org.octopus.api.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	@GetMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	@GetMapping("/hello/weekdays")
	public List<String> helloweekdays() {
		List<String> weekdays = new ArrayList<String>();
		weekdays.add("Mon.");
		weekdays.add("Tues.");
		weekdays.add("Wed.");
		weekdays.add("Thur.");
		weekdays.add("Fri.");
		return weekdays;
	}

	@GetMapping("/hello/employee")
	public Map<String, String> helloemployee() {
		Map<String, String> employee = new HashMap<String, String>();
		employee.put("001", "Jason");
		employee.put("002", "Peter");
		return employee;
	}

	@GetMapping("/hello/department")
	public ResponseEntity<Department> getDepartment() {
		Department department = new Department();
		department.setId(Utils.getUUID());
		department.setName("Technology");
		return ResponseEntity.ok().body(department);
	}
	
	@RequestMapping(value = "/greet", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Greeting greet() {
        Greeting greeting = new Greeting();
        greeting.setId(1);
        greeting.setMessage("Hello World!!!");
        return greeting;
    }
	
	@RequestMapping(value = "/greetWithPathVariable/{name}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Greeting greetWithPathVariable(@PathVariable("name") String name) {
        Greeting greeting = new Greeting();
        greeting.setId(1);
        greeting.setMessage("Hello World " + name + "!!!");
        return greeting;
    }

    @RequestMapping(value = "/greetWithQueryVariable", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Greeting greetWithQueryVariable(@RequestParam("name") String name) {
        Greeting greeting = new Greeting();
        greeting.setId(1);
        greeting.setMessage("Hello World " + name + "!!!");
        return greeting;
    }
    
    @RequestMapping(value = "/greetWithPostAndFormData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Greeting greetWithPostAndFormData(@RequestParam("id") int id, @RequestParam("name") String name) {
        Greeting greeting = new Greeting();
        greeting.setId(id);
        greeting.setMessage("Hello World " + name + "!!!");
        return greeting;
    }
}
