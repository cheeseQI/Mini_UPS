package model;

public class Package {
    private Integer packageId;
    private String status;
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
    public Package(Integer packageId, String status, String description, Integer itemCount, Integer truckId, Integer userId, int destX, int destY, int whid, int startX, int startY) {
        this.packageId = packageId;
        this.status = status;
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

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", status='" + status + '\'' +
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