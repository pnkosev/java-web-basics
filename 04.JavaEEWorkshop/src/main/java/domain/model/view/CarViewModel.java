package domain.model.view;

import domain.entity.Engine;

public class CarViewModel {
    private String brand;
    private String model;
    private String year;
    private Engine engine;
    private String userUsername;

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getUserUsername() { return userUsername; }

    public void setUserUsername(String userUsername) { this.userUsername = userUsername; }
}
