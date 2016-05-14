var FoodOrderApp = angular.module('FoodOrderApp', [ 'angular.filter' ]);
function starRating() {
	return {
		restrict : 'EA',
		template : '<ul class="star-rating" ng-class="{readonly: readonly}">'
				+ '  <li ng-repeat="star in stars" class="star" ng-class="{filled: star.filled}" ng-click="toggle($index)">'
				+ '    <i class="fa fa-star"></i>' + // or &#9733
				'  </li>' + '</ul>',
		scope : {
			ratingValue : '=ngModel',
			max : '=?', // optional (default is 5)
			onRatingSelect : '&?',
			readonly : '=?'
		},
		link : function(scope, element, attributes) {
			if (scope.max == undefined) {
				scope.max = 5;
			}

			function updateStars() {
				scope.stars = [];
				for (var i = 0; i < scope.max; i++) {
					scope.stars.push({
						filled : i < scope.ratingValue
					});
				}
			}
			;
			scope.toggle = function(index) {
				if (scope.readonly == undefined || scope.readonly === false) {
					scope.ratingValue = index + 1;
					scope.onRatingSelect({
						rating : index + 1
					});
				}
			};
			scope.$watch('ratingValue', function(oldValue, newValue) {
				if (newValue) {
					updateStars();
				}
			});
		}
	};
}
FoodOrderApp.filter('datetime', function($filter) {
	return function(input) {
		if (input == null) {
			return "";
		}

		var _date = $filter('date')(new Date(input), 'HH:mm a MMM dd yyyy');

		return _date.toUpperCase();

	};
});
FoodOrderApp
		.config(function($compileProvider) {
			$compileProvider
					.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
			$compileProvider
					.imgSrcSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
		});
FoodOrderApp.directive('starRating', starRating);
FoodOrderApp
		.controller(
				'OrderController',
				function($scope, $http) {

					$scope.csrfToken = {};
					$scope.items = [];
					$scope.cartItems = [];
					$scope.order = {};
					$scope.order.submit = false;
					$scope.order.pickupdateSet = false;
					$scope.order.buttonText = "Get Earliest Pickup Time";

					$scope.rating = {};
					$scope.rating.rating = 1;

					$scope.changePickupDate = function() {
						console.log("Changed Pickup Date!!"
								+ $scope.order.pickupdate);
						if ($scope.order.pickupdate == ""
								|| $scope.order.pickupdate == undefined) {
							console.log("Date Null");
							$scope.order.pickupdateSet = true;
						} else {
							console.log("Date present"
									+ $scope.order.pickupdate);
							$scope.order.pickupdateSet = false;
							$scope.order.submit = false;
						}
					}

					$scope.changePickupTime = function() {
						console.log("Changed Pickup Time!!"
								+ $scope.order.pickuptime);
						if ($scope.order.pickuptime == ""
								|| $scope.order.pickuptime == undefined) {
							$scope.order.buttonText = "Get Earliest Pickup Time";
							$scope.order.submit = false;
						} else {
							$scope.order.buttonText = "Check Pickup Time Availability";
							$scope.order.submit = false;
						}
					}

					$scope.validatePickupDate = function() {

						// Reset the Error Message
						$scope.order.error = false;

						console.log("validatePickupDate!!");
						if ($scope.order.pickuptime != ""
								&& $scope.order.pickuptime != undefined) {

							// Check if the Estimated Pickup time is Feasible as
							// Pick up Time.
							var xsrf = $.param({
								_csrf : $scope.csrfToken.token,
								pickuptime : $scope.order.pickuptime,
								pickupdate : $scope.order.pickupdate
							});
							$http(
									{
										method : 'POST',
										url : './checkCustomerPickupDateTime',
										data : xsrf,
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded'
										},
										withCredentials : true
									})
									.success(
											function(data, status, headers,
													config) {
												console.log(data);
												if (data.pickupdatetime) {
													$scope.order.submit = true;
													$scope.order.error = true;
													if ($scope.order.pickuptime != data.estimatedDateTime) {
														$scope.order.errorMessage = "There was Slight Problem in Placing Your Order,"
																+ " Your food won't be ready for Pickup at the time you have mentioned."
																+ " For the Date "
																+ $scope.order.pickupdate
																+ ","
																+ " We estimated that the Earliest Time we can prepare your Order and Keep it Ready for pickup on "
																+ data.estimatedDateTime;
														$scope.order.estimatedDateTime = data.estimatedDateTime;
														$scope.order.pickuptime = data.estimatedDateTime;
													} else {
														$scope.order.errorMessage = "Great News!! We can Process your Order and keep it read for you to pick up at your Preferred Time Slot on "
																+ $scope.order.pickupdate
																+ " at "
																+ $scope.order.pickuptime;
													}
												} else {
													$scope.order.submit = false;
													$scope.order.error = true;
													$scope.order.errorMessage = "We are Sorry, There are no Pickup Slots avaiable to Fulfill your Order on "
															+ $scope.order.pickupdate
															+ "!! Please Revise Your Order to reduce the Items or Change Your Order Pickup  Date to Proceed";
													$scope.order.estimatedDateTime = "";
													$scope.order.pickuptime = data.estimatedDateTime;
													$scope.order.estimatedDateTime = data.estimatedDateTime;
												}
												console
														.log("checkCustomerPickupDate!!");
											})
									.error(
											function(data, status, headers,
													config) {
												console
														.log("checkCustomerPickupDate:: "
																+ data);
												$scope.order.error = true;
												$scope.order.errorMessage = "There was an Error Processing Your Order, Please Try again....";
												$scope.order.submit = false;
											});
						} else {
							// Get the Earliest Available Pickup Time
							console
									.log("Get the Earliest Available Pickup Time");
							var xsrf = $.param({
								_csrf : $scope.csrfToken.token,
								pickuptime : $scope.order.pickuptime,
								pickupdate : $scope.order.pickupdate
							});
							$http(
									{
										method : 'POST',
										url : './estimatePickupDataTime',
										data : xsrf,
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded'
										},
										withCredentials : true
									})
									.success(
											function(data, status, headers,
													config) {
												console.log(data);
												if (data.pickupdatetime) {
													$scope.order.submit = true;
													$scope.order.error = true;
													$scope.order.errorMessage = "We estimated that the Earliest Time We can prepare your Order and Keep it Ready for pickup is "
															+ data.estimatedDateTime;
													$scope.order.estimatedDateTime = data.estimatedDateTime;
													$scope.order.pickuptime = data.estimatedDateTime;
												} else {
													$scope.order.submit = false;
													$scope.order.error = true;
													$scope.order.errorMessage = "We are Sorry, There are no Pickup Slots avaiable to Fulfill your Order on "
															+ $scope.order.pickupdate
															+ "!! Please Revise Your Order to reduce the Items or Change Your Order Pickup  Date to Proceed";
													$scope.order.estimatedDateTime = "";
												}
												console
														.log("checkCustomerPickupDate!!");
											})
									.error(
											function(data, status, headers,
													config) {
												console
														.log("checkCustomerPickupDate:: "
																+ data);
												$scope.order.error = true;
												$scope.order.errorMessage = "There was an Error Processing Your Order, Please Try again....";
												$scope.order.submit = false;
												$scope.order.estimatedDateTime = "";
											});
						}
					}

					$scope.submitOrder = function() {
						console.log("submitOrder!!");
						if ($scope.order.pickupdate == document
								.getElementById("pickupdate").value) {
							var xsrf = $.param({
								_csrf : $scope.csrfToken.token
							});
							$http(
									{
										method : 'POST',
										url : './submitOrder',
										data : xsrf,
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded'
										},
										withCredentials : true
									})
									.success(
											function(data, status, headers,
													config) {
												console.log(data);
												$scope.cartItems = data;
												console
														.log("Menu Item Removed to Cart!!");
											})
									.error(
											function(data, status, headers,
													config) {
												console
														.log("Error Removed Menu Items :: "
																+ data);
											});
						} else {
							$scope.order.submit = false;
							alert("Pickup Date changed, Please Rerun the Validation to Check if this Solt ")
						}
					}

					$scope.deleteLineItem = function(item) {
						console.log("deleteLineItemfunction");
						var cancel = confirm("Are You Sure you want to Delete this Line Item??");
						if (cancel == true) {
							var xsrf = $.param({
								_csrf : $scope.csrfToken.token,
								item : item
							});
							$http(
									{
										method : 'POST',
										url : './deleteLineItem',
										data : xsrf,
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded'
										},
										withCredentials : true
									})
									.success(
											function(data, status, headers,
													config) {
												console.log(data);
												$scope.cartItems = data;
												$scope.order.submit = false;
												console
														.log("Menu Item Removed to Cart!!");
											})
									.error(
											function(data, status, headers,
													config) {
												console
														.log("Error Removed Menu Items :: "
																+ data);
											});
						} else {
							console.log("Order not Cancelled");
						}
					}

					$scope.cancelOrder = function() {
						console.log("cancelOrder");
						var cancel = confirm("Are You Sure you want to Cancel the Order??");
						if (cancel == true) {
							var xsrf = $.param({
								_csrf : $scope.csrfToken.token
							});
							$http(
									{
										method : 'POST',
										url : './cancelOrder',
										data : xsrf,
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded'
										},
										withCredentials : true
									})
									.success(
											function(data, status, headers,
													config) {
												console.log(data);
												$scope.cartItems = [];
												$scope.order.submit = false;
												console
														.log("Menu Item Added to Cart!!");
											})
									.error(
											function(data, status, headers,
													config) {
												console
														.log("Error Added Menu Items :: "
																+ data);
											});
						} else {
							console.log("Order not Cancelled");
						}
					}

					$scope.changeQty = function(item) {
						console.log("changeQty");
						var xsrf = $.param({
							_csrf : $scope.csrfToken.token,
							item : item
						});
						$http(
								{
									method : 'POST',
									url : './updateItemQuantity',
									data : xsrf,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									withCredentials : true
								}).success(
								function(data, status, headers, config) {
									console.log(data);
									$scope.cartItems = data;
									$scope.order.submit = false;
									console.log("Menu Item Added to Cart!!");
								}).error(
								function(data, status, headers, config) {
									console.log("Error Added Menu Items :: "
											+ data);
								});
					}

					$scope.AddLineItem = function(item) {
						delete item.Picture;
						console.log("Add Line Item");
						var xsrf = $.param({
							_csrf : $scope.csrfToken.token,
							item : item
						});
						$http(
								{
									method : 'POST',
									url : './addLineItem',
									data : xsrf,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									withCredentials : true
								}).success(
								function(data, status, headers, config) {
									console.log(data);
									$scope.cartItems = data;
									$scope.order.submit = false;
									console.log("Menu Item Added to Cart!!");
								}).error(
								function(data, status, headers, config) {
									console.log("Error Added Menu Items :: "
											+ data);
								});
					}

					$scope.getShoppingCart = function() {
						var xsrf = $.param({
							_csrf : $scope.csrfToken.token
						});
						$http(
								{
									method : 'POST',
									url : './getShoppingCart',
									data : xsrf,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									withCredentials : true
								})
								.success(
										function(data, status, headers, config) {
											console.log(data);
											$scope.cartItems = data;
											console
													.log("Shopping Item Retrieved!!");
										})
								.error(
										function(data, status, headers, config) {
											console
													.log("Error Getting Shopping Items :: "
															+ data);
										});
					}

					$scope.getItems = function() {
						var xsrf = $.param({
							_csrf : $scope.csrfToken.token
						});
						$http(
								{
									method : 'POST',
									url : './getMenuItems',
									data : xsrf,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									withCredentials : true
								}).success(
								function(data, status, headers, config) {
									console.log(data);
									$scope.items = data;
									console.log("Menu Item Retrieved!!");
								}).error(
								function(data, status, headers, config) {
									console.log("Error Getting Menu Items :: "
											+ data);
								});
					}

					$scope.initializeOrder = function() {
						var xsrf = $.param({
							_csrf : $scope.csrfToken.token
						});
						$http(
								{
									method : 'POST',
									url : './initializeOrder',
									data : xsrf,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									withCredentials : true
								})
								.success(
										function(data, status, headers, config) {
											console.log(data);
											console
													.log("Order Init Completed!!");
										})
								.error(
										function(data, status, headers, config) {
											console.log("Order Init Failed :: "
													+ data);
										});
					}

					$scope.init = function(name, token) {
						$scope.csrfToken.name = name;
						$scope.csrfToken.token = token;
						$scope.initializeOrder();
						$scope.getItems();
						$scope.getShoppingCart();
						$scope.changePickupDate();
					}

				});