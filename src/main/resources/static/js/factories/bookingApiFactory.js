app.factory("bookingFactory", function ($http, $rootScope) {

	var config = {
		headers: {
			"HEADERS_REQUESTER": $rootScope.userLogged.id,
			"HEADERS_TOKEN": $rootScope.userLogged.token
		}
	};


	var _getBookingByUserId = function (userId) {
		return $http.get("booking/user/" + userId, config);
	}

	var _getAllBookings = function () {
		return $http.get("booking/getAll", config);
	}

	var _getBookingById = function (bookingId) {
		return $http.get("booking/" + bookingId, config);
	}

	var _checkOut = (booking) => {
		return $http.put("booking/checkOut", booking, config);
	}

	var _setDevolution = (booking) =>{
		return $http.put("booking/devolution", booking, config);
	}

	return {
		getBookingByUserId: _getBookingByUserId,
		getAllBookings: _getAllBookings,
		getBookingById: _getBookingById,
		checkOut: _checkOut,
		setDevolution: _setDevolution
	}

});