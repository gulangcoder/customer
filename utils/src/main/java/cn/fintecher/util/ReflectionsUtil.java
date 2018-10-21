package cn.fintecher.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.util.Assert;

import java.lang.reflect.*;

/**
 * @Author: wangtao
 * @Date: 2018/05/14 16:36
 * @Description:反射工具类
 */
public class ReflectionsUtil {
    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:45
     * @Params: obj：反射的对象  propertyName：属性名
     * @Description: 调用Getter方法. 支持多级，如：对象名.对象名.方法
     * @return: 返回属性值
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : StringUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
        }
        return object;
    }

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:50
     * @Params: obj:需要反射的对象，propertyName：属性名，value：属性值
     * @Description:调用Setter方法, 仅匹配方法名。 支持多级，如：对象名.对象名.方法
     * @return:
     */
    public static void invokeSetter(Object obj, String propertyName, Object propertyValue) {
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[] {propertyValue});
            }
        }
    }

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:56
     * @Params: obj:需反射的对象，fieldName：属性名
     * @Description:直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     * @return: 返回属性值
     */
    public static Object getFieldValue(final Object obj, final String fieldName) throws IllegalArgumentException, IllegalAccessException {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }
        Object result = null;
        result = field.get(obj);
        return result;
    }

    /**
     * @Author:wangtao
     * @Date:2018/05/14 16:58
     * @Params: obj：需反射的对象，fieldName：属性名，fieldName：属性值
     * @Description:直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     * @return:
     */
    public static void setFieldValue(final Object obj, final String fieldName,
                                     final Object fieldValue) throws IllegalArgumentException, IllegalAccessException {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }
        field.set(obj, fieldValue);
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/14 17:02
     * @Params: obj：需反射的对象，methodName：方法名，parameterTypes：类型，args：参数
     * @Description:直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用. 同时匹配方法名+参数类型，
     * @return:
     */
    public static Object invokeMethod(final Object obj,final String methodName,
                                      final Class<?>[] parameterTypes,final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + obj + "]");
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/14 17:20
     * @Params: obj(需反射的对象)，methodName(方法名)，args(参数)
     * @Description: 直接调用对象方法, 无视private/protected修饰符，
     * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
     * 只匹配函数名，如果有多个同名函数调用第一个。
     * @return: 返回反射方法的返回值
     */
    public static Object invokeMethodByName(final Object obj,
                                            final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + obj + "]");
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     *
     * 如向上转型到Object仍无法找到, 返回null.
     */
    /**
     * @Author: wangtao
     * @Date: 2018/05/14 17:28
     * @Params: obj(需反射的对象),fieldName(属性名)
     * @Description: 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * @return:
     */
    public static Field getAccessibleField(final Object obj,
                                           final String fieldName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/14 17:35
     * @Params: obj(需反射的对象),methodName(方法名)，parameterTypes（类型）
     * @Description: 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
     * 匹配函数名+参数类型。用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...args)
     * @return: Method
     */
    public static Method getAccessibleMethod(final Object obj,
                                             final String methodName, final Class<?>... parameterTypes) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName,
                        parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 11:12
     * @Params: obj(反射对象)
     * @Params: methodName(方法名)
     * @Description: 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null. 只匹配函数名。
     *                用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...args)
     * @return: Method
     */
    public static Method getAccessibleMethodByName(final Object obj,
                                                   final String methodName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 11:14
     * @Params: method
     * @Description: 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     * @return:
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier
                .isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 11:14
     * @Params: field
     * @Description: 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     * @return:
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
                .isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 11:14
     * @Params: clazz
     * @Description: 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处 如无法找到, 返回Object.class. eg.
     * @return:
     */
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 11:15
     * @Params: clazz
     * @Params: index
     * @Description: 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     * @return:
     */
    public static Class getClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
			/*logger.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");*/
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
			/*logger.warn("Index: " + index + ", Size of "
					+ clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);*/
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
			/*logger.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");*/
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 11:16
     * @Params: instance（反射对象）
     * @Description: 反射获取类名
     * @return:
     */
    public static Class<?> getUserClass(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/15 11:18
     * @Params: e(异常类)
     * @Description: 将反射时的checked exception转换为unchecked exception.
     * @return: RuntimeException
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException
                || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(
                    ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }
}
