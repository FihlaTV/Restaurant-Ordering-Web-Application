var itemApp = angular.module('itemApp',[]);

itemApp.controller('itemController', function($scope,$http) {
	$scope.edit=false;
	$scope.csrfToken = {};
	
	$scope.initToken = function(name, token){
		$scope.csrfToken.name = name;
		$scope.csrfToken.token = token;
		$scope.getAllItems();
	}
	$scope.getAllItems = function() {
		
	}
	
	$scope.deleteItem = function(item){
		console.log("In deleted Item");
		item.status=false;
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token,
			item: JSON.stringify(item)
		});
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
		console.log("In enable Item"+JSON.stringify(item));
		item.status=true;
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token,
			item: JSON.stringify(item)
		});
		$http({
			url:'./enableItem',
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
	
	$scope.addItem = function(item){
		console.log("In add"+item.category);
		item.status=true;
		console.log("File: "+$scope.picture);
		console.log("File: "+JSON.stringify(item));
		var fd = new FormData();
		fd.append('item',JSON.stringify(item));
		fd.append('file',$scope.picture);
		
		var xsrf = $.param({
			_csrf : $scope.csrfToken.token,
			item: JSON.stringify(item),
			file: JSON.stringify($scope.picture)
		});
		
		$http({
			url:'./addItem',
			method:'POST',
			data: fd,
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined,
		        'X-CSRF-Token': $scope.csrfToken.token
		    },
			withCredentials : true
		}).success(function(data){
			console.log("addItem success");
			if(!$scope.edit){
				item.id=data;
				$scope.items.push(item);
			} else {
				item.id=item.id;
				$scope.edit = false;
			}		
			$scope.picture = null;
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