package upsApp.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedirectRequestBody {

    @JsonProperty("packageId")
    private Long packageId;
    @JsonProperty("newX")
    private Integer newX;
    @JsonProperty("newY")
    private Integer newY;

    // Constructors
    public RedirectRequestBody() {
    }

    public RedirectRequestBody(Long packageId, Integer newX, Integer newY) {
        this.packageId = packageId;
        this.newX = newX;
        this.newY = newY;
    }

    // Getters
    public Long getPackageId() {
        return packageId;
    }

    public Integer getNewX() {
        return newX;
    }

    public Integer getNewY() {
        return newY;
    }

    // Setters
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public void setNewX(Integer newX) {
        this.newX = newX;
    }

    public void setNewY(Integer newY) {
        this.newY = newY;
    }
}
