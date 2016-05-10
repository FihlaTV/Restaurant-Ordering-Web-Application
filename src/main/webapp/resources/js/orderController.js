var FoodOrderApp = angular.module('FoodOrderApp', ['angular.filter']);
FoodOrderApp.config(function( $compileProvider ) {   
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
    $compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
  }
);
FoodOrderApp.controller('OrderController', function($scope, $http) {
	
	$scope.csrfToken = {};
	$scope.items = [];
	$scope.cartItems = [];
	$scope.order = {};
	$scope.order.submit = false;
	$scope.order.pickupdateSet = true;
	$scope.order.buttonText = "Get Earliest Pickup Time";
	
	$scope.changePickupDate = function(){
		console.log("Changed Pickup Date!!" + document.getElementById("pickupdate").value);
		if($scope.order.pickupdate == "" || $scope.order.pickupdate == undefined){
			console.log("Date Null");
			$scope.order.pickupdateSet = true;
			//$scope.order.pickupdate = "";
		}else{
			console.log("Date present" + $scope.order.pickupdate );
			$scope.order.pickupdateSet = false;
			//$scope.order.pickupdate = document.getElementById("pickupdate").value;
			$scope.order.submit = false;
		}
	}
	
	$scope.changePickupTime = function(){
		console.log("Changed Pickup Time!!" + $scope.order.pickuptime);
		if($scope.order.pickuptime == "" || $scope.order.pickuptime == undefined){
			$scope.order.buttonText = "Get Earliest Pickup Time";
			//	$scope.order.pickuptime = "";
			$scope.order.submit = false;
		}else{
			$scope.order.buttonText = "Check Pickup Date Availability";
			//$scope.order.pickuptime = document.getElementById("pickuptime").value;
			$scope.order.submit = false;	
		}
	}
	
	$scope.validatePickupDate = function(){
		console.log("validatePickupDate!!");
		if(document.getElementById("pickupdate").value == "" || document.getElementById("pickupdate").value == undefined){
			alert("Please Enter the Pick Up Date for Your Order!!!");
		}else{
			if(document.getElementById("pickuptime").value != "" && document.getElementById("pickuptime").value != undefined){
				//Check if the Estimated Pickup time is Feasible as Pick up Time.
				var xsrf = $.param({
					_csrf : $scope.csrfToken.token,
					pickuptime:$scope.order.pickuptime,
					pickupdate:$scope.order.pickupdate
				});
				$http({
					method : 'POST',
					url : './checkCustomerPickupDateTime',
					data : xsrf,
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					withCredentials : true
				}).success(function(data, status, headers, config) {
					console.log(data);
					if(data.pickupdatetime){
						$scope.order.submit = true;	
					}else{
						$scope.order.submit = false;
						$scope.order.error = true;
						$scope.order.errorMessage = "There was Slight Problem in Placing Your Order," +
								" Your food won't be ready for Pickup at the time you have mentioned." +
								" For the Date <b>" + $scope.order.pickupdate + "</b>," +
								" We estimated that the <b>Earliest Time<b> we can prepare your Order and Keep it Ready for pickup is." + data.estimatedDateTime;
					}
					console.log("checkCustomerPickupDate!!");
				}).error(function(data, status, headers, config) {
					console.log("checkCustomerPickupDate:: " + data);
					$scope.order.submit = false;
				});
			}else{
				//Get the Earliest Available Pickup Time
				console.log("Get the Earliest Available Pickup Time");
			}
		}
		
	}
	
	$scope.submitOrder = function(){
		console.log("submitOrder!!");
		if($scope.order.pickupdate == document.getElementById("pickupdate").value){
			var xsrf = $.param({
				_csrf : $scope.csrfToken.token,
				item:item
			});
			$http({
				method : 'POST',
				url : './submitOrder',
				data : xsrf,
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				withCredentials : true
			}).success(function(data, status, headers, config) {
				console.log(data);
				$scope.cartItems = data;
				console.log("Menu Item Removed to Cart!!");
			}).error(function(data, status, headers, config) {
				console.log("Error Removed Menu Items :: " + data); 	
			});
		}else{
			$scope.order.submit = false;
			alert("Pickup Date changed, Please Rerun the Validation to Check if this Solt ")
		}
	}
	
	$scope.deleteLineItem = function(item){
		console.log("deleteLineItemfunction");
		var cancel = confirm("Are You Sure you want to Delete this Line Item??");
		if (cancel == true) {
			var xsrf = $.param({
				_csrf : $scope.csrfToken.token,
				item:item
			});
			$http({
				method : 'POST',
				url : './deleteLineItem',
				data : xsrf,
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				withCredentials : true
			}).success(function(data, status, headers, config) {
				console.log(data);
				$scope.cartItems = data;
				console.log("Menu Item Removed to Cart!!");
			}).error(function(data, status, headers, config) {
				console.log("Error Removed Menu Items :: " + data); 	
			});
		} else {
		   console.log("Order not Cancelled");
		}
	}
	
	$scope.cancelOrder = function(){
		console.log("cancelOrder");
		var cancel = confirm("Are You Sure you want to Cancel the Order??");
		if (cancel == true) {
			var xsrf = $.param({
				_csrf : $scope.csrfToken.token
			});
			$http({
				method : 'POST',
				url : './cancelOrder',
				data : xsrf,
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				withCredentials : true
			}).success(function(data, status, headers, config) {
				console.log(data);
				$scope.cartItems = [];
				console.log("Menu Item Added to Cart!!");
			}).error(function(data, status, headers, config) {
				console.log("Error Added Menu Items :: " + data);
			});
		} else {
		   console.log("Order not Cancelled");
		}
	}
	
	$scope.changeQty = function(item){
		console.log("changeQty");
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token,
			item : item
		});
		$http({
			method : 'POST',
			url : './updateItemQuantity',
			data : xsrf,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			withCredentials : true
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.cartItems = data;
			console.log("Menu Item Added to Cart!!");
		}).error(function(data, status, headers, config) {
			console.log("Error Added Menu Items :: " + data);
		});
	}
	
	$scope.AddLineItem = function(item){
		delete item.Picture;
		console.log("Add Line Item");
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token,
			item : item
		});
		$http({
			method : 'POST',
			url : './addLineItem',
			data : xsrf,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			withCredentials : true
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.cartItems = data;
			console.log("Menu Item Added to Cart!!");
		}).error(function(data, status, headers, config) {
			console.log("Error Added Menu Items :: " + data);
		});
	}
	
	$scope.getShoppingCart = function() {
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token
		});
		$http({
			method : 'POST',
			url : './getShoppingCart',
			data : xsrf,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			withCredentials : true
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.cartItems = data;
			console.log("Shopping Item Retrieved!!");
		}).error(function(data, status, headers, config) {
			console.log("Error Getting Shopping Items :: " + data);
		});
	}
	
	$scope.getItems = function() {
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token
		});
		$http({
			method : 'POST',
			url : './getMenuItems',
			data : xsrf,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			withCredentials : true
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.items = data;
			console.log("Menu Item Retrieved!!");
		}).error(function(data, status, headers, config) {
			console.log("Error Getting Menu Items :: " + data);
		});
	}
	
	$scope.initializeOrder = function(){
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token
		});
		$http({
			method : 'POST',
			url : './initializeOrder',
			data : xsrf,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			withCredentials : true
		}).success(function(data, status, headers, config) {
			console.log(data);
			console.log("Order Init Completed!!");
		}).error(function(data, status, headers, config) {
			console.log("Order Init Failed :: " + data);
		});
	}
	
	$scope.init = function(name,token){
		$scope.csrfToken.name = name;
		$scope.csrfToken.token = token;
		$scope.initializeOrder();
		$scope.getItems();
		$scope.getShoppingCart();
		$scope.changePickupDate();
	}
	
});