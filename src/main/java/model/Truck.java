package model;

public class Truck {
    private Integer truckId;
    private String status;
    private int currX;
    private int currY;
    private Integer packageNum;

    public Truck(){}
    public Truck(Integer truckId, String status, int currX, int currY, Integer packageCount) {
        this.truckId = truckId;
        this.status = status;
        this.currX = currX;
        this.currY = currY;
        this.packageNum = packageCount;
    }

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrX() {
        return currX;
    }

    public void setCurrX(int currX) {
        this.currX = currX;
    }

    public int getCurrY() {
        return currY;
    }

    public void setCurrY(int currY) {
        this.currY = currY;
    }

    public Integer getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }

    @Override
    public String toString() {
        return "Truck {" +
                "truckId=" + truckId +
                ", status='" + status + '\'' +
                ", currX=" + currX +
                ", currY=" + currY +
                ", packageNum=" + packageNum +
                '}';
    }
}

