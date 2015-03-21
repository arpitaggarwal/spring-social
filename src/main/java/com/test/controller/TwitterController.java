package com.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping(value = "/social/twitter")
@Controller
public class TwitterController<TwitterApi> {

	private static final String PUBLISH_SUCCESS = "success";

	private static final String TWITTER = "twitter";

	@Autowired
	private ConnectionFactoryRegistry connectionFactoryRegistry;

	@Autowired
	private OAuth1Parameters oAuth1Parameters;

	@Value("${app.config.oauth.twitter.callback}")
	private String callback;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TwitterConnectionFactory twitterConnectionFactory = (TwitterConnectionFactory) connectionFactoryRegistry
				.getConnectionFactory(TWITTER);
		OAuth1Operations oauthOperations = twitterConnectionFactory
				.getOAuthOperations();

		OAuthToken oAuthToken = oauthOperations.fetchRequestToken(callback,
				oAuth1Parameters);
		String authorizeUrl = oauthOperations.buildAuthenticateUrl(
				oAuthToken.getValue(), oAuth1Parameters);
		RedirectView redirectView = new RedirectView(authorizeUrl, true, true,
				true);

		return new ModelAndView(redirectView);
	}

	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	@ResponseBody
	public String springSocialCallback(
			@RequestParam("oauth_token") String oauthToken,
			@RequestParam("oauth_verifier") String oauthVerifier,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TwitterTemplate twitterTemplate = new TwitterTemplate(oauthToken);
		TimelineOperations timelineOperations = twitterTemplate
				.timelineOperations();
		timelineOperations.updateStatus("Hello Twitter!");
		return PUBLISH_SUCCESS;
	}
}
