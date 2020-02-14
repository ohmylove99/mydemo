package org.octopus.api.lt;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class AppControllerLT {
	private RestTemplate restTemplate = new RestTemplate();
	private final static String BaseUrl = "http://localhost:8080";

	@Test
	public void when_api_app_health_then_200() throws JSONException {
		String expected = "{\"status\":\"UP\"}";

		ResponseEntity<String> response = restTemplate.getForEntity(BaseUrl + "/api/app/health", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void when_api_app_findById_then_found_200() throws JSONException {
		String expected = "{\"id\":\"1\",\"appId\":\"403\",\"shortName\":\"app1\",\"name\":\"application1\",\"contact\":\"a@gmail.com\"}";

		ResponseEntity<String> response = restTemplate.getForEntity(BaseUrl + "/api/app/1", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test(expected = HttpClientErrorException.class)
	public void when_api_app_findById_then_notfound_404() {
		restTemplate.getForEntity(BaseUrl + "/api/app/100", String.class);
	}
}
