package com.fhr.httpclientdemo.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class HttpUtilsTest {
	private static final String GET_URL="http://localhost:8080/HttpClientTest/get";
	private static final String POST_JSON_URL="http://localhost:8080/HttpClientTest/postJson";
	private static final String POST_FORM_URL="http://localhost:8080/HttpClientTest/postForm";

	@Test
	public void testGet() throws ClientProtocolException, IOException {
		String response=HttpUtils.get(GET_URL);
	}

	@Test
	public void testPostForm() throws ClientProtocolException, IOException {
		List<NameValuePair> params=new LinkedList<>();
		params.add(new BasicNameValuePair("id", "1"));
		params.add(new BasicNameValuePair("age", "2"));
		String response=HttpUtils.postForm(POST_FORM_URL, params);

	}

	@Test
	public void testPostJson() throws ClientProtocolException, IOException {
		String response=HttpUtils.postJson(POST_JSON_URL,"{\"id\":1,\"test\":1}");
	}

}
