package upsApp.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackageInfo {

    @JsonProperty("packageId")
    private Long packageId;

    @JsonProperty("description")
    private String description;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("destX")
    private Integer destX;
    @JsonProperty("destY")
    private Integer destY;
    @JsonProperty("username")
    private String username;

    @JsonProperty("startX")
    private Integer startX;
    @JsonProperty("startY")
    private Integer startY;
    @JsonProperty("currX")
    private Integer currX;
    @JsonProperty("currY")
    private Integer currY;
    @JsonProperty("status")
    private String status;

    public PackageInfo(Long packageId, String description, Integer count, Integer destX, Integer destY, String username, Integer startX, Integer startY, Integer currX, Integer currY, String status) {
        this.packageId = packageId;
        this.description = description;
        this.count = count;
        this.destX = destX;
        this.destY = destY;
        this.username = username;
        this.startX = startX;
        this.startY = startY;
        this.currX = currX;
        this.currY = currY;
        this.status = status;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDestX() {
        return destX;
    }

    public void setDestX(Integer destX) {
        this.destX = destX;
    }

    public Integer getDestY() {
        return destY;
    }

    public void setDestY(Integer destY) {
        this.destY = destY;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = username;
    }

    public Integer getStartX() {
        return startX;
    }

    public void setStartX(Integer destX) {
        this.startX = startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public void setStartY(Integer destY) {
        this.startY = startY;
    }


    public Integer getCurrX() {
        return currX;
    }

    public void setCurrX(Integer currX) {
        this.currX = currX;
    }

    public Integer getCurrY() {
        return currY;
    }

    public void setCurrY(Integer currY) {
        this.currY = currY;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
