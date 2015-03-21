package com.test.service;

public class OAuthServiceConfig<T> {

	private String apiKey;
	private String apiSecret;
	private String callback;
	private Class<T> apiClass;

	public OAuthServiceConfig() {
	}

	public OAuthServiceConfig(String apiKey, String apiSecret, String callback,
			Class<T> apiClass) {
		super();
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.callback = callback;
		this.apiClass = apiClass;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Class<T> getApiClass() {
		return apiClass;
	}

	public void setApiClass(Class<T> apiClass) {
		this.apiClass = apiClass;
	}
}
