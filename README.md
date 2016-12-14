# hauntedhouses

[![Build Status](https://travis-ci.org/piskula/hauntedhouses.svg?branch=master)](https://travis-ci.org/piskula/hauntedhouses)

This application acts as a database of haunted houses and monsters that haunt these hauses.

The house is defined by its name, address (not an entity), the date it became haunted and its concise history.
Each monster has its abilities, which can be shared with other monsters and haunts a single house.
A monster has an extremely scary name, single interval during which it haunts and a description why and
how it haunts. An ability has its name and description. In each house there can also by several
cursed objects which attract monsters. Cursed object has its name, description and factor that defines
its attractiveness to monsters. These objects attract monsters in general and are not tied to any specific
monsters.

Students working on this project:
- [Ondrej Oravčok](https://github.com/piskula)
- [Filip Petrovič](https://github.com/Fillo7)
- [Kristýna Loukotová](https://github.com/Tilwaen)
- [Marek Jančo](https://github.com/marcus991)

### Running application
This application has two parts:
- REST API running on /pa165/rest
- AngularJS front-end running on /pa165

1. run `mvn clean install` command in home directory
2. run `mvn` in front-end-and-rest-layer directory
