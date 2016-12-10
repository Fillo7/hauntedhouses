'use strict';

var hauntedHousesApp = angular.module('hauntedHousesApp', ['ngRoute', 'hauntedHousesControllers']);
var hauntedHousesControllers = angular.module('hauntedHousesControllers', []);

hauntedHousesApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/monsters', {templateUrl: 'elements/monsters_view.html', controller: 'MonstersController'}).
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

hauntedHousesControllers.controller('MonstersController',
    function ($scope, $rootScope, $routeParams, $http) {
        // get monster id from URL fragment #/monster/:monsterId
        var monsterId = $routeParams.monsterId;
        $http.get('/pa165/rest/monsters' + monsterId).then(
            function (response) {
                $scope.monster = response.data;
                console.log('AJAX loaded detail of monster ' + $scope.monster.name);
            },
            function error(response) {
                console.log("failed to load monster " + monsterId);
                console.log(response);
                $rootScope.warningAlert = 'Cannot load monster: '+response.data.message;
            }
        );
    });

// To do: add rest of the controllers
