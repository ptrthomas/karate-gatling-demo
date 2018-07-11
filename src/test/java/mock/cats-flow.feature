Feature: cats api flow

  Background:
    * url karate.properties['mock.cats.url']

  Scenario: create, get, update, and delete a cat
    Given request { name: 'Billie' }
    When method post
    Then status 200
    And match response == { id: '#uuid', name: 'Billie' }
    * def id = response.id

    Given path id
    When method get
    Then status 200
    And match response == { id: '#(id)', name: 'Billie' }

    Given path id
    When request { id: '#(id)', name: 'Bob' }
    When method put
    Then status 200
    And match response == { id: '#(id)', name: 'Bob' }

    When method get
    Then status 200

    Given path id
    When method delete
    Then status 200
    And match response == ''

    Given path id
    When method get
    Then status 404