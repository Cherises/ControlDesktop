package com.controldesktop;

import org.bytedeco.javacv.CanvasFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


//此类用于存放所有多次使用的函数体
public class ControlFunction {
    //将收到的信息写入到文本域中（加上时间）
    public static void setReceiveMessage(JTextArea jta,String message){
        SimpleDateFormat formatter = new SimpleDateFormat("\nyyyy-MM-dd HH:mm:ss > ");
        Date date = new Date(System.currentTimeMillis());
        String nowTime = formatter.format(date);
        jta.append(nowTime+message);
    }

    //发送报头信息函数
    public static void sendHeadMessage(HeadMessage hm, Socket socket, JTextArea jta){
        try {
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(hm);
            oos.flush();
        }catch (Exception e){
            setReceiveMessage(jta,e.toString());
        }
    }

    public static void showImgWindow(BufferedImage bi, String ip){
        CanvasFrame frame = new CanvasFrame(ip+"的屏幕");
        frame.setCanvasSize(800, 600);// 设置CanvasFrame窗口大小
        frame.showImage(bi);
    }

    public static String escapeSymbol(String value){
        value = value.replaceAll("\\\\","\\\\\\\\");
        value = value.replaceAll("\"","\\\\\"");
        value = value.replaceAll("'", "\\\\'");
        value = value.replaceAll("%","\\%");
        value = value.replaceAll("_","\\_");
        value = value.replaceAll("\n","\\\\\\n");
        return value;
    }
    public static void showDialog(String message){
        class showDia extends Thread{
            String message;
            public showDia(String message){
                this.message = message;
            }

            @Override
            public void run() {
                JOptionPane.showMessageDialog(null,message);
            }
        }
        new showDia(message).start();
    }

}
