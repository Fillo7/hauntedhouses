'use strict';

var hauntedHousesApp = angular.module('hauntedHousesApp', ['ngRoute', 'hauntedHousesControllers']);
var hauntedHousesControllers = angular.module('hauntedHousesControllers', []);

hauntedHousesApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                when('/', {templateUrl: 'elements/main_view.html'}).
                when('/login', {templateUrl: 'elements/login.html', controller: 'LoginController'}).
                when('/users/', {templateUrl: 'elements/users_view.html', controller: 'UsersController'}).
                when('/abilities/', {templateUrl: 'elements/abilities_view.html', controller: 'AbilitiesController'}).
                when('/cursedObjects/', {templateUrl: 'elements/cursedObject_view.html', controller: 'CursedObjectController'}).
                when('/houses/', {templateUrl: 'elements/houses_view.html', controller: 'HousesController'}).
                when('/monsters/', {templateUrl: 'elements/monsters_view.html', controller: 'MonstersController'}).
                when('/createAbility', {templateUrl: 'elements/create_ability.html', controller: 'AbilityCreateController'}).
                when('/createCursedObject', {templateUrl: 'elements/create_cursed_object.html', controller: 'CursedObjectCreateController'}).
                when('/createHouse', {templateUrl: 'elements/create_house.html', controller: 'HouseCreateController'}).
                when('/createMonster', {templateUrl: 'elements/create_monster.html', controller: 'MonsterCreateController'}).
                when('/updateAbility/:ability', {templateUrl: 'elements/update_ability.html', controller: 'AbilityUpdateController'}).
                when('/updateCursedObject/:cursedObjectId', {templateUrl: 'elements/update_cursed_object.html', controller: 'CursedObjectUpdateController'}).
                when('/updateHouse/:houseId', {templateUrl: 'elements/update_house.html', controller: 'HouseUpdateController'}).
                when('/updateMonster/:monster', {templateUrl: 'elements/update_monster.html', controller: 'MonsterUpdateController'}).
                // to do: add rest of the (yet unimplemented) paths
                otherwise({redirectTo: '/'});

        // Note: The '/' after monsters/abilities/houses/cursedObjects is intentional.
        // It ensures that whenever the page is redirected at the same page as the one the user is currently browsing,
        // it reloads the content. Feels quicker than to reload via $window.location.reload().
        // Reference: http://stackoverflow.com/a/35139326/4733847
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
    $rootScope.initializeUserSession = function () {
        $rootScope.loggedIn = false;
        $rootScope.userName = undefined;
        $rootScope.userRole = undefined;
    };
});    

/*** Login controller ***/

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
            var token = response.data;
            if(token.authenticationResult) {
                console.log('Login was successful.');
                $rootScope.successAlert = 'Login was successful.';
                $rootScope.loggedIn = token.authenticationResult;
                $rootScope.userName = token.login;
                $rootScope.userRole = token.userRole;
                $location.path("/");
            } else {
                console.log('Login failed.');
                $rootScope.warningAlert = 'Invalid login credentials.';
            }
        }, function error(response) {
            console.log("Error when authenticating user.");
            console.log(response);
            $rootScope.errorAlert = 'Cannot authenticate user! Reason given by the server: ' + response.data.message;
        });
    };
});

hauntedHousesControllers.controller('UsersController', function ($scope, $http) {
    $http.get('/pa165/rest/users').then(function (response) {
        $scope.users = response.data;
    });
});

/**
 * Helper method that returns array of monster IDs from array of monsters with optional checked property.
 * @param {type} selection Array that has items with optional checked property.
 * @return {Array|getMonstersId.monsterIds} Array of checked item IDs.
 */
function getIdsFromSelection(selection) {
    var resultIds = [];

    for (var i = 0; i < selection.length; i++) {
        if (selection[i].checked === true) {
            resultIds.push(selection[i].id);
        }
    }

    return resultIds;
}

/*** Monster controllers ***/

hauntedHousesControllers.controller('MonstersController', function ($scope, $http, $rootScope, $location) {
    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });

    $http.get('/pa165/rest/abilities').then(function (response) {
        $scope.abilities = response.data;
    });

    $scope.filterByAbilityId = function (monster) {
        return function (ability) {
            return ability.monsterIds.indexOf(monster.id) !== -1;
        }
    };

    $scope.delete = function (monster) {
        console.log("Deleting monster with id = " + monster.id + " (" + monster.name + ")");
        $http.delete("rest/monsters/" + monster.id).then(
                function success(response) {
                    console.log("Succesfully deleted monster " + monster.id + " on the server");
                    // Display confirmation alert
                    $rootScope.successAlert = ("Deleted monster \"" + monster.name + "\"");
                    $location.path("/monsters");
                },
                function error(response) {
                    console.log("Error when deleting monster with id \"" + monster.id + "\"");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = "Cannot delete non-existent monster!";
                            break;
                        default:
                            $rootScope.errorAlert = "Cannot delete the monster! Reason given by the server: " + response.data.message;
                            break;
                    }
                }
        );
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
        'houseId': [],
        'abilityIds': []
    };

    // Check if any house has been selected
    $scope.houseSelected = function () {
        if ($scope.house)
            return false;
        return true;
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
                    $rootScope.errorAlert = 'Cannot create monster! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    };
});

hauntedHousesControllers.controller('MonsterUpdateController', function ($scope, $http, $routeParams, $location, $rootScope) {

    $http.get('/pa165/rest/houses').then(function (response) {
        $scope.houses = response.data;
    });

    // Load abilities and add them a "checked" property
    $http.get('/pa165/rest/abilities').then(function (response) {
        $scope.abilities = response.data;

        // Clear checked status
        $scope.abilities.forEach(function (ability) {
            ability.checked = false;
        });

        // Check correct monsters
        for (var i = 0; i < $scope.abilities.length; i++) {
            for (var j = 0; j < $scope.monster.abilityIds.length; j++) {
                if ($scope.abilities[i].id === $scope.monster.abilityIds[j]) {
                    $scope.abilities[i].checked = true;
                }
            }
        }
    });

    $scope.monster = {
        'name': '',
        'description': '',
        'hauntedIntervalStart': '',
        'hauntedIntervalEnd': '',
        'houseId': [],
        'abilityIds': []
    };

    $scope.monster = JSON.parse($routeParams.monster);
    console.log("Monster passed as parameter:");
    console.log($scope.monster);
    console.log("Interval start:");
    console.log($scope.monster.hauntedIntervalStart);

    // Update button clicked
    $scope.update = function (monster) {
        monster.abilityIds = getIdsFromSelection($scope.abilities);

        $http({
            method: 'PUT',
            url: '/pa165/rest/monsters/' + $scope.monster.id,
            data: monster
        }).then(function success(response) {

            var updatedMonster = response.data;
            $rootScope.successAlert = "Monster \"" + updatedMonster.name + "\" was updated.";
            console.log("Monster " + updatedMonster.name + " updated");
            $location.path("/monsters");

        }, function error(response) {

            console.log("Error when attempting to update monster:");
            console.log(monster);
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = "Sent data were found to be invalid by server!";
                    break;
                default:
                    $rootScope.errorAlert = "Cannot update monster! Reason given by the server: " + response.data.message;
                    break;
            }
        });
    };
});

/*** Ability controllers ***/

/**
 * Manipulates listing of all abilities and their deletion.
 */
hauntedHousesControllers.controller('AbilitiesController', function ($scope, $http, $rootScope, $location) {
    $http.get('/pa165/rest/abilities').then(function (response) {
        $scope.abilities = response.data;
    });

    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });

    $scope.filterByMonsterId = function (ability) {
        return function (monster) {
            return ability.monsterIds.indexOf(monster.id) !== -1;
        }
    };

    $scope.delete = function (ability) {
        console.log("Deleting ability with id = " + ability.id + " (" + ability.name + ")");
        $http.delete("rest/abilities/" + ability.id).then(
                function success(response) {
                    console.log("Succesfully deleted ability " + ability.id + " on the server");
                    // Display confirmation alert
                    $rootScope.successAlert = ("Deleted ability \"" + ability.name + "\"");
                    $location.path("/abilities");
                },
                function error(response) {
                    console.log("Error when deleting ability with id \"" + ability.id + "\"");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = "Cannot delete non-existent ability!";
                            break;
                        default:
                            $rootScope.errorAlert = "Cannot delete the ability! Reason given by the server: " + response.data.message;
                            break;
                    }
                }
        );
    };
});

/**
 * Manipulates ability creation.
 */
hauntedHousesControllers.controller('AbilityCreateController', function ($scope, $http, $location, $rootScope) {

    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });

    $scope.ability = {
        'name': '',
        'description': '',
        'monsterIds': []
    };

    // Create button clicked
    $scope.create = function (ability) {
        ability.monsterIds = getIdsFromSelection($scope.monsters);

        $http({
            method: 'POST',
            url: '/pa165/rest/abilities/create',
            data: ability
        }).then(function success(response) {

            var createdAbility = response.data;
            $rootScope.successAlert = "Ability \"" + createdAbility.name + "\" was created.";
            console.log("Ability " + createdAbility.name + " created");
            $location.path("/abilities");

        }, function error(response) {

            console.log("Error when attempting to create ability:");
            console.log(ability);
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = "Sent data were found to be invalid by server!";
                    break;
                default:
                    $rootScope.errorAlert = "Cannot create ability! Reason given by the server: " + response.data.message;
                    break;
            }
        });
    };
});

/**
 * Manipulates ability update.
 */
hauntedHousesControllers.controller('AbilityUpdateController', function ($scope, $http, $routeParams, $location, $rootScope) {

    // Load monsters and add them a "checked" property
    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;

        // Clear checked status
        $scope.monsters.forEach(function (monster) {
            monster.checked = false;
        });

        // Check correct monsters
        for (var i = 0; i < $scope.monsters.length; i++) {
            for (var j = 0; j < $scope.ability.monsterIds.length; j++) {
                if ($scope.monsters[i].id === $scope.ability.monsterIds[j]) {
                    $scope.monsters[i].checked = true;
                }
            }
        }
    });

    $scope.ability = {
        'name': 'Placeholder name',
        'description': 'Placeholder description',
        'monsterIds': []
    };

    $scope.ability = JSON.parse($routeParams.ability);

    // Update button clicked
    $scope.update = function (ability) {
        ability.monsterIds = getIdsFromSelection($scope.monsters);

        $http({
            method: 'PUT',
            url: '/pa165/rest/abilities/' + $scope.ability.id,
            data: ability
        }).then(function success(response) {

            var updatedAbility = response.data;
            $rootScope.successAlert = "Ability \"" + updatedAbility.name + "\" was updated.";
            console.log("Ability " + updatedAbility.name + " updated");
            $location.path("/abilities");

        }, function error(response) {

            console.log("Error when attempting to update ability:");
            console.log(ability);
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = "Sent data were found to be invalid by server!";
                    break;
                default:
                    $rootScope.errorAlert = "Cannot update ability! Reason given by the server: " + response.data.message;
                    break;
            }
        });
    };
});

/*** Cursed object controllers ***/

//loads cursedObjects
function loadAllCursedObjects($http, $scope) {
    $http.get('rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;
    });
}


hauntedHousesControllers.controller('CursedObjectController', function ($scope, $rootScope, $routeParams, $http) {
    loadAllCursedObjects($http, $scope);

    $scope.delete = function (cursedObject) {
        if (confirm("are you sure you want to delete?")) {
            console.log("deleting cursedObject with id=" + cursedObject.id);
            $http.delete('rest/cursedObjects/' + cursedObject.id).then(
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
                                $rootScope.errorAlert = 'Cannot delete cursedObject ! Reason given by the server: ' + response.data.message;
                                break;
                        }
                    }
            );
        }
    };

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
                    $rootScope.errorAlert = 'Cannot create product ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    };
});

hauntedHousesControllers.controller('CursedObjectUpdateController', function ($scope, $routeParams, $http, $location, $rootScope) {
    //show houses in select menu
    $http.get('/pa165/rest/houses').then(function (response) {
        $scope.houses = response.data;
    });

    //get object that should be updated
    var cursedObjectId = $routeParams.cursedObjectId;
    $http.get('/pa165/rest/cursedObjects/' + cursedObjectId).then(function (response) {
        var cursedObject = response.data;
        $scope.cursedObject = cursedObject;
    });

    //update cursedObject function
    $scope.update = function (cursedObject) {
        $http({
            method: 'PUT',
            url: '/pa165/rest/cursedObjects/' + $scope.cursedObject.id,
            data: $scope.cursedObject
        }).then(function success(response) {
            var updatedCursedObject = response.data;
            $rootScope.successAlert = "cursedObject \"" + updatedCursedObject.name + "\" was updated.";
            console.log("Cursed Object " + $scope.cursedObject.name + " updated");
            $location.path("/cursedObjects");

        }, function error(response) {

            console.log("Error when attempting to update cursed object:");
            console.log($scope.cursedObject);
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = "Sent data were found to be invalid by server!";
                    break;
                default:
                    $rootScope.errorAlert = "Cannot update cursed object! Reason given by the server: " + response.data.message;
                    break;
            }
        });
    };
});

/*** House controllers ***/

hauntedHousesControllers.controller('HousesController', function ($scope, $http, $rootScope, $location) {
    $http.get('/pa165/rest/houses').then(function (response) {
        $scope.houses = response.data;
    });

    $scope.delete = function(house) {
        console.log("Deleting house with id = " + house.id + " (" + house.name + ")");
        $http.delete("rest/houses/" + house.id).then(
                function success(response) {
                    console.log("Succesfully deleted house " + house.id + " on the server");
                    // Display confirmation alert
                    $rootScope.successAlert = ("Deleted house \"" + house.name + "\"");
                    $location.path("/houses");
                },
                function error(response) {
                    console.log("Error when deleting house with id \"" + house.id + "\"");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = "Cannot delete non-existent ability!";
                            break;
                        default:
                            $rootScope.errorAlert = "Cannot delete house! Reason given by the server: " + response.data.message;
                            break;
                    }
                }
        );
    };
});

hauntedHousesControllers.controller('HouseCreateController', function ($scope, $http, $rootScope, $location) {

    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });

    $http.get('/pa165/rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;
    });

    $scope.house = {
        'name': '',
        'address': '',
        'monsterIds': [],
        'cursedObjectIds': []
    };

    // Create button clicked
    $scope.create = function (house) {
        house.monsterIds = getIdsFromSelection($scope.monsters);
        house.cursedObjectIds = getIdsFromSelection($scope.cursedObjects);

        $http({
            method: 'POST',
            url: '/pa165/rest/houses/create',
            data: house
        }).then(function success(response) {

            var createdHouse = response.data;
            $rootScope.successAlert = "house \"" + createdHouse.name + "\" was created.";
            console.log("house " + createdHouse.name + " created");
            $location.path("/houses");

        }, function error(response) {

            console.log("Error when attempting to create house:");
            console.log(house);
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = "Sent data were found to be invalid by server!";
                    break;
                default:
                    $rootScope.errorAlert = "Cannot create house! Reason given by the server: " + response.data.message;
                    break;
            }
        });
    };
});

hauntedHousesControllers.controller('HouseUpdateController', function ($scope, $http, $routeParams, $rootScope, $location) {
    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;

        // Clear checked status
        $scope.monsters.forEach(function (monster) {
            monster.checked = false;
        });

        // Check correct monsters
        for (var i = 0; i < $scope.monsters.length; i++) {
            for (var j = 0; j < $scope.house.monsterIds.length; j++) {
                if ($scope.monsters[i].id === $scope.house.monsterIds[j]) {
                    $scope.monsters[i].checked = true;
                }
            }
        }
    });

    $http.get('/pa165/rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;

        // Clear checked status
        $scope.cursedObjects.forEach(function (cursedObject) {
            cursedObject.checked = false;
        });

        // Check correct monsters
        for (var i = 0; i < $scope.cursedObjects.length; i++) {
            for (var j = 0; j < $scope.house.cursedObjectIds.length; j++) {
                if ($scope.cursedObjects[i].id === $scope.house.cursedObjectIds[j]) {
                    $scope.cursedObjects[i].checked = true;
                }
            }
        }
    });

    $scope.house = {
        'name': '',
        'address': '',
        'monsterIds': [],
        'cursedObjectIds': []
    };

    //get object that should be updated
    var houseId = $routeParams.houseId;
    $http.get('/pa165/rest/houses/id/' + houseId).then(function (response) {
        var house = response.data;
        $scope.house = house;
    });

    // Update button clicked
    $scope.update = function (house) {
        house.monsterIds = getIdsFromSelection($scope.monsters);
        house.cursedObjectIds = getIdsFromSelection($scope.cursedObjects);

        $http({
            method: 'PUT',
            url: '/pa165/rest/houses/' + $scope.house.id,
            data: house
        }).then(function success(response) {

            var updatedHouse = response.data;
            $rootScope.successAlert = "house \"" + updatedHouse.name + "\" was updated.";
            console.log("house " + updatedHouse.name + " updated");
            $location.path("/houses");

        }, function error(response) {

            console.log("Error when attempting to update house:");
            console.log(house);
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = "Sent data were found to be invalid by server!";
                    break;
                default:
                    $rootScope.errorAlert = "Cannot update house! Reason given by the server: " + response.data.message;
                    break;
            }
        });
    };
});
