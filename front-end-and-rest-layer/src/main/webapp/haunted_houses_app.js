'use strict';

var hauntedHousesApp = angular.module('hauntedHousesApp', ['ngRoute', 'hauntedHousesControllers']);
var hauntedHousesControllers = angular.module('hauntedHousesControllers', []);

hauntedHousesApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/monsters', {templateUrl: 'elements/monsters_view.html', controller: 'MonstersController'}).
        when('/createMonster', {templateUrl: 'elements/create_monster.html', controller: 'MonsterCreateController'}).
        when('/login', {templateUrl: 'elements/login.html', controller: 'LoginController'}).
        when('/abilities', {templateUrl: 'elements/abilities_view.html', controller: 'AbilitiesController'}).
        when('/cursedObjects', {templateUrl: 'elements/cursedObject_view.html', controller: 'CursedObjectController'}).
        when('/createCursedObject', {templateUrl: 'elements/create_cursed_object.html', controller: 'CursedObjectController'}).
        // to do: add rest of the (yet unimplemented) paths
        otherwise({redirectTo: '/'});
    }]);

hauntedHousesApp.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});

hauntedHousesControllers.controller('MonstersController', function ($scope, $http) {
    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });
});

hauntedHousesControllers.controller('LoginController', function ($scope, $routeParams, $http, $location, $rootScope) {
    $scope.user = {
        'name': '',
        'password': ''
    };
    
    $scope.login = function (user) {
        $http({
            method: 'POST',
            url: '/pa165/rest/authenticate',
            data: user
        }).then(function success(response) {
            console.log('login was successful');
            var loggedUser = response.data;
            $rootScope.successAlert = 'User "' + loggedUser.name + '" logged in.';
            $location.path("/");
        }, function error(response) {
            console.log("error when authenticating user");
            console.log(response);
            switch (response.data.code) {
            case 'InvalidRequestException':
                $rootScope.errorAlert = 'Sent data were found to be invalid by server!';
                break;
            default:
                $rootScope.errorAlert = 'Cannot authenticate user! Reason given by the server: '+response.data.message;
                break;
            }
        });
    };
});

hauntedHousesControllers.controller('MonsterCreateController', function ($scope, $routeParams, $http, $location, $rootScope) {
    $http.get('/pa165/rest/houses').then(function (response) {
        $scope.houses = response.data;
    });
    $http.get('/pa165/rest/abilities').then(function (response) {
        $scope.abilities = response.data;
    });

    $scope.monster = {
        'name': '',
        'description': '',
        'hauntedIntervalStart': '',
        'hauntedIntervalEnd': '',
        'houseId': '',
        'abilityIds': ''
    };

    // Function called when submit button is clicked, creates monster on server
    $scope.create = function (monster) {
        $http({
            method: 'POST',
            url: '/pa165/rest/monsters/create',
            data: monster
        }).then(function success(response) {
            console.log('created monster');
            var createdMonster = response.data;
            $rootScope.successAlert = 'A new monster "' + createdMonster.name + '" was created';
            $location.path("/monsters");
        }, function error(response) {
            console.log("error when creating monster");
            console.log(response);
            switch (response.data.code) {
            case 'InvalidRequestException':
                $rootScope.errorAlert = 'Sent data were found to be invalid by server!';
                break;
            default:
                $rootScope.errorAlert = 'Cannot create monster! Reason given by the server: '+response.data.message;
                break;
            }
        });
    };
});

hauntedHousesControllers.controller('AbilitiesController', function ($scope, $http) {
    $http.get('/pa165/rest/abilities').then(function (response) {
        $scope.abilities = response.data;
    });
});


hauntedHousesControllers.controller('CursedObjectController', function ($scope, $http) {
    $http.get('/pa165/rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;
    });
});

hauntedHousesControllers.controller('CursedObjectController', function ($scope, $routeParams, $http, $location, $rootScope) {
    $http.get('/pa165/rest/houses').then(function (response) {
        $scope.houses = response.data;
    });

    $scope.cursedObject = {
        'name': '',
        'description': '',
        'monsterAttractionFactor': '',
        'houseId': '',
    };
});
// To do: add rest of the controllers
