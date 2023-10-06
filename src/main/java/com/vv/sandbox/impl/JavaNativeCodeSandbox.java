package com.vv.sandbox.impl;


import com.vv.model.ExecuteCodeRequest;
import com.vv.model.ExecuteCodeResponse;
import com.vv.sandbox.JavaCodeSandboxTemplate;
import org.springframework.stereotype.Component;

/**
 * Java 原生代码沙箱实现（直接复用模板方法）
 */
@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
