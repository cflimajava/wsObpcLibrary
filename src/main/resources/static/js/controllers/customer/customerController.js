app.controller("customerFormCtrl", function($scope, $rootScope, customerPromise, customerFactory){

    $rootScope.navMenu = true;

    $rootScope.pageName = "Gerenciamento de dados pessoais";

    $scope.pageName = "Cadastro de dados pessoais";

    $scope.customer = customerPromise.data;

    $scope.userSelected = JSON.parse(localStorage.getItem("lastUserSelected"));


    $scope.saveCustomer = (customer) =>{
        $scope.customer.userId = $scope.userSelected.id;        
        customerFactory.saveCustomer(customer)
            .then((content) =>{
                $scope.customer = content.data;
            },
            (error) =>{
                console.log(error);
            })
    }

    $scope.updateCustomer = (customer) =>{        
        customerFactory.updateCustomer(customer)
            .then((content) =>{
                $scope.customer = content.data;
            },
            (error) =>{
                console.log(error);
            })
    }

});