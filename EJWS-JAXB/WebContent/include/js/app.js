var app = angular.module('monApp', ['mesControllers']);

app.config(['$routeProvider', function($routeProvider){
  
	// ce qu'il doit faire quand on se trouve dans telle ou telle url
	$routeProvider
		.when('/test', {
			templateUrl: 'rest/authentification',
			controller: 'monCtrl1'
		})
		.otherwise({
			redirectTo: '/'
		});
		
}]);