package upsApp.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PackageInfoList {

    @JsonProperty("PackageInfoList")
    private List<PackageInfo> packageInfoList;

    public PackageInfoList() {
        // 默认构造器
        packageInfoList = new ArrayList<>();
    }

    public PackageInfoList(List<PackageInfo> packageInfoList) {
        this.packageInfoList = packageInfoList;
    }

    public List<PackageInfo> getPackageInfoList() {
        return packageInfoList;
    }

    public void setPackageInfoList(List<PackageInfo> packageInfoList) {
        this.packageInfoList = packageInfoList;
    }
    public void add(PackageInfo packageInfo){
        this.packageInfoList.add(packageInfo);
    }
}
