package com.linrun.ssm.controller;

/**
 * 类说明
 *
 * @author zws
 * @version V1.0
 * @Package com.linrun.ssm.controller
 * @date 2020-10-20 17:10
 * @Copyright © 2020 深圳榕亨实业集团有限公司
 */
public class TestMain {

    /*本地测试专用*/
    public static void main(String[] args) {
        FFmpegUtils fFmpegUtils = new FFmpegUtils();
        String sourceVideoPath = "G:/testvideo/sea.mp4";  //本地源视频

        // 转换为MP4格式
        /*String type = "mp4";// 目标格式
        fFmpegUtils.ConvertVideoType(sourceVideoPath, type);*/

        // 视频截图
        /*String imageRealPath = "G:/testvideo/screen/";
        String time = "00:01:40";
        fFmpegUtils.ScreenCapture(sourceVideoPath, imageRealPath, time, "");*/

        // 视频剪切
        String targetVideoPath = "G:/testvideo/cutVideo/";
        String startTime = "00:00:10";
        String continueTime = "00:00:20";
        fFmpegUtils.CutVideo(sourceVideoPath, targetVideoPath, startTime, continueTime);
    }
}
