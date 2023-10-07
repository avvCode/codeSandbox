package com.vv.controller;
import cn.hutool.core.io.resource.ResourceUtil;
import com.google.common.collect.Lists;

import com.vv.model.ExecuteCodeRequest;
import com.vv.model.ExecuteCodeResponse;
import com.vv.sandbox.impl.local.JavaNativeCodeSandbox;

import java.nio.charset.StandardCharsets;

/**
 * @author vv
 */
public class Test {
    public static void main(String[] args) {
        JavaNativeCodeSandbox javaNativeCodeSandbox = new JavaNativeCodeSandbox();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Lists.newArrayList("1 2" ,"4 5","6 7"));
        String code = ResourceUtil.readStr("testCode/acm/Main.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }
}
