package co.edu.javeriana.Proyecto_Web.dto;

public class ShipDTO {
    private long id;
    private String name;
    private int xspeed;
    private int yspeed;
    
    public ShipDTO() {
    }

    public ShipDTO(long id, String name, int xspeed, int yspeed, Long modelId, String modelName, Long ownerId,
            String ownerName) {
        this.id = id;
        this.name = name;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
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
    
}