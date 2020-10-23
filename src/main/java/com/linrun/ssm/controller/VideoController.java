package com.linrun.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/video")
public class VideoController extends BaseController {
    /**
     * 查询队列大小
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getString")
    public String getString() {
        return "TMMS-VIDEO";
    }

    /*
     * 功能描述: 上传文件并转换为MP4格式
     * @Param: [file, request]
     * @Return: java.lang.String
     * @Author: zws
     * @Date: 2020-10-23 15:29
     */
    @ResponseBody
    @RequestMapping("/convertVideo")
    public String convertVideo(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {

        String result = "";
        System.out.println("进入addVideo视频上传控制层");
        if (file.getSize() != 0) {
            String path = request.getSession().getServletContext().getRealPath("") + "video\\";

            File TempFile = new File(path);
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

            // 获取上传时候的文件名
            String filename = file.getOriginalFilename();

            // 获取文件后缀名
            String filename_extension = filename.substring(filename
                    .lastIndexOf(".") + 1);
            System.out.println("视频的后缀名:" + filename_extension);

            //时间戳做新的文件名，避免中文乱码-重新生成filename
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String currentDate = dateFormat.format(new Date());
            filename = currentDate + "." + filename_extension;

            //源视频地址+重命名后的视频名+视频后缀
            String yuanPATH = path + filename;
            System.out.println("源视频路径为:" + yuanPATH);

            //上传到本地磁盘/服务器
            try {
                System.out.println("写入本地磁盘/服务器");
                InputStream is = file.getInputStream();
                OutputStream os = new FileOutputStream(new File(path, filename));
                int len = 0;
                byte[] buffer = new byte[2048];

                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.close();
                os.flush();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("========上传完成=======");
            //调用转码机制flv mp4 f4v m3u8 webm ogg放行直接播放，
            //asx，asf，mpg，wmv，3gp，mov，avi，wmv9，rm，rmvb等进行其他转码为mp4
            if (filename_extension.equals("avi") || filename_extension.equals("rm")
                    || filename_extension.equals("rmvb") || filename_extension.equals("wmv")
                    || filename_extension.equals("3gp") || filename_extension.equals("mov")
                    || filename_extension.equals("flv") || filename_extension.equals("ogg")

            ) {
                FFmpegUtils utils = new FFmpegUtils();
                utils.ConvertVideoType(yuanPATH, "mp4");
            }
            String newFileName = currentDate + ".mp4";
            //删除临时文件
            File f = new File(yuanPATH);
            if (f.exists()) {
                f.delete();
                System.out.println("删除源视频");
            } else {
                System.out.println("没有该文件");
            }

            String url = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath()
                    + "/video/" + newFileName;

            System.out.println("视频URL:" + url);
            result = url;
        }
        return result;
    }

    /*
     * 功能描述: 剪切视频
     * @Param: [file, request]
     * @Return: java.lang.String
     * @Author: zws
     * @Date: 2020-10-23 15:29
     */
    @ResponseBody
    @RequestMapping("/cutVideo")
    public String cutVideo(@RequestParam("file") CommonsMultipartFile file, @RequestParam("fileName") String fileName,
                           @RequestParam("starTime") String starTime, @RequestParam("endTime") String endTime,
                           HttpServletRequest request) {
        String result = "";
        String path = request.getSession().getServletContext().getRealPath("") + "video\\";
        String realPath = path + fileName;
        FFmpegUtils ffUtils = new FFmpegUtils();
        File f = new File(realPath);
        if (!f.exists()) {
            System.out.println("不存在文件,先上传");
            // 获取上传时候的文件名
            String filename = file.getOriginalFilename();
            // 获取文件后缀名
            String filename_extension = filename.substring(filename
                    .lastIndexOf(".") + 1);
            //时间戳做新的文件名，避免中文乱码-重新生成filename
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String currentDate = dateFormat.format(new Date());
            filename = currentDate + "." + filename_extension;
            //上传到本地磁盘/服务器
            try {
                System.out.println("写入本地磁盘/服务器");
                InputStream is = file.getInputStream();
                OutputStream os = new FileOutputStream(new File(path, filename));
                int len = 0;
                byte[] buffer = new byte[2048];

                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.close();
                os.flush();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("========上传完成=======");
            realPath = path + filename;// 上传后重新赋值
        }
        //裁剪视频
        String cutFileName = ffUtils.CutVideo(realPath, path, starTime, endTime);
        if (!"".equals(cutFileName)) {
            String url = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath()
                    + "/video/" + cutFileName;
            System.out.println("视频URL:" + url);
            result = url;
        }
        return result;
    }
}
