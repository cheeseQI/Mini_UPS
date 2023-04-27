package upsApp.controller;

import common.MyBatisUtil;
import mapper.PackageMapper;
import model.Package;
import org.apache.ibatis.session.SqlSession;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import upsApp.Entity.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    //    @GetMapping("healthCheck")
//    public ResponseEntity<String> getWelcome() {
//        String orderInfo = String.format("hello");
//        System.out.println("111");
//        return ResponseEntity.ok(orderInfo);
//    }
    @GetMapping("query/1/{packageId}")
    public ResponseEntity<PackageInfoList> getPackageById(@PathVariable String packageId) {
        System.out.println(packageId);
        // 在实际应用中，您需要查询数据库或其他数据源获取订单信息
//        String orderInfo = String.format("hello");
        service.UserService userService = new UserService();
        Package myPackage = userService.queryPackageById(Long.parseLong(packageId));
        PackageInfo packageInfo = new PackageInfo(
                myPackage.getPackageId(),
                myPackage.getDescription(),
                myPackage.getItemNum(),
                myPackage.getDestX(),
                myPackage.getDestY(),
                myPackage.getUserId()
        );
        PackageInfoList pkgInfoList = new PackageInfoList();
        pkgInfoList.add(packageInfo);

        return ResponseEntity.ok(pkgInfoList);
    }

    @GetMapping("query/2/{userId}")
    public ResponseEntity<PackageInfoList> getPackageByUserId(@PathVariable String userId) {
        System.out.println(userId);
        // 在实际应用中，您需要查询数据库或其他数据源获取订单信息
//        String orderInfo = String.format("hello");
        service.UserService userService = new UserService();
        List<Package> myPackages = userService.queryPackageByUserId(Integer.parseInt(userId));
        PackageInfoList pkgInfoList = new PackageInfoList();
        for (Package pkg : myPackages) {
            PackageInfo packageInfo = new PackageInfo(
                    pkg.getPackageId(),
                    pkg.getDescription(),
                    pkg.getItemNum(),
                    pkg.getDestX(),
                    pkg.getDestY(),
                    pkg.getUserId()
            );
            pkgInfoList.add(packageInfo);
        }
        return ResponseEntity.ok(pkgInfoList);
    }
}