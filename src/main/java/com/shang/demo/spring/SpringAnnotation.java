package com.shang.demo.spring;



/**
 * 此类记录spring框架中常用的核心注解
 */
public class SpringAnnotation {

    /**
     * @Controller 用于标注控制层组件,可以把Request请求header部分的值绑定到方法的参数上
     * @RestController 相当于@Controller和@ResponseBody 的组合效果
     * @Component 泛指组件,当组件不好归类的时候,就可以使用这个注解来标注
     * @Resposity 用于注解dao层,在DaoImpl类上面使用
     * @Service 用于标注业务层组件,一般也是在ServiceImpl类上面标注
     */


    /**
     * @ResponseBody 主要是把Controller的方法的httpMessageConverter转换成指定的格式后,写入到Response对象的Body中
     * @ResquestMapping 用来处理请求地址的映射关系,如果用于类上,表示类中方法的父路径
     * @Autowired 它可以对类成员变量,方法及构造函数进行标注,完成自动装配工作,消除get ,set方法
     * @PathVariable 用于将请求url中的模板变量映射到方法的参数上:
     *      //@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
     *        public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){}
     * @RequestParam 主要用于在SpringMVC后台控制层获取参数,类似于:request.getParameter("name)
     * @RequestHeader 可以把Request请求header部分的值绑定到方法的参数上面
     */

    /**
     * @ModelAttribute  在执行Controller所有的方法之前,先执行@ModelAttribute方法,可以用于注解和方法参数中
     * @SessionAttribute 将值放到Session域中,写在Class上面
     * @Valid 实体数据校验,可以结合hibernate validator一起使用
     * @CookieValue 用于获取cookie中的参数值
     */

    /**
     * Spring与第三方框架集成
     *  权限:shiro 安全框架->认证,授权,加密,会话管理,与Web集成,缓存
     *  缓存:Ehcache:进程内存缓存框架,是Hibernate中默认的CacheProvider
     *  持久层框架: Hibernate: 将POJO与数据库表建立映射关系,是一个全自动的orm框架
     *            Mybatis: 支持SQL查询,存储过程和高级映射的持久层框架
     *  定时任务: Quartz :任务调度调度框架,cron表达式
     *          SpringTask:相当于一个轻量的Quartz框架,只需要导入spring相关的包,支持注解和配置文件两种形式
     *  校验框架: Hibernate Validator:通常用来验证bean的字段,基于注解,方便快捷
     *          OVal: 可扩展的JAVA对象数据验证框架,验证的规则可以通过配置文件,Annotation,POJO进行设定
     */

}
