<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    // 使用小脚本访问
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=path %>/static/js/jquery.js" charset="UTF-8"></script>
    <script src="<%=path %>/static/js/jquery.nicescroll.js" charset="UTF-8"></script>
    <link rel="stylesheet" href="<%=path %>/static/js/alert.css">
    <script src="<%=path %>/static/js/alert.min.js"></script>
    <link rel="stylesheet" href="<%=path %>/static/js/ion.rangeSlider.css">
    <script src="<%=path %>/static/js/ionRangeSlider.js"></script>
    <script src="<%=path %>/static/js/tool.js"></script>
    <!-- ajax请求遮罩层控件busyLoad add by zws -->
    <!-- 引入busyLoad等待遮罩层控件样式 -->
    <link type='text/css' rel='stylesheet' href="<%=path %>/static/js/busyLoad.css"/>
    <!-- 引入busyLoad等待遮罩层控件js -->
    <script src="<%=path %>/static/js/busyLoad.js"></script>


    <!-- 裁剪插件cropper的样式 -->
    <link rel="stylesheet" href="<%=path %>/static/js/cropper.css">
    <link rel="stylesheet" href="<%=path %>/static/js/ImgCropper.css">
    <%--裁剪插件cropper--%>
    <script src="<%=path %>/static/js/cropper.js" charset="UTF-8"></script>

    <title>视频编辑</title>
    <style>
        .tailoring-container .tailoring-content {
            background: #0e3748;
            box-shadow: #2fb7de 0 0 2px 1px inset;
        }

        .preview-box-parcel .square, .tailoring-content-one, .tailoring-content .tailoring-box-parcel, .preview-box-parcel .circular, .tailoring-content .tailoring-content-three {
            /*border-color: #0AAB8A;*/
            border-color: #8A2BE2;
        }

        .preview-box-parcel p, #imgName {
            color: #8A2BE2;
        }

        /* 音频控件样式===============================================================================*/
        .audio-wrapper {
            background-color: #fcfcfc;
            margin: 10px auto;
            max-width: 1080px;
            height: 60px;
            border: 1px solid #8A2BE2;
        }

        .audio-left {
            float: left;
            text-align: center;
            width: 50px;
            height: 100%;
        }

        .audio-left img {
            width: 40px;
            position: relative;
            top: 10px;
            margin: 0;
            display: initial; /* 解除与app的样式冲突 */
            cursor: pointer;
        }

        .audio-right {
            margin: 0px 10px;
            float: right;
            width: 1005px;
            height: 100%;
        }

        .audio-right p {
            font-size: 15px;
            height: 35%;
            margin: 8px 0;

            /* 歌曲名称只显示在一行，超出部分显示为省略号 */
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            max-width: 243px; /* 要适配小屏幕手机，所以最大宽度先设小一点，后面js根据屏幕大小重新设置 */
        }

        .progress-bar-bg {
            background-color: #d9d9d9;
            position: relative;
            height: 2px;
        }

        .progress-bar {
            background-color: #649fec;
            width: 0;
            height: 2px;
        }

        .progress-bar-bg span {
            content: " ";
            width: 10px;
            height: 10px;
            border-radius: 50%;
            -moz-border-radius: 50%;
            -webkit-border-radius: 50%;
            background-color: #3e87e8;
            position: absolute;
            left: 0;
            top: 50%;
            margin-top: -5px;
            margin-left: -5px;
        }

        .audio-time {
            overflow: hidden;
            margin-top: 1px;
        }

        .audio-length-total {
            float: right;
            font-size: 12px;
        }

        .audio-length-current {
            float: left;
            font-size: 12px;
        }


    </style>
</head>
<body>
<div style="margin: 0 auto; width: 1500px;">

    <fieldset style="margin-top: 20px;border: 2px solid #8A2BE2;padding:0 50px;">
        <legend style="color:#8A2BE2">图片裁剪</legend>
        <div class="tailoring-content">
            <div class="tailoring-content-one">
                <label title="上传图片" for="chooseImg" class="l-btn choose-btn" style="margin: 8px;">
                    <input type="file" accept="image/*" id="chooseImg"
                           class="hidden">
                    选择图片
                </label>
                <span id="imgName" style="line-height: 40px;"></span>
            </div>
            <div class="tailoring-content-two">
                <div class="tailoring-box-parcel">
                    <img id="tailoringImg">
                </div>
                <div class="preview-box-parcel" style="right: 450px;">
                    <p>裁剪预览：</p>
                    <div class="square previewImg" style="width: 450px;height: 415px;"></div>
                    <%--<div class="circular previewImg"></div>--%>
                </div>
            </div>
            <div class="tailoring-content-three">
                <button class="l-btn cropper-reset-btn" disabled>复位</button>
                <button class="l-btn cropper-rotate-btn" disabled>旋转</button>
                <button class="l-btn cropper-scaleX-btn" disabled>换向</button>
                <button class="l-btn sureCut" id="sureCut" disabled>上传</button>
            </div>
        </div>
    </fieldset>

    <fieldset style="margin-top: 20px;border: 2px solid #8A2BE2;padding:0 50px;">
        <legend style="color:#8A2BE2">音频裁剪</legend>
        <div style="margin:10px 5px;border-bottom: 1px solid #8A2BE2;">
            <label title="选择音频" for="fileAudio" class="l-btn choose-btn">
                <input type="file" id="fileAudio" class="hidden" accept="audio/mpeg,audio/ogg" onchange="readAudio(this.files)"/>
                选择音频
            </label>
        </div>
        <div id="divAudio" style="margin:10px 5px; width: 1080px;">
            <div class="audio-wrapper">
                <audio id="audioShow"></audio>
                <div class="audio-left"><img id="audioImg" src="<%=path %>/static/img/play.png"></div>
                <div class="audio-right">
                    <marquee><p style="max-width: 1000px;" id="audioPname"></p></marquee>
                    <div class="progress-bar-bg" id="progressBarBg"><span id="progressDot"></span>
                        <div class="progress-bar" id="progressBar"></div>
                    </div>
                    <div class="audio-time"><span class="audio-length-current" id="audioCurTime">00:00:00</span><span
                            class="audio-length-total" id="audioDurationTime">00:01:00</span></div>
                </div>
            </div>
            <div>
                <div class="range-slider" style="width: 1080px;">
                    <input type="text" id="rangeAudio" class="js-range-slider" value=""/>
                </div>
                <div class="extra-controls">
                    <input type="text" id="inputFromAudio" class="js-input-from" style="width: 120px;"
                           value="0"/>
                    —
                    <input type="text" id="inputToAudio" class="js-input-to" style="width: 120px;" value="0"/>
                    <span id="showAudioPlayTime"></span>
                </div>
                <input id="btnCutAudio" class="l-btn" type="button" onclick="cutAudio();" value="裁剪"/>
            </div>

        </div>
    </fieldset>

    <fieldset style="margin-top: 20px;border: 2px solid #8A2BE2;padding:0 50px;">
        <legend id="show" style="color:#8A2BE2">视频预览</legend>
        <div style="margin:10px 5px;border-bottom: 1px solid #8A2BE2;">
            <label title="选择视频" for="file" class="l-btn choose-btn">
                <input type="file" class="hidden" id="file" accept="video/*" onchange="jsReadFiles(this.files)"/>
                选择视频
            </label>
            <input id="newFileName" type="hidden"/>
        </div>
        <div id="divVideo" style="margin:10px 5px; width: 1080px;">
            <video id="videoshow" controls="controls" style="width: 1080px; border: 1px solid #428BCA;">
                <!-- controls="controls" -->
            </video>
            <div>
                <div class="range-slider" style="width: 1080px;">
                    <input type="text" id="rangeVideo" class="js-range-slider" value=""/>
                </div>
                <div class="extra-controls">
                    <input type="text" id="inputFromVideo" class="js-input-from" style="width: 120px;" value="0"/>
                    —
                    <input type="text" id="inputToVideo" class="js-input-to" style="width: 120px;" value="0"/>
                    <span id="showVideoPlayTime"></span>
                </div>
                <input id="btn" class="l-btn" type="button" onclick="cutVideo();" value="裁剪"/>
            </div>
        </div>
    </fieldset>
</div>
那
</body>
</html>

<script>

    // 图片编辑==========================================================================================
    initCropper();// 初始化裁剪控件
    // 初始化裁剪框
    function initCropper() {
        // cropper图片裁剪框实例
        $('#tailoringImg').cropper({
            // aspectRatio: 1 / 1,// 默认比例
            viewMode: 1, // 视图控制- 0 无限制- 1 限制裁剪框不能超出图片的范围- 2 限制裁剪框不能超出图片的范围 且图片填充模式为 cover 最长边填充- 3 限制裁剪框不能超出图片的范围 且图片填充模式为 contain 最短边填充
            preview: '.previewImg',// 预览视图
            guides: true, // 裁剪框的虚线(九宫格)
            autoCropArea: 0.7, // 0-1之间的数值，定义自动剪裁区域的大小，默认0.8
            movable: true, // 是否允许移动图片
            dragCrop: true, // 是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
            movable: true, // 是否允许移动剪裁框
            resizable: true, // 是否允许改变裁剪框的大小
            zoomable: true, // 是否允许缩放图片大小
            mouseWheelZoom: true, // 是否允许通过鼠标滚轮来缩放图片
            touchDragZoom: false, // 是否允许通过触摸移动来缩放图片
            rotatable: true, // 是否允许旋转图片
            minCropBoxWidth: 50,// 裁剪框的最小宽度
            minCropBoxHeight: 50,// 裁剪框的最小高度
            crop: function (e) {
                // 输出结果数据裁剪图像。
            }
        });
    }

    // 选择图片按钮
    $("#chooseImg").on("change", function (e) {
        selectImg(e);
        $(this).val(''); //清除获取的值，为了可以连续上传同一张
    });

    // 选择文件触发事件
    function selectImg(obj) {
        var file = obj.target.files[0];
        //文件为空，返回
        if (!file) {
            $(".cropper-reset-btn").prop("disabled", true);
            $(".cropper-rotate-btn").prop("disabled", true);
            $(".cropper-scaleX-btn").prop("disabled", true);
            $("#sureCut").prop("disabled", true);
            return;
        }

        // 判断类型是不是图片
        if (!/image\/\w+/.test(file.type)) {
            WarningTips("请确保文件为图像类型!");
            return false;
        }
        let outSize = Math.floor(file.size / 1024);
        if (outSize > 1024 * 2) {
            WarningTips("上传的图片不得大于2MB，所选图片:" + (outSize / 1024).toFixed(2) + "MB!");
            return;
        }
        var imgVal = $("#chooseImg").val();
        //var fileName = imgVal.substring(imgVal.lastIndexOf("\\") + 1);
        $("#imgName").html(imgVal);
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function (evt) {
            var base64Code = evt.target.result;
            $('#tailoringImg').cropper('replace', base64Code, false);// 默认false，适应高度，不失真
            $(".cropper-reset-btn").prop("disabled", false);
            $(".cropper-rotate-btn").prop("disabled", false);
            $(".cropper-scaleX-btn").prop("disabled", false);
            $("#sureCut").prop("disabled", false);
        }
        // 更换cropper的图片
    }

    // 旋转
    $(".cropper-rotate-btn").on("click", function () {
        $('#tailoringImg').cropper("rotate", 90);
    });
    // 复位
    $(".cropper-reset-btn").on("click", function () {
        $('#tailoringImg').cropper("reset");
    });
    // 换向
    var flagX = true;
    $(".cropper-scaleX-btn").on("click", function () {
        if (flagX) {
            $('#tailoringImg').cropper("scaleX", -1);
            flagX = false;
        } else {
            $('#tailoringImg').cropper("scaleX", 1);
            flagX = true;
        }
        flagX != flagX;
    });


    // 确定按钮点击事件
    $("#sureCut").on("click", function () {
        if ($("#tailoringImg").attr("src") == null) {
            return false;
        } else {
            var cas = $('#tailoringImg').cropper('getCroppedCanvas');// 获取被裁剪后的canvas
            var base64 = cas.toDataURL(); // 转换为base64，默认格式为png
            var file = Base64URLtoFile(base64);
            uploadFile(file);
        }
    });

    // base64转file
    function Base64URLtoFile(dataurl) {
        let arr = dataurl.split(',')
        let mime = arr[0].match(/:(.*?);/)[1]
        let suffix = mime.split('/')[1]
        let bstr = atob(arr[1])
        let n = bstr.length
        let u8arr = new Uint8Array(n)
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n)
        }
        let filename = 'zws.' + suffix;
        return new File([u8arr], filename, {type: mime})
    }


    //裁剪后ajax上传(base64)
    function uploadFile(file) {
        let formData = new FormData()
        formData.append('file', file);
        // 延迟Ajax请求
        $(".tailoring-content").busyLoad("show", {
            text: "上传中...",
        }); // 仅遮罩某元素
        $.ajax({
            url: "<%=path %>/video/upLoadImg",
            type: 'POST',
            cache: false,
            processData: false,
            contentType: false,
            data: formData,
            complete: function () {
                // 隐藏遮罩层
                $(".tailoring-content").busyLoad("hide"); // 仅遮罩某元素
            },
            success: function (data) {
                if (data != "") {
                    $('#tailoringImg').cropper("destroy");// 销毁裁剪控件
                    $(".cropper-reset-btn").prop("disabled", true);
                    $(".cropper-rotate-btn").prop("disabled", true);
                    $(".cropper-scaleX-btn").prop("disabled", true);
                    $("#sureCut").prop("disabled", true);
                    $("#imgName").html("");

                    initCropper();

                    ShowTips('上传成！');
                }
            }
        });
    }


    //音频剪切==============================================================================
    let playerAudio = document.querySelector("#audioShow");
    let audioImg = document.querySelector("#audioImg");
    let $audio = $('#audioShow');
    let $rangeAudio = $("#rangeAudio"),
        $inputFromAudio = $("#inputFromAudio"),
        $inputToAudio = $("#inputToAudio"),
        $instanceAudio,
        minAudio = 0,
        maxAudio = 60,
        fromValAudio = 0, // 滑块初始值
        toValAudio = 60; // 滑块结束值


    /**
     * 上传文件本地读取
     * @param {*} files
     */
    function readAudio(files) {
        $audio.attr("src", '');
        var file = files[0];
        //(asx|asf|mpg|wmv|3gp|mp4|mov|avi|flv|mkv)
        if (files.length && /(ogg|mp3)$/.test($("#fileAudio")[0].files[0].name.toLowerCase())) {
            // 遮罩
            $("#divAudio").busyLoad("show", {
                text: "上传中...",
            }); // 仅遮罩某元素
            var reader = new FileReader(); //new一个FileReader实例
            reader.onload = function () {
                $audio.attr("src", this.result);
            }
            reader.readAsDataURL(file);
            // $('#newFileName').val($("#fileAudio")[0].files[0].name);
            $('#audioPname').html($("#fileAudio")[0].files[0].name)
            // 隐藏遮罩层
            $("#divAudio").busyLoad("hide"); // 仅遮罩某元素

            setTimeout(() => {
                playerAudio.play();
                audioImg.src = '<%=path %>/static/img/pause.png';
            }, 500);

        } else {
            WarningTips('请选择正确的音频文件！');
            setTimeout(() => {
                $("#fileAudio")[0].value = '';
            }, 1000);
        }
    }


    $rangeAudio.ionRangeSlider({
        skin: 'sharp', //flat,big,modern,sharp,round,square
        type: "double",
        grid: true,
        from: fromValAudio,
        to: toValAudio,
        onStart: updateAudioInputs,
        onChange: updateAudioInputs,
        values: ConvertTimeAry(60),
    });
    $instanceAudio = $rangeAudio.data("ionRangeSlider");

    // 滑块移动是更新文本框的值
    function updateAudioInputs(data) {
        fromValAudio = data.from;
        toValAudio = data.to;
        $inputFromAudio.val(data.from_value);
        $inputToAudio.val(data.to_value);

        playerAudio.currentTime = fromValAudio;
        if (playerAudio.readyState) {
            playerAudio.play();
            audioImg.src = '<%=path %>/static/img/pause.png';
        }
    }

    // 监听音频播放时间并更新进度条
    playerAudio.addEventListener('loadedmetadata', function () {
        // console.log(playerAudio.duration);
        let duartion = parseInt(this.duration); // 当前音频总时长
        if (duartion > 86400) {
            // 视频时长大于24小时
            return;
        }
        document.getElementById('audioDurationTime').innerText = transTime(duartion);
        $instanceAudio.update({
            from: 0,
            to: duartion,
            values: ConvertTimeAry(duartion)
        });

        $inputFromAudio.val($instanceAudio.result.from_value);
        $inputToAudio.val($instanceAudio.result.to_value);
        maxAudio = duartion;

    }, false);


    $(audioImg).on('click', function () {
        if (playerAudio.readyState) {
            if (playerAudio.paused) {
                playerAudio.play();
                audioImg.src = '<%=path %>/static/img/pause.png';
            } else {
                playerAudio.pause();
                audioImg.src = '<%=path %>/static/img/play.png';
            }
        } else {
            WarningTips('请先选择音频！');
        }
    });

    // 监听音频播放时间并更新进度条
    playerAudio.addEventListener('timeupdate', function () {
        updateProgress(playerAudio);
    }, false);

    /**
     * 更新进度条与当前播放时间
     * @param {object} audio - audio对象
     */
    function updateProgress(audio) {
        var value = audio.currentTime / audio.duration;
        document.getElementById('progressBar').style.width = value * 100 + '%';
        document.getElementById('progressDot').style.left = value * 100 + '%';
        document.getElementById('audioCurTime').innerText = SecondsToTime(audio.currentTime);
        document.getElementById('audioDurationTime').innerText = transTime(playerAudio.duration);
    }

    // 监听播放完成事件
    playerAudio.addEventListener('ended', function () {
        audioEnded();
    }, false);

    /**
     * 播放完成时把进度调回开始的位置
     */
    function audioEnded() {
        document.getElementById('progressBar').style.width = 0;
        document.getElementById('progressDot').style.left = 0;
        document.getElementById('audioCurTime').innerText = transTime(0);
        audioImg.src = '<%=path %>/static/img/play.png';
    }


    // 开始时间文本框失去焦点
    $inputFromAudio.on('blur', function () {
        let fromInputVal = $inputFromAudio.val();
        let val = TimeToSeconds(fromInputVal);
        // validate
        if (isTime(fromInputVal)) { // 验证时间格式
            if (val < minAudio) {
                val = minAudio;
            } else if (val > toValAudio) {
                WarningTips('开始时间超过结束时间！');
                val = minAudio;
                $inputFromAudio.val(SecondsToTime(minAudio));
            }
        } else {
            ErrorTips('时间格式错误！');
            val = minAudio;
            $inputFromAudio.val(SecondsToTime(minAudio));
        }
        $instanceAudio.update({
            from: val
        });
    });

    // 结束时间文本框改变
    /* $inputTo.on("input", function() {
        let inputVal = $inputTo.val();
        isTime(inputVal);
    }); */

    // 结束时间文本框失去焦点
    $inputToAudio.on("blur", function () { // validate
        let toInputVal = $inputToAudio.val();
        let val = TimeToSeconds(toInputVal);
        if (isTime(toInputVal)) {
            if (val < fromValAudio) {
                WarningTips('结束时间小于开始时间！')
                val = maxAudio;
                $inputToAudio.val(SecondsToTime(maxAudio));
            } else if (val > maxAudio) {
                WarningTips('结束时间超过视频时长！');
                val = maxAudio;
                $inputToAudio.val(SecondsToTime(maxAudio));
            }
        } else {
            ErrorTips('时间格式错误！');
            val = maxAudio;
            $inputToAudio.val(SecondsToTime(maxAudio));
        }
        $instanceAudio.update({
            to: val
        });
    });


    // 剪切音频
    function cutAudio() {
        WarningTips('功能待完善^_^');
    }











    //视频剪切==============================================================================
    let $video = $('#videoshow');
    let player = document.querySelector("#videoshow");
    let $range = $("#rangeVideo"),
        $inputFrom = $("#inputFromVideo"),
        $inputTo = $("#inputToVideo"),
        $instance,
        min = 0,
        max = 60,
        fromVal = 0, // 滑块初始值
        toVal = 60; // 滑块结束值
    /**
     * 上传文件本地读取
     * @param {*} files
     */
    function jsReadFiles(files) {
        $video.attr("src", '');
        var file = files[0];
        //(asx|asf|mpg|wmv|3gp|mp4|mov|avi|flv|mkv)
        if (files.length && /(asx|asf|mpg|wmv|3gp|mp4|mov|avi|flv|mkv|mp3)$/.test($("#file")[0].files[0].name.toLowerCase())) {
            if (/(mp4|mp3)$/.test($("#file")[0].files[0].name.toLowerCase())) {
                // 遮罩
                $("#divVideo").busyLoad("show", {
                    text: "上传中...",
                }); // 仅遮罩某元素
                var reader = new FileReader(); //new一个FileReader实例
                reader.onload = function () {
                    $video.attr("src", this.result);
                }
                reader.readAsDataURL(file);
                $('#newFileName').val($("#file")[0].files[0].name);
                // 隐藏遮罩层
                $("#divVideo").busyLoad("hide"); // 仅遮罩某元素

                setTimeout(() => {
                    player.play();
                }, 500);
            } else {
                // 上传到后端转换格式
                $("#divVideo").busyLoad("show", {
                    text: "上传中...",
                }); // 仅遮罩某元素
                var formData = new FormData();
                formData.append('file', $("#file")[0].files[0]);
                // 发送请求
                $.ajax({
                    url: "<%=path %>/video/convertVideo",
                    type: 'post',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (data) {
                        if (data != "") {
                            var fileName = data.substring(data.lastIndexOf("/") + 1, data.length);
                            $('#newFileName').val(fileName);
                            $video.attr("src", data);
                        }
                    },
                    complete: function () {
                        // 隐藏遮罩层
                        $("#divVideo").busyLoad("hide"); // 仅遮罩某元素
                        setTimeout(() => {
                            player.play();
                        }, 500);
                    }
                });
            }

        } else {
            WarningTips('请选择正确的视频文件！');
            setTimeout(() => {
                $("#file")[0].value = '';
            }, 1000);
        }
    }

    // 剪切视频
    function cutVideo() {
        if ($("#file").val()) {
            $video.attr("src", '');
            $("#divVideo").busyLoad("show", {
                text: "裁剪中...",
            }); // 仅遮罩某元素
            var formData = new FormData();
            formData.append('file', $("#file")[0].files[0]);
            formData.append('fileName', $('#newFileName').val());
            formData.append("starTime", $inputFrom.val());
            formData.append("endTime", $inputTo.val());
            // 发送请求
            $.ajax({
                url: "<%=path %>/video/cutVideo",
                type: 'post',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data != "") {
                        var fileName = data.substring(data.lastIndexOf("/") + 1, data.length);
                        $video.attr("src", data);
                        $("#file").val('');
                    }
                },
                complete: function () {
                    // 隐藏遮罩层
                    $("#divVideo").busyLoad("hide"); // 仅遮罩某元素
                    setTimeout(() => {
                        player.play();
                    }, 500);
                }
            });
        } else {
            WarningTips('请先上传视频！');
        }
    }

    $range.ionRangeSlider({
        skin: 'big', //flat,big,modern,sharp,round,square
        type: "double",
        grid: true,
        from: fromVal,
        to: toVal,
        onStart: updateInputs,
        onChange: updateInputs,
        values: ConvertTimeAry(60),
    });
    $instance = $range.data("ionRangeSlider");
    //loadedmetadata ：元数据加载。当指定的音频/视频的元数据已加载时触发，元数据包括：时长、尺寸（仅视频）以及文本轨道
    $video.on('loadedmetadata', function (e) {
        let duartion = parseInt(this.duration); // 当前视频总时长
        if (duartion > 86400) {
            // 视频时长大于24小时
            return;
        }
        $instance.update({
            from: 0,
            to: duartion,
            values: ConvertTimeAry(duartion)
        });

        $inputFrom.val($instance.result.from_value);
        $inputTo.val($instance.result.to_value);
        max = duartion;

        if (player.duration) {
            $('#showVideoPlayTime').html(SecondsToTime(player.currentTime) + '/' + SecondsToTime(player.duration));
        }
    });

    //ended播放结束
    $video.on('ended', function (e) {
    });

    // 目前的播放位置已更改时，播放时间更新
    $video.on('timeupdate', function (e) {
        if (player.duration) {
            $('#showVideoPlayTime').html(SecondsToTime(player.currentTime) + '/' + SecondsToTime(player.duration));
        }
    });

    // 滑块移动是更新文本框的值
    function updateInputs(data) {
        fromVal = data.from;
        toVal = data.to;
        $inputFrom.val(data.from_value);
        $inputTo.val(data.to_value);
        if (player.duration) {
            player.currentTime = fromVal;
            setTimeout(() => {
                player.play();
            }, 100);
        }
    }

    // 开始时间文本框值改变
    /* $inputFrom.on('input', function() {
        var inputVal = $(this).val();
        isTime(inputVal);
    }); */
    // 开始时间文本框失去焦点
    $inputFrom.on('blur', function () {
        let fromInputVal = $inputFrom.val();
        let val = TimeToSeconds(fromInputVal);
        // validate
        if (isTime(fromInputVal)) { // 验证时间格式
            if (val < min) {
                val = min;
            } else if (val > toVal) {
                WarningTips('开始时间超过结束时间！');
                val = min;
                $inputFrom.val(SecondsToTime(min));
            }
        } else {
            ErrorTips('时间格式错误！');
            val = min;
            $inputFrom.val(SecondsToTime(min));
        }
        $instance.update({
            from: val
        });
    });

    // 结束时间文本框改变
    /* $inputTo.on("input", function() {
        let inputVal = $inputTo.val();
        isTime(inputVal);
    }); */

    // 结束时间文本框失去焦点
    $inputTo.on("blur", function () { // validate
        let toInputVal = $inputTo.val();
        let val = TimeToSeconds(toInputVal);
        if (isTime(toInputVal)) {
            if (val < fromVal) {
                WarningTips('结束时间小于开始时间！')
                val = max;
                $inputTo.val(SecondsToTime(max));
            } else if (val > max) {
                WarningTips('结束时间超过视频时长！');
                val = max;
                $inputTo.val(SecondsToTime(max));
            }
        } else {
            ErrorTips('时间格式错误！');
            val = max;
            $inputTo.val(SecondsToTime(max));
        }
        $instance.update({
            to: val
        });
    });


    //  公共函数================================================================================================
    // 封装alert
    function ShowTips(content) {
        let o = jqueryAlert({
            'icon': '<%=path %>/static/img/right.png',
            'content': content,
            'closeTime': 1000,
            // 'modal':true,
        });
    }

    function WarningTips(content) {
        let o = jqueryAlert({
            'icon': '<%=path %>/static/img/warning.png',
            'content': content,
            'closeTime': 1000,
            // 'modal':true,
        });
    }

    function ErrorTips(content) {
        jqueryAlert({
            'icon': '<%=path %>/static/img/error.png',
            'content': content,
            'closeTime': 1000,
        });
    }
</script>
