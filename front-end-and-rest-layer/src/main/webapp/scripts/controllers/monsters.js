'use strict';

angular.module('angularApp')
    .controller('MonstersCtrl', function ($scope, $http) {
        $http.get('/pa165/rest/monsters').then(function (response) {
            $scope.priserky = response.data;
        });
    });
