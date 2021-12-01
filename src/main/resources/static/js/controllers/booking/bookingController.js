app.controller("bookingCtrl", function($scope, $rootScope, $location, bookingsPromise){

    $rootScope.navMenu = true;

    $rootScope.pageName = "Gerenciamento de Reservas";
    $rootScope.itemSelecionado = "";


    $scope.bookings = bookingsPromise.data;   

    $scope.filtroSelecionado = "ID";

    $scope.statusSelecionados = [];

    $scope.statusFilter = () =>{
        return function(p){
            for(var i in $scope.statusSelecionados){
                if(p.status == $scope.statusSelecionados[i]){
                    return true;
                }
            }
            if($scope.statusSelecionados.length == 0){
                return true;
            }
        } 
    }

    $scope.managerStatusSelecionados = (statusModificado) =>{
        let deleteIt = false;
        let indexAux = undefined;

        $scope.statusSelecionados.forEach((element,index) => {                        
            if(statusModificado == element){
                indexAux = index; 
                deleteIt = true;               
            }
        });

        (deleteIt && indexAux != undefined) ?  $scope.statusSelecionados.splice(indexAux, 1) : $scope.statusSelecionados.push(statusModificado);       

    }


	$scope.checkStatus = (status) =>{
		if(status == 'DEVOLUCAO PENDENTE')
			return 'statusAtrasado';
	}


    $scope.setFiltro = (filtroSelecionado) =>{
        $scope.filtroSelecionado = filtroSelecionado;
    }

    $scope.ordernarPor = (campo) =>{
        $scope.criterioDeOrdenacao = campo;
        $scope.direcaoDaOrdenacao = !$scope.direcaoDaOrdenacao;
    }
   
    $scope.goToDetail = (bookingId) =>{
        localStorage.setItem("lastBookingSelected", bookingId);
        $location.path("/bookingDetail/"+bookingId);
    }
   
});