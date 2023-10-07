package com.vv.sandbox.template;

import com.vv.model.ExecuteCodeRequest;
import com.vv.model.ExecuteCodeResponse;
import com.vv.model.ExecuteMessage;
import com.vv.sandbox.CodeSandbox;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @author vv
 */
@Slf4j
public class CppCodeSandboxTemple extends CodeSandboxTemplate implements CodeSandbox {
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    private static final long TIME_OUT = 5000L;

    private static final String COMPIlE_CMD = "javac -encoding utf-8 %s";
    private static final String RUN_CMD = "java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main";


    public CppCodeSandboxTemple() {
        super.globalCodeDirName = GLOBAL_CODE_DIR_NAME;
        super.timeOut = TIME_OUT;
        super.globalCodeName = GLOBAL_JAVA_CLASS_NAME;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        try {
            List<String> inputList = executeCodeRequest.getInputList();
            String code = executeCodeRequest.getCode();

            //1. 把用户的代码保存为文件
            File userCodeFile = saveCodeToFile(code);

            //2. 编译代码，得到 class 文件


            ExecuteMessage compileFileExecuteMessage = compileFile(userCodeFile,COMPIlE_CMD);

            System.out.println(compileFileExecuteMessage);


            // 3. 执行代码，得到输出结果
            List<ExecuteMessage> executeMessageList = runFile(userCodeFile,RUN_CMD, inputList);

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
