app.controller("bookingDetailCtrl", function ($scope, $rootScope, $timeout, bookingPromise, bookingFactory) {
	$rootScope.navMenu = true;
	$rootScope.pageName = "Detalhes da reserva";
	$rootScope.itemSelecionado = localStorage.getItem("lastBookingSelected");
	
	$scope.booking = bookingPromise.data;
	$scope.showMessage = undefined;
	$scope.showPopupSizeLoan = undefined;
	$scope.showNotesPopup = undefined;

	
	$scope.ordernarPor = (campo) => {
		$scope.criterioDeOrdenacao = campo;
		$scope.direcaoDaOrdenacao = !$scope.direcaoDaOrdenacao;
	}

	$scope.removerLivro = (livro) => {
		$scope.booking.books.forEach((book, index) => {
			if (book.id == livro.id) {
				$scope.booking.books.splice(index, 1);
			}
		});
	}

	$scope.openPopupSizeLoan =()=>{
		$scope.showPopupSizeLoan = true;
	}

	$scope.openPopupAddNotas = () =>{
		$scope.showNotesPopup = true;
	}
	
	$scope.addNotas=()=>{
		console.log($scope.booking);
		$scope.showNotesPopup = false;
	}

	$scope.registarRetirada = () =>{
		$scope.showPopupSizeLoan = false;
		let bookingDto = $scope.booking;

		bookingDto.booksId = $scope.booking.books.map((book) =>{
			return book.id
		});

		bookingFactory.checkOut(bookingDto).
			then(
				(content)=>{
					$scope.booking = content.data;
					$scope.openMessage("SUCESSO");
				},
				(error) =>{
					error.data.status == 422 ?
						$scope.errorMessage = "Não foi possível registar retirada, dados pessoais não cadastrados." : 
						$scope.errorMessage = "Não foi possível registar retirada."					
						
						$scope.openMessage("ERROR");
				}
			);
	}

	$scope.registrarDevoulucao = () =>{
		let bookingDto = $scope.booking;
		bookingDto.booksId = $scope.booking.books.map((book) =>{
			return book.id
		});

		bookingFactory.setDevolution(bookingDto).
			then(
				(content)=>{
					$scope.booking = content.data;
					$scope.openMessage("DEVOLVIDO");
				},
				(error) =>{
					error.data.status == 422 ?
						$scope.errorMessage = "Não foi possível registar retirada, dados pessoais não cadastrados." : 
						$scope.errorMessage = "Não foi possível registar retirada."					
						
						$scope.openMessage("ERROR");
				}
			);

		console.log($scope.booking);

		
	}

	$scope.closePopup = () =>{
		$scope.showPopupSizeLoan = undefined;
		$scope.showNotesPopup = undefined;
		$scope.showMessage = false;
	}

	$scope.openMessage=(tipo)=>{
		$scope.showMessage = true;

		if(tipo == "SUCESSO"){
			$scope.tipoMessage = "alert alert-success"
			$scope.errorMessage = undefined;
			$scope.successMessage = "Retirada registardo com sucesso"
		}else if(tipo == "DEVOLVIDO"){
			$scope.tipoMessage = "alert alert-info"
			$scope.errorMessage = undefined;
			$scope.successMessage = "Retirada registardo com sucesso"
		}else{
			$scope.successMessage = undefined;
			$scope.tipoMessage = "alert alert-danger"
		}

		$timeout(()=>{
			$scope.closePopup();
		}, 30000)				
	}

	;

});