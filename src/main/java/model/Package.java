package model;

public class Package {
    private Long packageId;
    private String description;
    private Integer itemNum;
    private Integer truckId;
    private Integer userId;
    private int destX;
    private int destY;
    private int whid;
    private int startX;
    private int startY;

    public Package(){}
    public Package(Long packageId, String description, Integer itemCount, Integer truckId, Integer userId, int destX, int destY, int whid, int startX, int startY) {
        this.packageId = packageId;
        this.description = description;
        this.itemNum = itemCount;
        this.truckId = truckId;
        this.userId = userId;
        this.destX = destX;
        this.destY = destY;
        this.whid = whid;
        this.startX = startX;
        this.startY = startY;
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

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getDestX() {
        return destX;
    }

    public void setDestX(int destX) {
        this.destX = destX;
    }

    public int getDestY() {
        return destY;
    }

    public void setDestY(int destY) {
        this.destY = destY;
    }

    public int getWhid() {
        return whid;
    }

    public void setWhid(int whid) {
        this.whid = whid;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    @Override
    public String toString() {
        return "Package {" +
                "packageId=" + packageId +
                ", description='" + description + '\'' +
                ", itemCount=" + itemNum +
                ", truckId=" + truckId +
                ", userId=" + userId +
                ", destX=" + destX +
                ", destY=" + destY +
                ", whid=" + whid +
                ", startX=" + startX +
                ", startY=" + startY +
                '}';
    }
}