app.factory('maFactory', ['$http', function($http){
  var factory = {
    getResultat: function(login, mdp){
      return $http.put('rest/authentification?login=' + login + '&password=' + mdp).then(
        function(data){
          return data;
        },
        function(error) {
            alert('erreur ' + error.status);
        }
      );
    }
  };
  
  return factory;
  
}]);