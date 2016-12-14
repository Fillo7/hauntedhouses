'use strict';

angular.module('angularApp')
    .controller('MonstersCtrl', function ($scope) {
        //TODO toto len nahradit za REST call a je hotovo
        $scope.priserky = [
            {
                name: 'Sebastian',
                description: 'desc',
                hauntedIntervalStart: null,
                hauntedIntervalEnd: null
            },
            {
                name: 'Momo',
                description: 'desc 2',
                hauntedIntervalStart: null,
                hauntedIntervalEnd: null
            }
        ];
    });
