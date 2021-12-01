app.factory("customerFactory", function ($http, $rootScope) {

	var config = {
		headers: {
			"HEADERS_REQUESTER": $rootScope.userLogged.id,
			"HEADERS_TOKEN": $rootScope.userLogged.token
		}
	};

	var _getCustomerByUserId = function (userId) {
		return $http.get("customer/getByUserId/" + userId, config);
	}


	var _saveCustomer = (customer) => {
		console.log("_saveCustomer: ", customer);
		var url = "customer/create";
		var data = customer;
		return $http.post(url, data, config);
	}

	var _updateCustomer = (customer) =>{
		var url = "customer/update";
		var data = customer;
		return $http.put(url, data, config);
	}

	return {
		getCustomerByUserId: _getCustomerByUserId,
		saveCustomer: _saveCustomer,
		updateCustomer: _updateCustomer
	}

});