package com.vv.sandbox.template;

import com.vv.model.ExecuteCodeRequest;
import com.vv.model.ExecuteCodeResponse;
import com.vv.model.ExecuteMessage;
import com.vv.sandbox.CodeSandbox;
import com.vv.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * Java 代码沙箱模板方法的实现
 */
@Slf4j
public abstract class JavaCodeSandboxTemplate extends CodeSandboxTemplate implements CodeSandbox {
    private static final String CODE_FILE_SUFFIX = ".java";
    private static final long TIME_OUT = 5000L;
    private static final String CODE_FILE_PREFIX = "Main";

    private static final String COMPIlE_CMD_FORMAT = "javac -encoding utf-8 %s";
    private static final String RUN_CMD_FORMAT = "java -Xmx256m -Dfile.encoding=UTF-8 -cp %s " + CODE_FILE_PREFIX;


    public JavaCodeSandboxTemplate() {
        super.timeOut = TIME_OUT;
        super.codeFileSuffix = CODE_FILE_SUFFIX;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        try {
            List<String> inputList = executeCodeRequest.getInputList();
            String code = executeCodeRequest.getCode();

            //1. 把用户的代码保存为文件
            File userCodeFile = saveCodeToFile(code);

            //2. 编译代码，得到 class 文件
            String compileCmd = String.format(COMPIlE_CMD_FORMAT,userCodeFile.getAbsolutePath());

            ExecuteMessage compileFileExecuteMessage = compileFile(userCodeFile,compileCmd);

            System.out.println(compileFileExecuteMessage);


            // 3. 执行代码，得到输出结果
            String runCmd = String.format(RUN_CMD_FORMAT,userCodeFile.getAbsolutePath());

            List<ExecuteMessage> executeMessageList = runFile(userCodeFile,runCmd, inputList);

            //4. 收集整理输出结果
            ExecuteCodeResponse outputResponse = getOutputResponse(executeMessageList);

            //5. 文件清理
            boolean b = deleteFile(userCodeFile);
            if (!b) {
                log.error("deleteFile error, userCodeFilePath = {}", userCodeFile.getAbsolutePath());
            }

            return outputResponse;
        } catch (Exception e) {
            return getErrorResponse(e);
        }
    }
}
