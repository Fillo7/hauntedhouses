'use strict';

var hauntedHousesApp = angular.module('hauntedHousesApp', ['ngCookies', 'ngRoute', 'hauntedHousesControllers']);
var hauntedHousesControllers = angular.module('hauntedHousesControllers', []);

hauntedHousesApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                when('/', {templateUrl: 'elements/main_view.html'}).
                when('/login', {templateUrl: 'elements/login.html', controller: 'LoginController'}).
                when('/logout', {templateUrl: 'elements/main_view.html', controller: 'LogoutController'}).
                when('/users/', {templateUrl: 'elements/users_view.html', controller: 'UsersController'}).
                when('/abilities/', {templateUrl: 'elements/abilities_view.html', controller: 'AbilitiesController'}).
                when('/cursedObjects/', {templateUrl: 'elements/cursed_objects_view.html', controller: 'CursedObjectController'}).
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
                otherwise({redirectTo: '/'});

        // Note: The '/' after monsters/abilities/houses/cursedObjects is intentional.
        // It ensures that whenever the page is redirected at the same page as the one the user is currently browsing,
        // it reloads the content. Feels quicker than to reload via $window.location.reload().
        // Reference: http://stackoverflow.com/a/35139326/4733847
    }
]);

hauntedHousesApp.run(function ($rootScope, $cookies, $timeout) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
    
    $rootScope.showSuccess = function (message) {
         $rootScope.successAlert = message;
         $timeout(function () { $rootScope.successAlert = undefined; }, 5000);   
    };
    
    $rootScope.showWarning = function (message) {
         $rootScope.warningAlert = message;
         $timeout(function () { $rootScope.warningAlert = undefined; }, 5000);   
    };
    
    $rootScope.showError = function (message) {
         $rootScope.errorAlert = message;
         $timeout(function () { $rootScope.errorAlert = undefined; }, 5000);   
    };
    
    $rootScope.userName = $cookies.get('userName');
    $rootScope.isUser = ($cookies.get('isUser') === 'true');
    $rootScope.isAdmin = ($cookies.get('isAdmin') === 'true');
});

/*** User controllers ***/

hauntedHousesControllers.controller('LoginController', function ($scope, $http, $location, $rootScope, $cookies) {
    $scope.user = {
        'login': '',
        'password': ''
    };

    $scope.login = function (user) {
        $http({
            method: 'POST',
            url: 'rest/users/authenticate',
            data: user
        }).then(function success(response) {
            var token = response.data;
            if (token.authenticationResult) {
                $cookies.put('userName', token.login);
                $cookies.put('isUser', 'true');
                
                $rootScope.isUser = true;
                $rootScope.userName = token.login;
                if (token.userRole === 'ADMIN') {
                    $cookies.put('isAdmin', 'true');
                    $rootScope.isAdmin = true;
                } else {
                    $cookies.put('isAdmin', 'false');
                    $rootScope.isAdmin = false;
                }
                
                console.log('Login was successful.');
                $rootScope.showSuccess('Login was successful.');
                $location.path("/");
            } else {
                console.log('Login failed.');
                $rootScope.showWarning('Invalid login credentials.');
            }
        }, function error(response) {
            console.log("Error when authenticating user.");
            console.log(response);
            $rootScope.showError('Cannot authenticate user! Reason given by the server: ' + response.data.message);
        });
    };
});

hauntedHousesControllers.controller('LogoutController', function ($location, $rootScope, $cookies) {
    $cookies.remove('userName');
    $cookies.remove('isUser');
    $cookies.remove('isAdmin');
    $rootScope.isUser = false;
    $rootScope.isAdmin = false;
    $rootScope.userName = undefined;
    $rootScope.showSuccess('Logout was successful.');
    $location.path("/");
});

hauntedHousesControllers.controller('UsersController', function ($scope, $http) {
    $http.get('rest/users').then(function (response) {
        $scope.users = response.data;
    });
});

/*** Monster controllers ***/

/**
 * Helper method that returns array of IDs from array with optional checked property.
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

/**
 * Helper method that returns array of names from array with optional checked property.
 * @param {type} selection Array that has items with optional checked property.
 * @return {Array|getMonstersId.monsterIds} Array of checked item names.
 */
function getNamesFromSelection(selection) {
    var resultNames = [];

    for (var i = 0; i < selection.length; i++) {
        if (selection[i].checked === true) {
            resultNames.push(selection[i].name);
        }
    }

    return resultNames;
}

hauntedHousesControllers.controller('MonstersController', function ($scope, $http, $rootScope, $location) {
    $http.get('/pa165/rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });
    
    $http.get('/pa165/rest/houses').then(function (response) {
        $scope.houses = response.data;
    });
    $http.get('/pa165/rest/abilities').then(function (response) {
        $scope.abilities = response.data;
    });

    $scope.filterByAbilityId = function (monster) {
        return function (ability) {
            return ability.monsterIds.indexOf(monster.id) !== -1;
        };
    };

    $scope.houseIdFilter = function (houseId) {
        return function (house) {
            return house.id === houseId;
        };
    };
    
    $scope.delete = function (monster) {
        if (!confirm("Are you certain you want to delete selected monster?")) {
            return;
        }

        console.log("Deleting monster with id = " + monster.id + " (" + monster.name + ")");
        $http.delete("rest/monsters/" + monster.id).then(
                function success(response) {
                    console.log("Succesfully deleted monster " + monster.id + " on the server");
                    $rootScope.showSuccess("Deleted monster \"" + monster.name + "\"");
                    $location.path("/monsters");
                },
                function error(response) {
                    console.log("Error when deleting monster with id \"" + monster.id + "\"");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.showError("Cannot delete non-existent monster!");
                            break;
                        default:
                            $rootScope.showError("Cannot delete the monster! Reason given by the server: " + response.data.message);
                            break;
                    }
                }
        );
    };
});

hauntedHousesControllers.controller('MonsterCreateController', function ($scope, $http, $location, $rootScope) {
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
        'abilityIds': []
    };

    console.log($scope.monster);

    // Function called when submit button is clicked, creates monster on server
    $scope.create = function (monster) {

        monster.abilityIds = getIdsFromSelection($scope.abilities);

        $http({
            method: 'POST',
            url: '/pa165/rest/monsters/create',
            data: monster
        }).then(function success(response) {
            console.log('created monster');
            var createdMonster = response.data;
            $rootScope.showSuccess('A new monster "' + createdMonster.name + '" was created');
            $location.path("/monsters");
        }, function error(response) {
            console.log("Error when creating monster: " + monster);
            console.log(response);
            $rootScope.showError('Cannot create monster! Input is invalid.');
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

        // Check correct abilities
        for (var i = 0; i < $scope.abilities.length; i++) {
            for (var j = 0; j < $scope.monster.abilityIds.length; j++) {
                if ($scope.abilities[i].id === $scope.monster.abilityIds[j]) {
                    $scope.abilities[i].checked = true;
                }
            }
        }
    });

    $scope.monster = JSON.parse($routeParams.monster);
    console.log($scope.monster);
    
    // Update button clicked
    $scope.update = function (monster) {
        console.log("Monster to be updated:");
        console.log(monster);
        
        // Ability selection is ignored
        monster.abilityIds = [];
        //monster.abilityIds = getIdsFromSelection($scope.abilities);

        $http({
            method: 'PUT',
            url: '/pa165/rest/monsters/' + $scope.monster.id,
            data: monster
        }).then(function success(response) {

            var updatedMonster = response.data;
            $rootScope.showSuccess("Monster \"" + updatedMonster.name + "\" was updated.");
            console.log("Monster " + updatedMonster.name + " updated");
            console.log(updatedMonster);
            $location.path("/monsters");

        }, function error(response) {
            console.log("Error when attempting to update monster: " + monster);
            console.log(response);
            $rootScope.showError('Cannot update monster! Input is invalid.');
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
        };
    };

    $scope.delete = function (ability) {
        if (!confirm("Are you certain you want to delete selected ability?")) {
            return;
        }

        console.log("Deleting ability with id = " + ability.id + " (" + ability.name + ")");
        $http.delete("rest/abilities/" + ability.id).then(
                function success(response) {
                    console.log("Succesfully deleted ability " + ability.id + " on the server");
                    $rootScope.showSuccess("Deleted ability \"" + ability.name + "\"");
                    $location.path("/abilities");
                },
                function error(response) {
                    console.log("Error when deleting ability with id \"" + ability.id + "\"");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.showError("Cannot delete non-existent ability!");
                            break;
                        default:
                            $rootScope.showError("Cannot delete the ability! Reason given by the server: " + response.data.message);
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
            $rootScope.showSuccess("Ability \"" + createdAbility.name + "\" was created.");
            console.log("Ability " + createdAbility.name + " created");
            $location.path("/abilities");

        }, function error(response) {
            console.log("Error when attempting to create ability: " + ability);
            console.log(response);
            $rootScope.showError("Cannot create ability! Reason given by the server: " + response.data.message);
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
            $rootScope.showSuccess("Ability \"" + updatedAbility.name + "\" was updated.");
            console.log("Ability " + updatedAbility.name + " updated");
            $location.path("/abilities");

        }, function error(response) {
            console.log("Error when attempting to update ability: " + ability);
            console.log(response);
            $rootScope.showError("Cannot update ability! Reason given by the server: " + response.data.message);
        });
    };
});

/*** Cursed object controllers ***/

hauntedHousesControllers.controller('CursedObjectController', function ($scope, $rootScope, $location, $http) {
    $http.get('rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;
    });

    $http.get('rest/houses').then(function (response) {
        $scope.houses = response.data;
    });

    $scope.houseIdFilter = function (houseId) {
        return function (house) {
            return house.id === houseId;
        };
    };

    $scope.threshold = {
        'threshold': ''
    };

    $scope.increaseFactor = function (threshold) {
        $http({
            method: 'POST',
            url: 'rest/cursedObjects/increase',
            data: threshold
        }).then(function success() {
            $rootScope.showSuccess('Monster attraction factor was successfully increased on cursed objects below selected threshold.');
            $location.path("/cursedObjects");
        }, function error(response) {
            console.log("Error when increasing monster attraction factor.");
            console.log(response);
            $rootScope.showError('Cannot increase monster attraction factor! Reason given by server: ' + response.data.message);
        });
    };

    $scope.delete = function (cursedObject) {
        if (!confirm("Are you certain you want to delete selected cursed object?")) {
            return;
        }

        console.log("Deleting cursed object with id: " + cursedObject.id);
        $http.delete('rest/cursedObjects/' + cursedObject.id).then(
            function success(response) {
                console.log('Deleted cursed object ' + cursedObject.id + ' on server');
                $rootScope.showSuccess('Deleted cursed object: "' + cursedObject.name + '"');
                $location.path("/cursedObjects");
            },
            function error(response) {
                console.log("Error when deleting cursed object");
                console.log(response);
                switch (response.data.code) {
                    case 'ResourceNotFoundException':
                        $rootScope.showError('Cannot delete non-existent cursed object!');
                        break;
                    default:
                        $rootScope.showError('Cannot delete cursed object! Reason given by the server: ' + response.data.message);
                        break;
                }
            }
        );
    };
});

hauntedHousesControllers.controller('CursedObjectCreateController', function ($scope, $http, $location, $rootScope) {
    $http.get('rest/houses').then(function (response) {
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
            url: 'rest/cursedObjects/create',
            data: cursedObject
        }).then(function success(response) {
            var createdCursedObject = response.data;
            console.log('Created cursed object');
            $rootScope.showSuccess('Cursed object with name "' + createdCursedObject.name + '" was created.');
            $location.path("/cursedObjects");
        }, function error(response) {
            console.log("Error when creating cursed object");
            console.log(response);
            $rootScope.showError('Cannot create cursed object! Reason given by the server: ' + response.data.message);
        });
    };
});

hauntedHousesControllers.controller('CursedObjectUpdateController', function ($scope, $routeParams, $http, $location, $rootScope) {
    // Load houses into select menu
    $http.get('rest/houses').then(function (response) {
        $scope.houses = response.data;
    });

    // Get object that should be updated
    var cursedObjectId = $routeParams.cursedObjectId;
    $http.get('rest/cursedObjects/' + cursedObjectId).then(function (response) {
        var cursedObject = response.data;
        $scope.cursedObject = cursedObject;
    });

    // Update cursedObject function
    $scope.update = function (cursedObject) {
        $http({
            method: 'PUT',
            url: 'rest/cursedObjects/' + $scope.cursedObject.id,
            data: $scope.cursedObject
        }).then(function success(response) {
            var updatedCursedObject = response.data;
            $rootScope.showSuccess('Cursed object with name "' + updatedCursedObject.name + '" was updated.');
            console.log("Cursed Object " + $scope.cursedObject.name + " updated");
            $location.path("/cursedObjects");

        }, function error(response) {
            console.log("Error when attempting to update cursed object:");
            console.log($scope.cursedObject);
            console.log(response);
            $rootScope.showError("Cannot update cursed object! Reason given by the server: " + response.data.message);
        });
    };
});

/*** House controllers ***/

hauntedHousesControllers.controller('HousesController', function ($scope, $http, $rootScope, $location) {
    $http.get('rest/houses').then(function (response) {
        $scope.houses = response.data;
    });

    $http.get('rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });

    $http.get('rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;
    });

    $scope.filterByMonsterId = function (house) {
        return function (monster) {
            return house.monsterIds.indexOf(monster.id) !== -1;
        };
    };

    $scope.filterByCursedObjectId = function (house) {
        return function (cursedObject) {
            return house.cursedObjectIds.indexOf(cursedObject.id) !== -1;
        };
    };

    $scope.delete = function (house) {
        if (!confirm("Are you certain you want to delete selected house?")) {
            return;
        }

        console.log("Deleting house with id: " + house.id);
        $http.delete("rest/houses/" + house.id).then(
                function success(response) {
                    console.log("Succesfully deleted house " + house.id + " on the server.");
                    $rootScope.showSuccess('Deleted house: "' + house.name + '"');
                    $location.path("/houses");
                },
                function error(response) {
                    console.log("Error when deleting house with id: " + house.id);
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.showError("Cannot delete non-existent house!");
                            break;
                        default:
                            $rootScope.showError("Cannot delete house with assigned monsters or cursed objects!");
                            break;
                    }
                }
        );
    };

    $scope.purge = function (house) {
        if (!confirm("Are you certain you want to purge selected house (delete all monsters and cursed objects residing in it)?")) {
            return;
        }

        console.log("Purging house with id: " + house.id);
        $http.delete("rest/houses/purge/" + house.id).then(
                function success(response) {
                    console.log("Succesfully purged house " + house.id + " on the server.");
                    $rootScope.showSuccess('House with name: "' + house.name + '" was successfully purged.');
                    $location.path("/houses");
                },
                function error(response) {
                    console.log("Error when purging house with id: " + house.id);
                    console.log(response);
                    $rootScope.showError("Cannot purge selected house!");
                }
        );
    };
});

hauntedHousesControllers.controller('HouseCreateController', function ($scope, $http, $rootScope, $location) {

    $http.get('rest/monsters').then(function (response) {
        $scope.monsters = response.data;
    });

    $http.get('rest/cursedObjects').then(function (response) {
        $scope.cursedObjects = response.data;
    });

    $scope.house = {
        'name': '',
        'address': '',
        'monsterIds': [],
        'cursedObjectIds': []
    };

    $scope.create = function (house) {
        house.monsterIds = getIdsFromSelection($scope.monsters);
        house.cursedObjectIds = getIdsFromSelection($scope.cursedObjects);

        $http({
            method: 'POST',
            url: 'rest/houses/create',
            data: house
        }).then(function success(response) {
            var createdHouse = response.data;
            $rootScope.showSuccess('House with name "' + createdHouse.name + '" was created.');
            console.log("house " + createdHouse.name + " created");
            $location.path("/houses");

        }, function error(response) {
            console.log("Error when attempting to create house: " + house);
            console.log(response);
            $rootScope.showError("Cannot create house! Reason given by the server: " + response.data.message);
        });
    };
});

hauntedHousesControllers.controller('HouseUpdateController', function ($scope, $http, $routeParams, $rootScope, $location) {

    $scope.house = {
        'name': '',
        'address': '',
        'monsterIds': [],
        'cursedObjectIds': []
    };

    // Get object that should be updated
    var houseId = $routeParams.houseId;
    $http.get('rest/houses/id/' + houseId).then(function (response) {
        var house = response.data;
        $scope.house = house;
        console.log($scope.house);
    });

    // Update button clicked
    $scope.update = function (house) {

        $http({
            method: 'PUT',
            url: 'rest/houses/' + $scope.house.id,
            data: house
        }).then(function success(response) {
            var updatedHouse = response.data;
            $rootScope.showSuccess('House with name "' + updatedHouse.name + '" was updated.');
            console.log("house " + updatedHouse.name + " updated");
            $location.path("/houses");

        }, function error(response) {
            console.log("Error when attempting to update house:");
            console.log(house);
            console.log(response);
            $rootScope.showError("Cannot update house! Reason given by the server: " + response.data.message);
        });
    };
});
