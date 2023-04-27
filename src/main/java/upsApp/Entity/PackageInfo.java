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
    public PackageInfo(Long packageId, String description, Integer count, Integer destX, Integer destY, String username) {
        this.packageId = packageId;
        this.description = description;
        this.count = count;
        this.destX = destX;
        this.destY = destY;
        this.username = username;
    }
}
