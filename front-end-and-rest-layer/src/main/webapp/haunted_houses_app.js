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
        when('/createCursedObject', {templateUrl: 'elements/create_cursed_object.html', controller: 'CursedObjectCreateController'}).
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
        'login': '',
        'password': ''
    };
    
    $scope.login = function (user) {
        $http({
            method: 'POST',
            url: '/pa165/rest/users/authenticate',
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

//loads cursedObjects
function loadAllCursedObjects($http, $scope) {
    $http.get('rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;
    });
}


hauntedHousesControllers.controller('CursedObjectController', function ($scope, $rootScope, $routeParams, $http) {
    loadAllCursedObjects($http, $scope);
    
    
    $scope.delete = function (cursedObject) {
            if(confirm("are you sure you want to delete?")){
            console.log("deleting cursedObject with id=" + cursedObject.id);
            $http.delete('rest/cursedObjects/'+cursedObject.id).then(
                function success(response) {
                    console.log('deleted cursedObject ' + cursedObject.id + ' on server');
                    //display confirmation alert
                    $rootScope.successAlert = 'Deleted cursedObject "' + cursedObject.name + '"';
                    loadAllCursedObjects($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting cursedObject");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = 'Cannot delete non-existent cursedObject ! ';
                            break;
                        default:
                            $rootScope.errorAlert = 'Cannot delete cursedObject ! Reason given by the server: '+response.data.message;
                            break;
                    }
                }
            );
        }};
    
});

hauntedHousesControllers.controller('CursedObjectCreateController', function ($scope, $routeParams, $http, $location, $rootScope) {
    
    $http.get('/pa165/rest/houses').then(function (response) {
        $scope.houses = response.data;
    });
    
    $scope.cursedObject = {
        'name': '',
        'description': '',
        'monsterAttractionFactor': '',
        'houseId': ''
    };
    
    $scope.create = function (cursedObject) {
            $http({
                method: 'POST',
                url: '/pa165/rest/cursedObjects/create',
                data: cursedObject
            }).then(function success(response) {
                var createdCursedObject = response.data;
                //display confirmation alert
                console.log('created cursed object');
                $rootScope.successAlert = 'A new cursedObject "' + createdCursedObject.name + '" was created';
                //change view to list of cursedObjects
                $location.path("/pa165/cursedObjects");
            }, function error(response) {
                //display error
                console.log("error when creating cursedObject");
                console.log(response);
                switch (response.data.code) {
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create product ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
});

// To do: add rest of the controllers
