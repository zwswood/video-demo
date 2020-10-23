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
    <title>视频编辑</title>
</head>
<body>
<div style="margin: 0 auto; width: 1500px;">
    <fieldset style="border: 2px solid blueviolet;padding:0 50px;">
        <legend id="h2" style="color: blueviolet">上传视频</legend>
        <div style="margin:10px 5px;">
            <form id="form" enctype="multipart/form-data">
                <label>选择视频<b style="color: red;">* </b></label>
                <input type="file" id="file" onchange="jsReadFiles(this.files)"/>
                <%----%>
                <input id="newFileName" type="hidden"/>
            </form>
        </div>
    </fieldset>
    <fieldset style="margin-top: 20px;border: 2px solid blueviolet;padding:0 50px;">
        <legend id="show" style="color:blueviolet">视频预览</legend>
        <div id="divVideo" style="margin:10px 5px; width: 1080px;">
            <video id="videoshow" controls="controls" style="width: 1080px; border: 1px solid #428BCA;">
                <!-- controls="controls" -->
            </video>
            <div>
                <div class="range-slider" id="sliderCtr" style="width: 1080px;">
                    <input type="text" class="js-range-slider" value=""/>
                </div>
                <div class="extra-controls">
                    <input type="text" class="js-input-from" style="width: 120px;" value="0"/>
                    —
                    <input type="text" class="js-input-to" style="width: 120px;" value="0"/>
                    <span id="showPlayTime"></span>
                </div>
                <input id="btn" type="button" onclick="cutVideo();" value="裁剪"/>
            </div>
        </div>
    </fieldset>
</div>
</body>
</html>

<script>
    let $video = $('#videoshow');
    let player = document.querySelector("#videoshow");
    let $range = $(".js-range-slider"),
        $inputFrom = $(".js-input-from"),
        $inputTo = $(".js-input-to"),
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
                    }
                });
            }

        } else {
            jqueryAlert({
                'icon': '<%=path %>/static/img/warning.png',
                'content': '请选择正确的视频文件！',
                'closeTime': 800,
            });
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
                    }, 1000);
                }
            });
        } else {
            jqueryAlert({
                'icon': '<%=path %>/static/img/warning.png',
                'content': '请先上传视频！',
                'closeTime': 800,
            });
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
            $('#showPlayTime').html(SecondsToTime(player.currentTime) + '/' + SecondsToTime(player.duration));
        }
    });

    //ended播放结束
    $video.on('ended', function (e) {
    });

    // 目前的播放位置已更改时，播放时间更新
    $video.on('timeupdate', function (e) {
        if (player.duration) {
            $('#showPlayTime').html(SecondsToTime(player.currentTime) + '/' + SecondsToTime(player.duration));
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
                jqueryAlert({
                    'icon': '<%=path %>/static/img/warning.png',
                    'content': '开始时间超过结束时间！',
                    'closeTime': 1000,
                });
                val = min;
                $inputFrom.val(SecondsToTime(min));
            }
        } else {
            jqueryAlert({
                'icon': '<%=path %>/static/img/error.png',
                'content': '时间格式错误！',
                'closeTime': 1000,
            });
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
                jqueryAlert({
                    'icon': '<%=path %>/static/img/warning.png',
                    'content': '结束时间小于开始时间！',
                    'closeTime': 1000,
                });
                val = max;
                $inputTo.val(SecondsToTime(max));
            } else if (val > max) {
                jqueryAlert({
                    'icon': '<%=path %>/static/img/warning.png',
                    'content': '结束时间超过视频时长！',
                    'closeTime': 1000,
                });
                val = max;
                $inputTo.val(SecondsToTime(max));
            }
        } else {
            jqueryAlert({
                'icon': '<%=path %>/static/img/error.png',
                'content': '时间格式错误！',
                'closeTime': 1000,
            });
            val = max;
            $inputTo.val(SecondsToTime(max));
        }
        $instance.update({
            to: val
        });
    });


    // 扩展实现StringBuffer
    function StringBuffer() {
        this._str_ = new Array();
    }

    StringBuffer.prototype.append = function (str) {
        this._str_.push(str);
        return this;
    }

    StringBuffer.prototype.toString = function () {
        return this._str_.join("");
    }


    function checkType(ths) {
        if (ths.value == "") {
            alert("请上传图片");
            return false;
        } else {
            if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(ths.value)) {
                alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
                ths.value = "";
                return false;
            }
        }
        return true;
    }
</script>
