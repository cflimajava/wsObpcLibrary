app.controller("loginCtrl", function($scope, $location, $interval, $rootScope, userFactory){
	
	$rootScope.navMenu = false;
	$rootScope.userLogged = null;

	$scope.appName = "Biblioteca OBPC";
	$scope.subtitulo = "Crie sua pagina de login agora";
	$scope.isAlert = false;
	
	$scope.doLogin = function(credentials){
		userFactory.doLogin(credentials)
			.then(
				(content)=>{
					if(content.status == 200){
						localStorage.setItem("userLogged", JSON.stringify(content.data));
						$rootScope.userLogged = content.data;
						if(content.data.role == "ROLE_ADM"){
							$location.path("/booking");							
						}else{
							$location.path("/search");
						}
						
					}
				},
				(error) =>{
					console.log("Error: ", error);
					if(error.status == 412){
						$scope.showAlert("Usuario não ativado, use o link de ativação enviado para seu email");
					}else if(error.status == 403){
						$scope.showAlert("Usuario ou/e senha incorretos!");
					}
					
				});		
	}


	$scope.showAlert = (message) =>{
		$scope.message = message;
		$scope.isAlert = true;		
	}
	
});