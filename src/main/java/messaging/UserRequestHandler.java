package messaging;

import protocol.AmazonUps;
import protocol.WorldUps;
import protocol.UpsUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Package;
import service.UserService;
public class UserRequestHandler implements Runnable {
    UserClient userClient;
    UserService userService;
    public UserRequestHandler(UserClient userClient){
        this.userClient = userClient;
        this.userService = new UserService();
    }
    @Override
    public void run() {
        //1.http recv
        //2. body
        //3.
//        UpsUser.UUserRequest userRequest = userClient.receiveUUserRequest();
        try {
            System.out.println(userClient.codedInputStream.readByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //
//        if(userRequest.hasQueryCommand()){
//            UpsUser.UQuery query = userRequest.getQueryCommand();
//            if(query.hasPackageId()){
//                Package pkg = userService.queryPackageById(query.getPackageId());
//                UpsUser.UPackage uPackage = common.BuilderUtil.buildUPackage(
//                        pkg.getPackageId(),
//                        pkg.getDescription(),
//                        pkg.getItemNum(),
//                        pkg.getDestX(),
//                        pkg.getDestY()
//                );
//                List<UpsUser.UPackage> packageList = new ArrayList<>();
//                packageList.add(uPackage);
//                UpsUser.UQueryResult queryResult = common.BuilderUtil.buildUQueryResult(packageList,1);
//                UpsUser.UUserResponse userResponse = common.BuilderUtil.buildUUserResponse(queryResult);
//                userClient.sendMessage(userResponse);
//            } else if(query.hasUserId()){
//                List<Package> packageList= userService.queryPackageByUserId(query.getUserId());
//                List<UpsUser.UPackage> uPackageList = new ArrayList<>();
//                for(Package pkg: packageList){
//                    UpsUser.UPackage uPackage = common.BuilderUtil.buildUPackage(
//                            pkg.getPackageId(),
//                            pkg.getDescription(),
//                            pkg.getItemNum(),
//                            pkg.getDestX(),
//                            pkg.getDestY()
//                    );
//                    uPackageList.add(uPackage);
//                }
//                UpsUser.UQueryResult queryResult = common.BuilderUtil.buildUQueryResult(uPackageList,1);
//                UpsUser.UUserResponse userResponse = common.BuilderUtil.buildUUserResponse(queryResult);
//                userClient.sendMessage(userResponse);
//            }
//        }
//
//        if(userRequest.hasRedirectCommand()){
//            UpsUser.URedirect redirectCommand = userRequest.getRedirectCommand();
//            String msg = userService.redirectPackage(
//                redirectCommand.getPackageId(),
//                redirectCommand.getX(),
//                redirectCommand.getY()
//            );
//            UpsUser.URedirectResult redirectResult = common.BuilderUtil.buildURedirectResult(msg);
//            UpsUser.UUserResponse userResponse = common.BuilderUtil.buildUUserResponse(redirectResult);
//            userClient.sendMessage(userResponse);
//        }
    }
}
