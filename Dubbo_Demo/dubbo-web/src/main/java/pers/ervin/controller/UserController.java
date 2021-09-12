package pers.ervin.controller;

import pers.ervin.pojo.User;
import pers.ervin.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    //注入Service
    //@Autowired//本地注入

    /*
        1. 从zookeeper注册中心获取userService的访问url
        2. 进行远程调用RPC
        3. 将结果封装为一个代理对象。给变量赋值

     */

    @Reference(cluster = "failover",version = "v2.0",loadbalance = "random",mock="fail:return null")//远程注入
    // 负载均衡类型: Random,RoundRobin,LeastActive,ConsistentHash
    // 集群容错类型: Failover,failfast,failsafe,fallback,forking,broadcast
    private UserService userService;


    @RequestMapping("/sayHello")
    public String sayHello(){
        return userService.sayHello();
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */

    @RequestMapping("/find")
    public User find(int id){

        return userService.findUserById(id);
    }

}
