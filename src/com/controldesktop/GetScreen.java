package com.controldesktop;

import org.bytedeco.javacv.CanvasFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

class Head{
    int length;
    byte[] value;
    public Head(int length,byte[] value){
        this.value=value;
        this.length=length;
    }

    public int getLength() {
        return length;
    }
    public byte[] getValue() {
        return value;
    }
}


class StartThead extends Thread{
    public volatile boolean exits = true;
    DatagramSocket DSocket;
    BufferedImage image;
    CanvasFrame frame = new CanvasFrame("屏幕");// javacv提供的图像展现窗口
    int width = 1000;
    int height = 600;
    byte[] datas;

    int length;
    public StartThead(DatagramSocket DSocket){
        this.DSocket = DSocket;
        frame.setBounds((int) (1536 - width) / 2, (int) (864 - height) / 2, width,
                height);// 窗口居中
        frame.setCanvasSize(width, height);// 设置CanvasFrame窗口大小

        //ByteArrayOutputStream bout = new ByteArrayOutputStream();

        datas = new byte[100000];

    }



    @Override
    public void run() {
        while (exits) {
            //ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ByteArrayInputStream bin;
            //DataInputStream dis;
            DatagramPacket packet;
            try {
                packet = new DatagramPacket(datas,datas.length, InetAddress.getByName("127.0.0.1"),9575);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }

            //System.out.println("1");
//            try {
//                DSocket.receive(packet);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            //length = 0;
            //System.out.println(length);

//            try {
//                while ((length = dis.read(datas)) != -1) {
//                    //System.out.println(length);
//                    bout.write(datas, 0, length);
//                    if (length < 100000){
//                        break;
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//                frame.dispose();
//                JOptionPane.showMessageDialog(null,"桌面已结束");
//                exits = false;
//                break;
//            }

            if (!exits){
                break;
            }
            System.out.println(exits);
            try {
                DSocket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bin = new ByteArrayInputStream(packet.getData(),0, packet.getLength());

            try {
                image = ImageIO.read(bin);
                //System.out.println(image);
                frame.showImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //这里不断获取socket发来的数据包
            // 从当前屏幕中读取的像素图像，该图像不包括鼠标光标
            //System.out.println(5);
            try {
                Thread.sleep(50);
                //System.out.println(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class GetScreen {
    public GetScreen() throws IOException {
        DatagramSocket DSocket = new DatagramSocket(9575);
        new StartThead(DSocket).start();
    }
}
