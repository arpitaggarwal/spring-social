var myApp = angular.module('enterprise', []);

myApp.controller('SocialController', function($window, $scope) {
	$scope.shareOnFacebook = function() {
		$window.location.replace("/SpringSocial/social/facebook/signin");
	};
	$scope.shareOnTwitter = function() {
		$window.location.replace("/SpringSocial/social/twitter/signin");
	};
});