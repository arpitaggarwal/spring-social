package com.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class FacebookUtil {

	@Value("${app.config.oauth.facebook.apikey}")
	private String apikey;

	public MultiValueMap<String, Object> publishLinkWithVisiblityRestriction(
			String state) {
		MultiValueMap<String, Object> userRestrictedMap = new LinkedMultiValueMap<String, Object>();
		userRestrictedMap.set("privacy", "{value:\"EVERYONE\"}");
		userRestrictedMap.set("message",
				"My Post through Spring Social using Facebook Graph API");
		userRestrictedMap.set("picture", "");
		userRestrictedMap.set("caption", "ArpitAggarwal.in");
		return userRestrictedMap;
	}
}
