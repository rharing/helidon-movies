package org.redlich.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Movie implements Serializable {
    private Integer id;
    @NotEmpty
    private String title;
    @NotEmpty
    private int year;

    public static Movie of( String title, int year) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYear(year);
        return movie;
    }
    public static Movie of(int id, String title, int year) {
        Movie movie = of(title, year);
        movie.setId(id);
        return movie;
        }

    public Integer getId() {
        return id;
        }

    public void setId(Integer id) {
        this.id = id;
        }

    public String getTitle() {
        return title;
        }

    public void setTitle(String title) {
        this.title = title;
        }

    public int getYear() {
        return year;
        }

    public void setYear(int year) {
        this.year = year;
        }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
        }
    }
