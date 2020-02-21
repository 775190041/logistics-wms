package com.nf.commons.uilts;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 实体工具类，目前copy不支持map、list
 */
public final class BeanUtils extends org.springframework.beans.BeanUtils {
    private BeanUtils(){}

    /**
     * 实例化对象
     * @param clz 类
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clz){
        try {
            return  (T) clz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化对象
     * @param clazzStr 类名
     * @return 对象
     */
    public static <T> T newInstance(String clazzStr) {
        try {
            Class<?> clazz = Class.forName(clazzStr);
            return (T) newInstance(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * copy 对象属性到另一个对象，默认不使用Convert
     *
     * @param src
     * @param clazz 类名
     * @return T
     */
    public static <T> T copy(Object src, Class<T> clazz) {
        BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);
        T to = newInstance(clazz);
        copier.copy(src, to, null);
        return to;
    }

    /**
     * 拷贝对象
     *
     * @param src  源对象
     * @param dist 需要赋值的对象
     */
    public static void copt(Object src , Object dist){
        BeanCopier copier = BeanCopier.create(src.getClass(),dist.getClass(),false);
        copier.copy(src,dist,null);
    }

}
