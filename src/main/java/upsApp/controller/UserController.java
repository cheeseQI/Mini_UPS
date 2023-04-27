package upsApp.controller;

import model.Package;
import org.apache.juli.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("query/2/{username}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<PackageInfoList> getPackageByUsername(@PathVariable String username) {
        System.out.println(username);
        // 在实际应用中，您需要查询数据库或其他数据源获取订单信息
//        String orderInfo = String.format("hello");
        service.UserService userService = new UserService();
        List<Package> myPackages = userService.queryPackageByUsername(username);
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
    @PostMapping("/redirect")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> redirectOrderBy(@RequestBody RedirectRequestBody redirectRequestBody) {
        System.out.println("redirect Info");
        System.out.println(redirectRequestBody.getPackageId());
        System.out.println(redirectRequestBody.getNewX());
        System.out.println(redirectRequestBody.getNewY());
        // 在实际应用中，您需要查询数据库或其他数据源获取订单信息
//        String orderInfo = String.format("hello");
        service.UserService userService = new UserService();
        String msg = userService.redirectPackage(
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