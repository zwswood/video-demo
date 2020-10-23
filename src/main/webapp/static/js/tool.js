
//============================工具方法==============================
// 将时间格式转为具体的秒：00:01:01==>61
function TimeToSeconds(value) {
    let arr = value.split(':');
    let hs = Number(arr[0]) * 3600;
    let ms = Number(arr[1]) * 60;
    let ss = Number(arr[2]);
    let seconds = hs + ms + ss;
    return seconds;
}

// 验证输入的时间格式
function isTime(str) {
    var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
    if (a == null) {
        return false;
    }
    if (a[1] > 24 || a[3] > 60 || a[4] > 60) {
        return false
    }
    return true;
}

// 秒转换为时间格式字符串数组 2==>['00:00:00','00:00:01','00:00:02']
function ConvertTimeAry(value) {
    let times = [];
    let count = parseInt(value); // 当前总秒数
    for (let i = 0; i <= count; i++) {
        times.push(SecondsToTime(i));
    }
    return times;
}

// 秒转换为00:00:00的格式：2==>'00:00:02'
function SecondsToTime(value) {
    var theTime = parseInt(value); // 秒
    var theTime1 = 0; // 分
    var theTime2 = 0; // 小时
    if (theTime >= 60) {
        theTime1 = parseInt(theTime / 60);
        theTime = parseInt(theTime % 60);
        if (theTime1 >= 60) {
            theTime2 = parseInt(theTime1 / 60);
            theTime1 = parseInt(theTime1 % 60);
        }
    }
    var result = "" + parseInt(theTime); //秒
    if (10 > theTime > 0) {
        result = "0" + parseInt(theTime); //秒
    } else {
        result = "" + parseInt(theTime); //秒
    }
    if (10 > theTime1 > 0) {
        result = "0" + parseInt(theTime1) + ":" + result; //分，不足两位数，首位补充0，
    } else {
        result = "" + parseInt(theTime1) + ":" + result; //分
    }
    if (10 > theTime2 > 0) {
        result = "0" + parseInt(theTime2) + ":" + result; //时,不足两位数，首位补充0
    } else {
        result = "" + parseInt(theTime2) + ":" + result; //时
    }
    return result;
}