package com.vv.sandbox;

import com.vv.sandbox.impl.local.CppNativeCodeSandbox;
import com.vv.sandbox.impl.local.JavaNativeCodeSandbox;

/**
 * 沙箱工程
 * 为controller提供不同的代码沙箱
 * @author vv
 */
public class CodeSandboxFactory {
    public CodeSandbox getCodeSandbox(String language){
        switch (language){
            case "java":
                return new JavaNativeCodeSandbox();
            case "cpp":
                return new CppNativeCodeSandbox();
        }
        throw new RuntimeException("Not Support This Language !");
    }
}
