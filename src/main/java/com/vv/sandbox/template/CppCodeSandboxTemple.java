package com.vv.sandbox.template;

import com.vv.model.ExecuteCodeRequest;
import com.vv.model.ExecuteCodeResponse;
import com.vv.model.ExecuteMessage;
import com.vv.sandbox.CodeSandbox;
import com.vv.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vv
 */
@Slf4j
public class CppCodeSandboxTemple extends CodeSandboxTemplate implements CodeSandbox {
    private static final long TIME_OUT = 1000L;
    private static final String CODE_FILE_SUFFIX = ".cpp";
    private static final String CODE_FILE_PREFIX = "main";
    private static final String COMPIlE_CMD_FORMAT = "g++ -finput-charset=UTF-8 -fexec-charset=UTF-8 %s -o %s";
    private static final String RUN_CMD_FORMAT = CODE_FILE_PREFIX;


    public CppCodeSandboxTemple() {
        super.codeFileSuffix = CODE_FILE_SUFFIX;
        super.timeOut = TIME_OUT;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        try {
            List<String> inputList = executeCodeRequest.getInputList();
            String code = executeCodeRequest.getCode();

            //1. 把用户的代码保存为文件
            File userCodeFile = saveCodeToFile(code);

            //2. 编译代码，得到 class 文件
            String userCodePath = userCodeFile.getAbsolutePath();
            String compileCmd = String.format(COMPIlE_CMD_FORMAT,userCodePath, userCodePath.substring(0, userCodePath.length() - 4));
            ExecuteMessage compileFileExecuteMessage = compileFile(userCodeFile,compileCmd);

            System.out.println(compileFileExecuteMessage);

            // 3. 执行代码，得到输出结果
            String runCmd = userCodeFile.getParentFile() + File.separator + RUN_CMD_FORMAT;
            List<ExecuteMessage> executeMessageList = runFile(userCodeFile,runCmd, inputList);

            //4. 收集整理输出结果
            ExecuteCodeResponse outputResponse = getOutputResponse(executeMessageList);

            //5. 文件清理
            boolean b = deleteFile(userCodeFile);
            if (!b) {
                log.error("deleteFile error, userCodeFilePath = {}", userCodePath);
            }
            return outputResponse;
        } catch (Exception e) {
            return getErrorResponse(e);
        }
    }
}
