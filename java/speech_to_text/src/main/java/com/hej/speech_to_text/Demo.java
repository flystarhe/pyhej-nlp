package com.hej.speech_to_text;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.PropertyConfigurator;

import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.LfasrType;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.cpdb.lfasr.model.ProgressStatus;

public class Demo {
    private static LfasrType type = LfasrType.LFASR_STANDARD_RECORDED_AUDIO;
    private static String err_msg = null;
    private static int sleepSecond = 1;

    static {
        try {
            InputStream is = Demo.class.getClassLoader().getResourceAsStream("log4j.properties");
            Properties prop = new Properties();
            prop.load(is);
            PropertyConfigurator.configure(prop);
        } catch (Exception e) {
            err_msg = "!!!/failed: load log4j.properties";
        }
    }

    public String to_text(String local_file) {
        return to_text(local_file, "true", null);
    }

    public String to_text(String local_file, String has_participle) {
        return to_text(local_file, has_participle, null);
    }

    public String to_text(String local_file, String has_participle, String max_alternatives) {
        if (err_msg != null) {
            return err_msg;
        }

        // 初始化LFASR实例
        LfasrClientImp lc = null;
        try {
            lc = LfasrClientImp.initLfasrClient();
        } catch (LfasrException e) {
            // 初始化异常
            Message initMsg = JSON.parseObject(e.getMessage(), Message.class);
            return "!!!/" + initMsg.getErr_no() + "/" + initMsg.getFailed();
        }

        // 获取上传任务ID
        String task_id = "";
        HashMap<String, String> params = new HashMap<String, String>();
        if (has_participle != null) {
            params.put("has_participle", has_participle);
        }
        if (max_alternatives != null) {
            params.put("max_alternatives", max_alternatives);
        }
        try {
            // 上传音频文件
            Message uploadMsg = lc.lfasrUpload(local_file, type, params);
            if (uploadMsg.getOk() == 0) {
                // 创建任务成功
                task_id = uploadMsg.getData();
            } else {
                // 创建任务失败
                return "!!!/" + uploadMsg.getErr_no() + "/" + uploadMsg.getFailed();
            }
        } catch (LfasrException e) {
            // 上传异常
            Message uploadMsg = JSON.parseObject(e.getMessage(), Message.class);
            return "!!!/" + uploadMsg.getErr_no() + "/" + uploadMsg.getFailed();
        }

        // 循环等待结果
        while (true) {
            // 短暂睡眠
            try {
                Thread.sleep(sleepSecond * 1000);
            } catch (InterruptedException e) {
            }

            // 获取处理进度
            try {
                Message progressMsg = lc.lfasrGetProgress(task_id);

                // 如果状态不等于0,任务失败
                if (progressMsg.getOk() != 0) {
                    // 等待服务端重试
                    continue;
                } else {
                    ProgressStatus progressStatus = JSON.parseObject(progressMsg.getData(), ProgressStatus.class);
                    if (progressStatus.getStatus() == 9) {
                        // 处理完成
                        break;
                    } else {
                        // 未处理完成
                        continue;
                    }
                }
            } catch (LfasrException e) {
                // 获取处理进度异常
                break;
            }
        }

        // 获取任务结果
        try {
            Message resultMsg = lc.lfasrGetResult(task_id);
            if (resultMsg.getOk() == 0) {
                // 转写成功
                return resultMsg.getData();
            } else {
                // 转写失败
                return "!!!/" + resultMsg.getErr_no() + "/" + resultMsg.getFailed();
            }
        } catch (LfasrException e) {
            // 获取结果异常
            Message resultMsg = JSON.parseObject(e.getMessage(), Message.class);
            return "!!!/" + resultMsg.getErr_no() + "/" + resultMsg.getFailed();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        String local_file = "/data2/datasets/video_teaching2017/_20170916082623_1min.wav";
        System.out.println(demo.to_text(local_file, "false", "3"));
    }
}
