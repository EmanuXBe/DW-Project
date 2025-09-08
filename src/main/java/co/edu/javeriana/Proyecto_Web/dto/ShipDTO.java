package co.edu.javeriana.Proyecto_Web.dto;

public class ShipDTO {
    private long id;
    private String name;
    private int xspeed;
    private int yspeed;
    private String model;
    private String color;
    private String owner;

    public ShipDTO() {
    }

    public ShipDTO(long id, String name, int xspeed, int yspeed, String model, String owner) {
        this.id = id;
        this.name = name;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.model = model;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXspeed() {
        return xspeed;
    }

    public void setXspeed(int xspeed) {
        this.xspeed = xspeed;
    }

    public int getYspeed() {
        return yspeed;
    }

    public void setYspeed(int yspeed) {
        this.yspeed = yspeed;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
