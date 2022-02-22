package org.redlich.movies

class MovieRepositoryTest extends spock.lang.Specification {

    def "should find existing movies based on title and year and skipping the id"(){
        given:
        MovieRepository repository = new MovieRepository();
        expect:
        _exists == repository.exists(new Movie(id: _id,title: _title, year:_year))

        where:
        _exists |_id | _title          | _year
        true    |null| "Reservoir Dogs"| 1992
        true    |  1 | "Reservoir Dogs"| 1992
        true    |  2 | "Reservoir Dogs"| 1992
        false   |  1 | "Reservoir Dog" | 1992
        false   |  1 | "Reservoir Dogs"| 1993
    }
    def "when adding a movie the id should be set to the size of the movies"(){
        given:
        MovieRepository repository = new MovieRepository();
        when:
        Movie saved = repository.save(new Movie(title:"the last dupe", year: 2023))
        then:
        saved != null
        saved.id == 10
    }
}
