package com.bupt.dc.control.controller;

import com.bupt.dc.dao.jpa.RoleRepository;
import com.bupt.dc.dao.jpa.UserRepository;
import com.bupt.dc.object.auth.Role;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.constant.RoleEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value="/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserAuthController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation(value="获取用户列表", notes="查询用户列表")
    @RequestMapping(value="/list", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUserList() {
        List<User> r = userRepository.findAll();
        return r;
    }

    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public Boolean login(@RequestBody User user) {
        User u = userRepository.findByUsername(user.getUsername());
        if (!Objects.isNull(u) && passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    @ApiOperation(value="注册用户", notes="注册用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public void postUser(@RequestBody User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        Set<Role> roles = roleRepository.findByNameIn(RoleEnum.getRoles());
        user.setRoles(roles);
        user.setCreateDate(new Date(System.currentTimeMillis()));
        userRepository.save(user);
    }

    // TODO: 2019/1/29 用户只能看到自己的详情
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).get();
    }


    // TODO: 2019/1/29 用户只能更新自己的详情
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
        @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @PreAuthorize("hasRole('USER')")
    public void putUser(@PathVariable Long id, @RequestBody User user) {
        User u = userRepository.findById(id).get();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        userRepository.save(u);
    }


    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    /**
     * 测试拦截地址
     * @return
     */
    @RequestMapping(value = "/test")
    public String index() {
        return "test";
    }
}
