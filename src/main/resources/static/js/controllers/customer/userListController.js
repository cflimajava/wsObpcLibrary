app.controller("userListCtrl", function($scope, $rootScope, $location, userPromise){
	$rootScope.navMenu = true;

	$rootScope.pageName = "Gerenciamento de usuáros";
	
	$scope.users =userPromise.data;	 

 	$scope.ordernarPor = (campo) =>{
        $scope.criterioDeOrdenacao = campo;
        $scope.direcaoDaOrdenacao = !$scope.direcaoDaOrdenacao;
	}
	

	$scope.goToCustomerForm = (user) =>{
		localStorage.setItem("lastUserSelected", JSON.stringify(user));
		$rootScope.itemSelecionado = user.username;		
        $location.path("/customerForm/"+user.id);
    }

});