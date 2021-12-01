app.factory("userFactory", function($http, $rootScope){
	
	var _doLogin = function(credentials){
		return $http.get("user/login", {
			headers : {
				"username":credentials.username,
				"password":credentials.password
			}
		});
	}

	var _getAllUsers = () =>{
		return $http.get("user/getAll", {
			headers : {
				"HEADERS_REQUESTER":$rootScope.userLogged.id,
				"HEADERS_TOKEN":$rootScope.userLogged.token
			}
		});
	}
	
	
	return {
		doLogin : _doLogin,
		getAllUsers : _getAllUsers
	}
	
});