package com.test.service;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.oauth.OAuthService;

public class OAuthServiceProvider<T> {

	private OAuthServiceConfig<? extends Api> config;

	public OAuthServiceProvider() {
	}

	public OAuthServiceProvider(OAuthServiceConfig<? extends Api> config) {
		this.config = config;
	}

	public OAuthService getService() {
		return new ServiceBuilder().provider(config.getApiClass())
				.apiKey(config.getApiKey()).apiSecret(config.getApiSecret())
				.callback(config.getCallback()).build();
	}

}
