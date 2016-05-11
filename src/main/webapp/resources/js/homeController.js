var FoodOrderApp = angular.module('FoodOrderApp', ['angular.filter']);
FoodOrderApp.config(function( $compileProvider ) {   
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
    $compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
  }
);
FoodOrderApp.controller('HomeController', function($scope, $http) {
	
	$scope.csrfToken = {};
	$scope.orderHistory = {};
	
	$scope.getOrderHistory = function(){
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
			console.log("Order Init Completed!!");
		}).error(function(data, status, headers, config) {
			console.log("Order Init Failed :: " + data);
		});
	}
	
	$scope.init = function(name,token){
		$scope.csrfToken.name = name;
		$scope.csrfToken.token = token;
		$scope.getOrderHistory();
	}
	
});