package upsApp.controller;

import model.Package;
import model.Truck;
import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PackageService;
import service.TruckService;
import service.UserService;
import upsApp.Entity.*;

import java.util.List;

@RestController
public class UserController {
    //    @GetMapping("healthCheck")
//    public ResponseEntity<String> getWelcome() {
//        String orderInfo = String.format("hello");
//        System.out.println("111");
//        return ResponseEntity.ok(orderInfo);
//    }
    @GetMapping("query/1/{packageId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<PackageInfoList> getPackageById(@PathVariable String packageId) {
        System.out.println(packageId);
        // 在实际应用中，您需要查询数据库或其他数据源获取订单信息
//        String orderInfo = String.format("hello");
        service.PackageService packageService = new PackageService();
        service.UserService userService = new UserService();
        service.TruckService  truckService = new TruckService();
        Package myPackage = packageService.queryPackageById(Long.parseLong(packageId));
        User user = userService.getUserByUserId(myPackage.getUserId());
        Truck truck = truckService.findTruckById(myPackage.getTruckId());
        PackageInfo packageInfo = new PackageInfo(
                myPackage.getPackageId(),
                myPackage.getDescription(),
                myPackage.getItemNum(),
                myPackage.getDestX(),
                myPackage.getDestY(),
                user.getUserName(),
                myPackage.getStartX(),
                myPackage.getStartY(),
                truck.getCurrX(),
                truck.getCurrY(),
                truck.getStatus()
        );
        System.out.println(myPackage);
        PackageInfoList pkgInfoList = new PackageInfoList();
        pkgInfoList.add(packageInfo);
        return ResponseEntity.ok(pkgInfoList);
    }

    @GetMapping("query/2/{username}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<PackageInfoList> getPackageByUsername(@PathVariable String username) {
        System.out.println(username);
        // 在实际应用中，您需要查询数据库或其他数据源获取订单信息
//        String orderInfo = String.format("hello");
        service.PackageService packageService = new PackageService();
        service.UserService userService = new UserService();
        service.TruckService  truckService = new TruckService();
        List<Package> myPackages = packageService.queryPackageByUsername(username);
        PackageInfoList pkgInfoList = new PackageInfoList();
        for (Package pkg : myPackages) {
            User user = userService.getUserByUserId(pkg.getUserId());
            Truck truck = truckService.findTruckById(pkg.getTruckId());
            PackageInfo packageInfo = new PackageInfo(
                    pkg.getPackageId(),
                    pkg.getDescription(),
                    pkg.getItemNum(),
                    pkg.getDestX(),
                    pkg.getDestY(),
                    user.getUserName(),
                    pkg.getStartX(),
                    pkg.getStartY(),
                    truck.getCurrX(),
                    truck.getCurrY(),
                    truck.getStatus());
            pkgInfoList.add(packageInfo);
        }
        return ResponseEntity.ok(pkgInfoList);
    }
    @PostMapping("/redirect")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> redirectOrderBy(@RequestBody RedirectRequestBody redirectRequestBody) {
        System.out.println("redirect Info");
        System.out.println(redirectRequestBody.getPackageId());
        System.out.println(redirectRequestBody.getNewX());
        System.out.println(redirectRequestBody.getNewY());
        // 在实际应用中，您需要查询数据库或其他数据源获取订单信息
//        String orderInfo = String.format("hello");
        service.PackageService packageService = new PackageService();
        String msg = packageService.redirectPackage(
                redirectRequestBody.getPackageId(),
                redirectRequestBody.getNewX(),
                redirectRequestBody.getNewY()
        );
        return ResponseEntity.ok(msg);
    }
    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Boolean> login(@RequestBody LoginRequestBody loginRequestBody) {
        System.out.println("login Info");
        System.out.println(loginRequestBody.getUsername());
        System.out.println(loginRequestBody.getPassword());
//        String orderInfo = String.format("hello");
        service.UserService userService = new UserService();
        boolean authRes = userService.authentication(
                loginRequestBody.getUsername(),
                loginRequestBody.getPassword()
        );
        return ResponseEntity.ok(authRes);
    }
}