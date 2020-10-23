package com.linrun.ssm.entity.bo;

import lombok.Data;

/**
 * 描述    :
 * 作者    : lyp
 * 创建时间: 2020-06-08
 */
@Data
public class ProtocolFrame {
    private int pfHeader;      //帧头   4B 不包括帧长度
    private short pfLength;    //帧长度 2B
    private long pfSerial;     //序列号 8B 标识指令唯一编码
    private byte pfSign;       //帧标识 1B 指令类型
    private byte pfCmd;        //命令号 1B
    private byte pfVersion;    //版本   1B
    private byte pfSend;       //发送方 1B 0x10: 信号机控制机；0x11: 智能机柜；0x20: 上位机（平台）；0x30: UI
    private byte pfReceive;    //接收方 1B 同上
    private byte pfArea;       //区域码 1B 取值0
    private int pfAddr;        //地址码 4B 信号机控制机或智能机柜的设备编号
    private int pfTime;        //时间戳 4B 当前距格林尼治时间1970年1月1日零0以来的秒数
    private byte[] pfInfo;     //信息码 NB
}
