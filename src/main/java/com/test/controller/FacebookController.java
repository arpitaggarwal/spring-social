package com.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.test.service.FacebookUtil;
import com.test.service.OAuthServiceProvider;

@RequestMapping(value = "/social/facebook")
@Component
public class FacebookController<FacebookApi> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FacebookController.class);
	private static final String FACEBOOK = "facebook";
	private static final String PUBLISH_SUCCESS = "success";
	private static final String PUBLISH_ERROR = "error";

	@Autowired
	private ConnectionFactoryRegistry connectionFactoryRegistry;

	@Autowired
	private OAuth2Parameters oAuth2Parameters;

	@Autowired
	FacebookUtil facebookUtil;

	@Autowired
	@Qualifier("facebookServiceProvider")
	private OAuthServiceProvider<FacebookApi> facebookServiceProvider;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactoryRegistry
				.getConnectionFactory(FACEBOOK);
		OAuth2Operations oauthOperations = facebookConnectionFactory
				.getOAuthOperations();
		oAuth2Parameters.setState("recivedfromfacebooktoken");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(
				GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
		RedirectView redirectView = new RedirectView(authorizeUrl, true, true,
				true);

		return new ModelAndView(redirectView);
	}

	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	@ResponseBody
	public String postOnWall(@RequestParam("code") String code,
			@RequestParam("state") String state, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OAuthService oAuthService = facebookServiceProvider.getService();

		Verifier verifier = new Verifier(code);
		Token accessToken = oAuthService
				.getAccessToken(Token.empty(), verifier);

		FacebookTemplate template = new FacebookTemplate(accessToken.getToken());

		FacebookProfile facebookProfile = template.userOperations()
				.getUserProfile();

		String userId = facebookProfile.getId();

		LOGGER.info("Logged in User Id : {}", userId);

		MultiValueMap<String, Object> map = facebookUtil
				.publishLinkWithVisiblityRestriction(state);
		try {
			template.publish(facebookProfile.getId(), "feed", map);
		} catch (Exception ex) {
			LOGGER.error(
					"Exception Occurred while posting a link on facebook for user Id : {}, exception is : {}",
					userId, ex);
			return PUBLISH_ERROR;
		}

		return PUBLISH_SUCCESS;
	}

	@RequestMapping(value = "/callback", params = "error_reason", method = RequestMethod.GET)
	@ResponseBody
	public void error(@RequestParam("error_reason") String errorReason,
			@RequestParam("error") String error,
			@RequestParam("error_description") String description,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			LOGGER.error(
					"Error occurred while validating user, reason is : {}",
					errorReason);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, description);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
