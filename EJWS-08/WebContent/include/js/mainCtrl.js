var mesControllers = angular.module('mesControllers', []);

mesControllers.controller('monCtrl1', ['$scope', '$http', '$routeParams', 'maFactory', function($scope, $http, $routeParams, maFactory){
	  
	  if($routeParams.login !== undefined){
	    var login = $routeParams.login;
	  }
	  
	  if($routeParams.password !== undefined){
	    var password = $routeParams.password;
	  }
  
	maFactory.getResultat(login, password).then(//promise
	  function(data){//success
	    $scope.resultat = data;
	  }
	);
  
}]);