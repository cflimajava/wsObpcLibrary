app.controller("navMenuController",  function($scope, $rootScope, $location){

	if($rootScope.userLogged == null){
		$rootScope.userLogged = JSON.parse(localStorage.getItem("userLogged"));
		if($rootScope.userLogged == null){
			$location.path("/");
		}
	}

	$scope.logout = () => {
		$rootScope.userLogged = null;
		$rootScope.pageName = undefined;
		localStorage.clear();
	}
});