package com.any.zhangjunjie.anno.application;



import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangjunjie
 * 动态代理实现类
 */
public class Applicationimpl implements ApplicationContext {


    private Map<Class, Object> beanFactory = new HashMap<>();

    public Object getbean(Class clazz) {

        return beanFactory.get(clazz);
    }

    private static String re = "/D:/javaproject/IOC/target/classes/";

    public Applicationimpl(String basePackage) {

        try {
            //将.替换成\
            String path = basePackage.replaceAll("\\.", "\\\\");
            //获取路径4
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
            //遍历枚举类得到路径
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String filepath = URLDecoder.decode(url.getFile(), "utf-8");
                loadbean(new File(filepath));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        autowired();

    }

    private void autowired() {
        //获取所有的bean
        Set<Map.Entry<Class, Object>> entries = beanFactory.entrySet();
        //遍历bean
        for (Map.Entry<Class, Object> entry : entries) {
            Object o = entry.getValue();
            Class<?> aClass = o.getClass();
            //获取bean的所有属性
            Field[] fields = aClass.getDeclaredFields();
            //遍历属性
            for (Field field : fields) {
                //判断属性是否有autowired注解
                di annotation = field.getAnnotation(di.class);
                //判断注解是否为空
                if (annotation != null) {
                    field.setAccessible(true);
                    //进行注入
                    try {
                        field.set(o, beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void loadbean(File file) throws Exception {
        //判断是否是文件夹
        if (file.isDirectory()) {
            //得到文件夹下的所有文件
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            //不为空，遍历文件
            for (File f : files) {
                //判断是否是文件夹
                if (f.isDirectory()) {
                    loadbean(f);
                }
                String pathWithClass = f.getAbsolutePath().substring(re.length() - 1);
                //判断是不是.class文件
                if (pathWithClass.endsWith(".class")) {
                    //将\替换成.
                    String path = pathWithClass.replaceAll("\\\\", ".");
                    //去掉.class
                    path = path.replace(".class", "");
                    //获取注解
                    Class<?> clazz = Class.forName(path);
                    //判断是不是接口
                    if (!clazz.isInterface()) {
                        //获取注解名称
                        bean annotation = clazz.getAnnotation(bean.class);
                        //判断注解是否为空
                        if (annotation != null) {
                            //实例化
                            Object o = clazz.getConstructor().newInstance();
                            //放入map
                            //判断是否是接口
                            if (clazz.getInterfaces().length > 0) {
                                beanFactory.put(clazz.getInterfaces()[0], o);
                            } else {
                                beanFactory.put(clazz, o);
                            }
                        }
                    }
                }
            }
        }

    }
}