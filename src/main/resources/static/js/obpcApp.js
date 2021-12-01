var app = angular.module("obpcApp", ['ngRoute', 'ngAnimate']);

app.config(function($routeProvider, $locationProvider){

	$routeProvider
	.when("/",{
		templateUrl:'templates/login.html',
		controller: 'loginCtrl'
	})
	.when("/search",{
		templateUrl:'templates/search.html',
		controller: 'searchCtrl'
	})
	.when("/users", {
		templateUrl:'templates/customer/userList.html',
		controller: 'userListCtrl',
		resolve:{
			userPromise: (userFactory) =>{
				return userFactory.getAllUsers();
			}
		}

	})
	.when("/booking", {
		templateUrl:'templates/booking/bookingManage.html',
		controller: 'bookingCtrl',
		resolve:{
			bookingsPromise: function(bookingFactory){
				return bookingFactory.getAllBookings();
			}
		}
	})
	.when("/bookingDetail/:bookingId", {
		templateUrl:'templates/booking/bookingDetail.html',
		controller: 'bookingDetailCtrl',
		resolve:{
			bookingPromise: (bookingFactory, $route) =>{
				return bookingFactory.getBookingById($route.current.params.bookingId);
			}
		}
	})
	.when("/bookingDetail", {
		templateUrl:'templates/booking/bookingDetail.html',
		controller: 'bookingDetailCtrl',
		resolve:{
			bookingPromise: (bookingFactory) =>{				
				return bookingFactory.getBookingById(localStorage.getItem("lastBookingSelected"));
			}
		}
	})	
	.when("/customerForm/:userId", {
		templateUrl:'templates/customer/cutomerForm.html',
		controller: 'customerFormCtrl',
		resolve:{
			customerPromise: (customerFactory, $route) =>{	
				return customerFactory.getCustomerByUserId($route.current.params.userId);
			}
		}

	})
	.when("/customerForm", {
		templateUrl:'templates/customer/cutomerForm.html',
		controller: 'customerFormCtrl',
		resolve:{
			customerPromise: (customerFactory) =>{
				return customerFactory.getCustomerByUserId(JSON.parse(localStorage.getItem("lastUserSelected")).id);
			}
		}

	})
	.otherwise({redirectTo:"/"})
	
	$locationProvider.html5Mode(true);
})