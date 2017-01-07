# hauntedhouses Front-end

Front-end is available at <http://localhost:8080/pa165/>.

You can use following credentials to login (name / password):
* Regular user access level: ming / lee
* Admin access level: admin / opieop

Access privileges:
* Anyone can view entities.
* Logged in users can create and update entities.
* Admin can delete entities and view users page. 

# hauntedhouses REST API

The REST API is available at <http://localhost:8080/pa165/rest>.

## Cursed object entity operations

### GET

Get all cursed objects:

```
curl -i -X GET http://localhost:8080/pa165/rest/cursedObjects
```


Get cursed object with specified id:

```
curl -i -X GET http://localhost:8080/pa165/rest/cursedObjects/1
```

### DELETE

Delete cursed object with specified id:

```
curl -i -X DELETE http://localhost:8080/pa165/rest/cursedObjects/1
```

### CREATE

Create new cursed object:

```
curl -X POST -i -H "Content-Type: application/json" --data '{"name":"some_name","description":"some_description","monsterAttractionFactor":"LOW","houseId":"1"}' http://localhost:8080/pa165/rest/cursedObjects/create
```

### UPDATE

Update existing cursed object:

```
curl -X PUT -i -H "Content-Type: application/json" --data '{"name":"some_name","description":"some_description","monsterAttractionFactor":"LOW","houseId":"1"}' http://localhost:8080/pa165/rest/cursedObjects/1
```

### INCREASE ATTRACTION FACTOR

Increase monster attraction factor for all cursed objects below threshold:

```
curl -X POST -i -H "Content-Type: application/json" --data '{"threshold":"MEDIUM"}' http://localhost:8080/pa165/rest/cursedObjects/increase
```

## Note

On Windows operating systems, default syntax for submitting JSON data with curl doesn't work, use following syntax instead:
"{\"name\":\"some_name\",\"description\":\"some_description\",\"monsterAttractionFactor\":\"LOW\",\"houseId\":\"1\"}"
(data enclosed in quotation marks instead of apostrophes, escaped quotation marks inside curly braces)
