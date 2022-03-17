Feature: sample karate test script for crudding the movies
  Note that your server must be up and running when using this feature file

  Background:
    * url 'http://localhost:8280'

  Scenario: getting them all
    Given path '/movies'
    When method get
    Then status 200
    And assert response.length == 9
    And match response[0].id == 1
    And match response[0].title == "Reservoir Dogs"

  Scenario: storing a new movie
    Given path "/movies"
    Given request {title:"unknown yet", year:2022}
    When method post
    Then status 201
    * def location = responseHeaders['Location'][0]
    * def movieId = location.substring(location.lastIndexOf('/') + 1)
    * def id = parseInt(movieId)
    Given path "/movies/"+ movieId
    When method get
    Then status 200
    * print "response:" + response
    And match response.title == "unknown yet"
    And match response.year == 2022
    And match response.id == id
# and now there should be one more
    Given path '/movies'
    When method get
    Then status 200
    And assert response.length == 10

  Scenario: storing a movie with bad json content
    Given path "/movies"
    Given request {title:"unknown yet"}
    When method post
    Then status 201