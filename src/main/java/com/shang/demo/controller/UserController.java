package com.shang.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shang.demo.annotation.AccessLimit;
import com.shang.demo.pojo.User;
import com.shang.demo.mapper.UserMapper;
import com.shang.demo.pojo.result.JsonResult;
import com.shang.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;

/**
 * <p></p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-04 14:25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;


    /**
     * 测试接口防刷自定义注解
     */
    @AccessLimit(seconds = 10,maxCount = 5,needLogin =true )
    @RequestMapping("/testLimit")
    public String fangshua(){
        return "success";
    }

    /**
     * 生成随机字符串
     */
    private static String creatRandomStr(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            stringBuilder.append(str.charAt(number));
        }
        return stringBuilder.toString();
    }

    /**
     * 使用pageHelper 分页查询
     * @param page int
     * @param pageSize int
     * @return PageInfo
     */
    @RequestMapping(value = "/findPage",method = RequestMethod.GET)
    @ApiOperation(value = "使用pageHelper 分页查询")
    public JsonResult findPage(Integer page, Integer pageSize) {
        try {
            if (page!=null&&pageSize!=null){
                com.github.pagehelper.Page<User> page1 = userService.findPage(page, pageSize);
                if (page1!=null&&!page1.isEmpty()){
                    long total = page1.getTotal();
                    return new JsonResult<>(201,"查询成功",page1,total);
                }
                return new JsonResult<>(210,"查询结果为空",null,0);
            }else {
                return new JsonResult<>(101,"page,pageSize不能为空",null,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(101,"查询出错",null,0);
        }
    }

    /**
     * 查询集合
     *
     * @return JsonResult
     */
    @RequestMapping(value = "/selectList",method = RequestMethod.GET)
    public JsonResult selectList() {
        try {
            List<User> userList = userService.list();
//            List<User> userList1 = userMapper.selectList(Wrappers.emptyWrapper());
            if (userList != null && userList.size() > 0) {
                return new JsonResult<>(201, "查询成功", userList);
            } else {
                return new JsonResult<>(210, "查询结果为空", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(101, "查询出错", null);
        }
    }

    /**
     * 根据Id查询
     *
     * @return JsonResult
     */
    @ApiOperation(value = "Rest风格根据ID查询")
    @RequestMapping(value = "/selectById/{userId}",method = RequestMethod.GET)
    public JsonResult selectById(@PathVariable Long userId) {
        try {
            if (userId != null) {
                User user = userService.getById(userId);
//                User user1 = userMapper.selectById(userId);
                if (user != null) {
                    return new JsonResult<>(201, "查询成功", user);
                } else {
                    return new JsonResult<>(210, "查询结果为空", null);
                }
            } else {
                return new JsonResult<>(101, "userId不能为空", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(101, "查询出错", null);
        }
    }

    /**
     * <p>
     * 查询（根据 columnMap 条件）
     * </p>
     *
     * @param name String
     */
    @RequestMapping(value = "/selectListByMap",method = RequestMethod.GET)
    public JsonResult selectListByMap(String  name) {
        try {
            if (name != null&&!"".equals(name)) {
                Map<String,Object> columnMap = new HashMap<>();
                columnMap.put("name",name);
                Collection<User> userCollection = userService.listByMap(columnMap);
//                User user1 = userMapper.selectById(userId);
                if (userCollection != null&&userCollection.size()>0) {
                    return new JsonResult<>(201, "查询成功", userCollection);
                } else {
                    return new JsonResult<>(210, "查询结果为空", null);
                }
            } else {
                return new JsonResult<>(101, "name不能为空", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(101, "查询出错", null);
        }
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/listMaps",method = RequestMethod.GET)
    public JsonResult listMaps() {
        try {
            List<Map<String, Object>> idAndNames = userMapper.selectMaps(new QueryWrapper<User>().select("id", "name"));
            if (idAndNames!=null&&idAndNames.size()>0){
                return new JsonResult<>(201, "查询成功", idAndNames);
            }else {
                return new JsonResult<>(210, "修改出错", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(101, "修改出错", null);
        }
    }

    /**
     * 分页查询
     *
     * @param page     int
     * @param pageSize int
     * @return JsonResult
     */
    @RequestMapping(value = "/selectPage",method = RequestMethod.GET)
    public JsonResult selectPage(Integer page, Integer pageSize, String name) {
        try {
            if (page != null && pageSize != null) {
                IPage<User> page1 = userService.page(new Page<>(page, pageSize), new QueryWrapper<User>().eq("name", name));
//                IPage<User> page2 = userMapper.selectPage(
//                        new Page<>(page, pageSize),
//                        new QueryWrapper<User>().eq("name", name)
//                );
                List<User> userList = page1.getRecords();
                if (userList != null && userList.size() > 0) {
                    long total = page1.getTotal();
                    return new JsonResult<>(201, "查询成功", userList, total);
                } else {
                    return new JsonResult<>(210, "查询结果为空", null, 0);
                }
            } else {
                return new JsonResult<>(101, "page和pageSize不能为空", null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(101, "查询出错", null, 0);
        }
    }

    /**
     * 添加
     *
     * @param user pojo
     * @return JsonResult
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(@RequestBody User user) {
        try {
            if (user != null) {
                User user1 = new User();
                boolean save = userService.save(user);
//                int n = userMapper.insert(user);
                return new JsonResult<>(202, "添加成功", save);
            } else {
                return new JsonResult<>(102, "user不能为空", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(102, "添加出错", false);
        }
    }

    /**
     * 添加
     *
     * @param userList pojo
     * @return JsonResult
     */
    @RequestMapping(value = "/saveBatch",method = RequestMethod.POST)
    public JsonResult saveBatch(@RequestBody List<User> userList) {
        try {
            if (userList != null&&userList.size()>0) {
                boolean save = userService.saveBatch(userList);
//                int n = userMapper.insert(user);
                return new JsonResult<>(202, "添加成功", save);
            } else {
                return new JsonResult<>(102, "user不能为空", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(102, "添加出错", false);
        }
    }

    /**
     * 根据Id删除
     *
     * @param userId int
     * @return JsonResult
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
    public JsonResult deleteById(Long userId) {
        try {
            if (userId != null) {
                User user = userService.getById(userId);
//                User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
//                User user2 = userMapper.selectById(userId);
                if (user != null) {
                   userService.removeById(userId);
//                    int n = userMapper.deleteById(userId);
                    return new JsonResult<>(203, "删除成功", true);
                } else {
                    return new JsonResult<>(103, "删除的数据不存在", false);
                }
            } else {
                return new JsonResult<>(103, "userId不能为空", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(103, "查询出错", false);
        }
    }

    /**
     * 根据条件删除-批量删除
     *
     * @param name String
     * @return JsonResult
     */
    @RequestMapping(value = "/deleteByMap",method = RequestMethod.DELETE)
    public JsonResult deleteByMap(String name) {
        try {
            if (name != null && !"".equals(name)) {
                User user = userService.getOne(new QueryWrapper<User>().eq("name", name));
                if (user != null) {
                    Map<String, Object> columnMap = new HashMap<>();
                    columnMap.put("name", name);
                    userService.removeByMap(columnMap);
                    //eq 可以连接使用
//                    boolean delete1 = userService.remove(new QueryWrapper<User>().eq("mame",name).eq("id",userId));
                    return new JsonResult<>(203, "删除成功", true);
                } else {
                    return new JsonResult<>(103, "删除的数据不存在", false);
                }
            } else {
                return new JsonResult<>(103, "userId不能为空", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(103, "查询出错", false);
        }
    }

    /**
     * 根据Id集合-批量删除
     *
     * @param userIdList list
     * @return JsonResult
     */
    @RequestMapping(value = "/deleteByIdList",method = RequestMethod.POST)
    public JsonResult deleteByIdList(@RequestBody List<Integer> userIdList) {
        try {
            if (userIdList != null && userIdList.size() > 0) {
                userService.removeByIds(userIdList);
//                int n = userMapper.delete(new QueryWrapper<User>().in("id", userIdList));
//                int n2 = userMapper.deleteBatchIds(userIdList);
                return new JsonResult<>(203, "删除成功", true);
            } else {
                return new JsonResult<>(103, "userId不能为空", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(103, "查询出错", false);
        }
    }


    /**
     * 编辑修改-根据Id
     */
    @RequestMapping(value = "/updateById",method = RequestMethod.PUT)
    public JsonResult updateById(@RequestBody User user) {
        try {
            if (user != null) {
                User user1 = userService.getById(user.getId());
                if (user1 != null) {
                    userService.updateById(user);
                    return new JsonResult<>(204, "修改成功", true);
                } else {
                    return new JsonResult<>(104, "修改的数据不存在", false);
                }
            } else {
                return new JsonResult<>(104, "user不能为空", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(104, "修改出错", false);
        }
    }

    /**
     * 按照条件-批量编辑修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public JsonResult update(@RequestBody User user) {
        try {
            if (user != null) {
                    userService.update(user,new UpdateWrapper<User>().eq("name","龙"));

                    return new JsonResult<>(204, "修改成功", true);
            } else {
                return new JsonResult<>(104, "user不能为空", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<>(104, "修改出错", false);
        }
    }




}
