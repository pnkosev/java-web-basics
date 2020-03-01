package service;

import repository.Test;

import javax.inject.Inject;

public class TestServiceImpl {
    private final Test test;

    public TestServiceImpl(Test test) {
        this.test = test;
    }
}
