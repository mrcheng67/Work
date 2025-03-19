package cn.jinan.Common;


import java.io.Serializable;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author CH
 * @title Invocation
 * @date 2024/11/29 14:11
 * @description TODO
 */
public class Invocation implements Serializable {
    private String interfaceName;
    private String methodName;
    private Class[] parameterTypes;
    private Object[] parameters;

    // 构造器
    public Invocation(String interfaceName, String methodName, Class[] parameterTypes, Object[] parameters) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
