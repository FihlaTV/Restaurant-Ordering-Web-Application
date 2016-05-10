var itemApp = angular.module('itemApp',[]);

itemApp.controller('itemController', function($scope,$http) {
	$scope.dummy = "Welcome Admin";
	$scope.edit=false;
	$scope.csrfToken = {};
	
	$scope.initToken = function(name, token){
		$scope.csrfToken.name = name;
		$scope.csrfToken.token = token;
	}
	
	$scope.deleteItem = function(item){
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token,
			data: item
		});
		console.log("In deleted Item");
		item.status=false;
		$http({
			url:'./deleteItem',
			method:'POST',
			data: xsrf,
			responseType: "json",
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			withCredentials: true
		}).success(function(data){
			console.log("deleteItem success");
		}).error(function(data){
			console.log("deleteItem Error");
		});
	}
	
	$scope.enableItem = function(item){
		console.log("In enable Item");
		item.status=true;
		$http({
			url:'./enableItem',
			method:'POST',
			data: item,
			responseType: "json",
			headers: {'Content-Type': 'application/json'}
		}).success(function(data){
			console.log("deleteItem success");
		}).error(function(data){
			console.log("deleteItem Error");
		});
	}
	
	$scope.addItem = function(item){
		console.log("In add"+item.category);
		item.status=true;
		console.log("File: "+$scope.picture);
		console.log("File: "+JSON.stringify(item));
		var fd = new FormData();
		fd.append('item',JSON.stringify(item));
		fd.append('file',$scope.picture);
		fd.append('_csrf', $scope.csrfToken.token);
		
//		var xsrf = $.param({
//			_csrf : $scope.csrfToken.token,
//			item: JSON.stringify(item),
//			file:$scope.picture
//		});
		
		$http({
			url:'./addItem',
			method:'POST',
			data: fd,
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined},
			withCredentials : true
		}).success(function(data){
			console.log("addItem success");
			if(!$scope.edit){
				$scope.items.push(item);
			} else {
				$scope.edit = false;
			}
			
			$scope.newItem=null;
			$('#addItemModal').modal('hide');
		}).error(function(data){
			console.log("addItem Error");
		});
	}
	
	$scope.editClicked = function(item){
		$scope.edit = true;
		$scope.newItem=item;
	}
	
	$scope.resetOrder = function(){
		
		console.log("In reset");
		//To implement reset order
	}
})
.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);;