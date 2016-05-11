var FoodOrderApp = angular.module('FoodOrderApp', [ 'angular.filter' ]);

FoodOrderApp.filter('status', function() {
	return function(input) {
		switch(input){
		case "C" :
			return "Cancelled";
		case "N" :
			return "Order Submitted";
		case "P" :
			return "Preparing Order";
		case "R" :
			return "Ready For Pickup";
		case "F" :
			return "Order Fullfilled";
		default:
			return "";
		}
	};
});

FoodOrderApp
		.config(function($compileProvider) {
			$compileProvider
					.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
			$compileProvider
					.imgSrcSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
		});
FoodOrderApp.controller('HomeController', function($scope, $http) {

	$scope.csrfToken = {};
	$scope.orderHistory = {};

	$scope.init = function(name, token) {
		$scope.csrfToken.name = name;
		$scope.csrfToken.token = token;
		$scope.getOrderHistory();
	}
	
	$scope.cancelOrder = function(order){
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token,
			orderid:order.Id
		});
		$http({
			method : 'POST',
			url : './cancelSubmittedOrder',
			data : xsrf,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			withCredentials : true
		}).success(function(data, status, headers, config) {
			$scope.getOrderHistory();
			console.log(data);
			console.log("Order Cancel Completed!!");
		}).error(function(data, status, headers, config) {
			console.log("Order Cancel Failed :: " + data);
		});
	}

	$scope.getOrderHistory = function() {
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token
		});
		$http({
			method : 'POST',
			url : './getOrderHistory',
			data : xsrf,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			withCredentials : true
		}).success(function(data, status, headers, config) {
			$scope.orderHistory = data;
			console.log(data);
			console.log("Order History Completed!!");
		}).error(function(data, status, headers, config) {
			console.log("Order History Failed :: " + data);
		});
	}

});