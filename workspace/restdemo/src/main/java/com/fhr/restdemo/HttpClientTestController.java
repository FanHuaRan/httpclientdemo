package com.fhr.restdemo;

import java.io.Serializable;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/HttpClientTest")
public class HttpClientTestController {
	@RequestMapping(value="/get",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse getTest(){
		return new RestResponse();
	}
	
	@RequestMapping(value="/postJson",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse postJsonTest(@RequestBody RestJsonRequest jsonRequest){
		return new RestResponse();
	}
	@RequestMapping(value="/postForm",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse postFormTest(@RequestParam Integer id,
									 @RequestParam Integer age){
		return new RestResponse();
	}
	
	public static class RestJsonRequest implements Serializable{
		private static final long serialVersionUID = 440588409273154863L;
		private Integer id;
		private Integer test;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getTest() {
			return test;
		}
		public void setTest(Integer test) {
			this.test = test;
		}
		@Override
		public String toString() {
			return "RestJsonRequest [id=" + id + ", test=" + test + "]";
		}
	}
	
	public static class RestResponse implements Serializable{
		private static final long serialVersionUID = 5120292963069716928L;
		private final Integer id=1;
		private final String name="jack";
		private final Integer age=12;
		
		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public Integer getAge() {
			return age;
		}

		@Override
		public String toString() {
			return "RestResponse [id=" + id + ", name=" + name + ", age=" + age + "]";
		}
	}
}
