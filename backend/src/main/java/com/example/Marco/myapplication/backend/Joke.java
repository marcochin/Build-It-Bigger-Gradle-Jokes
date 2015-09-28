package com.example.Marco.myapplication.backend;

/**
 * This is a joke pojo class to be returned and converted into JSON by an API call
 */
public class Joke {
    private String joke;

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
