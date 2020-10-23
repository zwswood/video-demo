package com.linrun.ssm.controller;

import com.linrun.ssm.constant.GlobalConst;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明
 * ffmpegUtils工具类
 *
 * @author zws
 * @version V1.0
 * @Package com.linrun.ssm.controller
 * @date 2020-10-20 16:39
 * @Copyright © 2020 深圳榕亨实业集团有限公司
 */
public class FFmpegUtils {

    private String ffmpegpath = GlobalConst.ffmpegpath;         // ffmpeg.exe工具的目录

    /*
     * 功能描述: 视频格式转换
     * @Param: sourceVideoPath 源视频路径
     * @Param: type 转换的目标格式
     * @Return: boolean
     * @Author: zws
     * @Date: 2020-10-21 10:20
     */
    public boolean ConvertVideoType(String sourceVideoPath, String type) {
        File fi = new File(sourceVideoPath);
        String filename = fi.getName();            //获取视频文件的名称。
//        String filerealname = filename.substring(0, filename.lastIndexOf("."));    //获取视频名+不加后缀名
        String oldFullPathName = sourceVideoPath.substring(0, sourceVideoPath.lastIndexOf("."));//取视频后缀名 后面加.toLowerCase()转为小写
        String targetPathName = oldFullPathName + "." + type;// 转换后的文件名(包含路径)
        // 构建命令行
        List<String> command = new ArrayList<String>();
        command.add(ffmpegpath);            //指定ffmpeg工具的路径
        command.add("-i");
        command.add(sourceVideoPath);            //源视频路径
        command.add("-vcodec");
        command.add("h263");  //
        command.add("-ab");        //新增4条
        command.add("128");      //高品质:128 低品质:64
        command.add("-acodec");
        command.add("mp3");      //音频编码器：原libmp3lame
        command.add("-ac");
        command.add("2");       //原1
        command.add("-ar");
        command.add("22050");   //音频采样率22.05kHz
        command.add("-r");
        command.add("29.97");  //高品质:29.97 低品质:15
        command.add("-c:v");
        command.add("libx264");    //视频编码器：视频是h.264编码格式
        command.add("-strict");
        command.add("-2");
        command.add(targetPathName);  // //转码后的路径+名称，是指定后缀的视频
        File fileTarget = new File(targetPathName);
        if (fileTarget.exists()) {
            System.out.println("删除已存在的文件：" + targetPathName);
            fileTarget.delete();
        }
        return ExcuteCommand(command);

    }


    /**
     * 视频截图
     * 命令行：ffmpeg -ss 00:00:01 -i sea.mp4 -f image2 -s 960x400 -y test1.jpg
     *
     * @param sourceVideoPath 需要被截图的视频路径（包含文件名和后缀名）
     * @param imageRealPath   截屏保存路径
     * @param time            截屏时间点 ("00:00:42")
     * @param size            图片尺寸 ("800x600")
     * @return
     */
    public boolean ScreenCapture(String sourceVideoPath, String imageRealPath, String time, String size) {
        //先确保保存截图的文件夹存在
        File TempFile = new File(imageRealPath);
        if (TempFile.exists()) {
            if (TempFile.isDirectory()) {
                System.out.println("该文件夹存在。");
            } else {
                System.out.println("同名的文件存在，不能创建文件夹。");
            }
        } else {
            System.out.println("文件夹不存在，创建该文件夹。");
            TempFile.mkdir();
        }

        File fi = new File(sourceVideoPath);
        String filename = fi.getName();            //获取视频文件的名称。
        String filerealname = filename.substring(0, filename.lastIndexOf("."));    //获取视频名+不加后缀名
        String imgName = filerealname + "_" + time.replace(":", "") + ".jpg";// 截图的名称
        String targetPathName = imageRealPath + imgName;
        // 构建命令行
        List<String> command = new ArrayList<String>();
        //截图命令：ffmpeg -ss 00:00:01 -i sea.mp4 -f image2 -s 960x400 -y test1.jpg
        command.add(ffmpegpath);            //指定ffmpeg工具的路径
        command.add("-ss");
        command.add(time);            //1是代表第1秒的时候截图
        command.add("-i");
        command.add(sourceVideoPath);        //源视频路径
        command.add("-f");
        command.add("image2");
        if (!"".equals(size)) {
            command.add("-s");
            command.add(size);
        }
        command.add("-y");
        command.add(targetPathName);        //生成截图保存路径+xxx.jpg
        File fileTarget = new File(targetPathName);
        if (fileTarget.exists()) {
            System.out.println("删除已存在的文件：" + targetPathName);
            fileTarget.delete();
        }
        return ExcuteCommand(command);
    }

    /*
     * 剪切音视频
     * 命令行：ffmpeg -ss 00:00:10 -to 00:00:20 -i test.mp4 -codec copy cut.mp4
     * @Param sourceVideoPath 源视频路径(包含文件名与后缀名) 如video/sea.mp4
     * @Param targetVideoPath 剪切后的视频保存路径
     * @Param startTime 开始时间点
     * @Param endTime 结束时间点
     * @Return: boolean
     * @Author: zws
     * @Date: 2020-10-21 9:52
     */
    public String CutVideo(String sourceVideoPath, String targetVideoPath, String startTime, String endTime) {
        String fileName = "";
        //先确保保存截图的文件夹存在
        File TempFile = new File(targetVideoPath);
        if (TempFile.exists()) {
            if (TempFile.isDirectory()) {
                System.out.println("该文件夹存在。");
            } else {
                System.out.println("同名的文件存在，不能创建文件夹。");
            }
        } else {
            System.out.println("文件夹不存在，创建该文件夹。");
            TempFile.mkdir();
        }
        File fi = new File(sourceVideoPath);
        String filename = fi.getName();            //获取视频文件的名称。
        String filerealname = filename.substring(0, filename.lastIndexOf("."));    //获取视频名+不加后缀名
        String type = sourceVideoPath.substring(sourceVideoPath.lastIndexOf(".") + 1, sourceVideoPath.length()).toLowerCase();//取视频后缀名 后面加.toLowerCase()转为小写


        String cutName = filerealname + "_cut_" + startTime.replace(":", "") + "_to_" + endTime.replace(":", "") + "." + type;// 截图的名称

        String targetPathName = targetVideoPath + cutName;

        // 构建命令行
        List<String> command = new ArrayList<String>();
        // 剪切命令：ffmpeg -ss 00:00:10 -to 00:00:20 -accurate_seek -i ss.mp4 -codec copy -avoid_negative_ts 1 ./cutVideo/cut.mp4
        // 从第10处开始剪切，剪切到第20秒
        command.add(ffmpegpath);            //指定ffmpeg工具的路径
        command.add("-ss");
        command.add(startTime);// 从startTime秒处开始剪切
        command.add("-to");
        command.add(endTime);// 剪切continueTime秒
        command.add("-accurate_seek");
        command.add("-i");
        command.add(sourceVideoPath);        //源视频路径
        command.add("-codec");
        command.add("copy");
        command.add("-avoid_negative_ts");
        command.add("1");
        command.add(targetPathName);//剪切后的路径+文件名
        File fileTarget = new File(targetPathName);
        if (fileTarget.exists()) {
            System.out.println("删除已存在的文件：" + targetPathName);
            fileTarget.delete();
        }
        if (ExcuteCommand(command)) {
            fileName = cutName;
        }
        return fileName;
    }


    /*
     * 功能描述: 执行ffmpeg命令行
     * @Param: [command]
     * @Return: boolean
     * @Author: zws
     * @Date: 2020-10-21 8:38
     */
    private boolean ExcuteCommand(List<String> command) {
        StringBuffer test = new StringBuffer();
        for (int i = 0; i < command.size(); i++) {
            test.append(command.get(i) + " ");
        }
        System.out.println("执行命令:" + test);
        try {
            //调用线程处理命令
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);
            Process p = builder.start();

            //获取进程的标准输入流
            final InputStream is1 = p.getInputStream();
            //获取进程的错误流
            final InputStream is2 = p.getErrorStream();
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            new Thread(() -> {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is1));
                try {
                    String lineB = null;
                    while ((lineB = br.readLine()) != null) {
                        if (lineB != null) {
                            System.out.println(lineB);    //必须取走线程信息避免堵塞
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //关闭流
                finally {
                    try {
                        is1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(() -> {
                BufferedReader br2 = new BufferedReader(
                        new InputStreamReader(is2));
                try {
                    String lineC = null;
                    while ((lineC = br2.readLine()) != null) {
                        if (lineC != null) {
                            System.out.println(lineC);   //必须取走线程信息避免堵塞
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //关闭流
                finally {
                    try {
                        is2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            // 等Mencoder进程转换结束，再调用ffmepg进程非常重要！！！
            p.waitFor();
            System.out.println("ffmepg进程结束");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
