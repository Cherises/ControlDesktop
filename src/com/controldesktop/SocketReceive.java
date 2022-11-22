package com.controldesktop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

public class SocketReceive extends Thread{
    private Socket socket;
    JTextArea jta;

    JList<String> ArticalList;

    JComboBox<String> comBox;
    public SocketReceive(Socket socket , JTextArea jta , JList<String> ArticalList, JComboBox<String> comBox){
        this.socket = socket;
        this.jta = jta;
        this.ArticalList = ArticalList;
        this.comBox = comBox;
    }

    @Override
    public void run() {
        while (true){
            try {
                InputStream is = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ObjectInputStream ois = new ObjectInputStream(bis);
                Object obj = ois.readObject();

                String ipAdd;
                if (obj != null){
                    HeadMessage hm = (HeadMessage) obj;
                    switch (hm.getType()){
                        case "RETURN_GET_ARTICLE_LIST":
                            ArticalList.removeAll();
                            String[] list = hm.getValue();
                            ArticalList.setListData(list);
                            break;
                        case "RETURN_DOWNLOAD_FILE":
                            new DownLoadFile(hm);
                        case "RETURN_LIST_PATH":
                            try {
                                String[] FilePath = hm.getValue();
                                //System.out.println(Arrays.toString(FilePath));
                                if (FilePath != null) {
                                    //System.out.println(Arrays.toString(FilePath));
                                    ArticalList.removeAll();
                                    ArticalList.setListData(FilePath);
                                    ArticalList.setName(hm.getFileInfo()[1]);
                                    //System.out.println(ArticalList.getName());
                                }
                            }catch (Exception e){
                                ControlFunction.showDialog(e.toString());
                                //throw new RuntimeException(e);
                                //JOptionPane.showMessageDialog(null,e.toString());
                            }
                            break;
                        case "RETURN_ERROR_MESSAGE":
                            ipAdd = hm.getIpInfo()[0];//获取发送端的IP
                            ControlFunction.setReceiveMessage(jta,"来自"+ipAdd+"的报错信息:"+hm.getValue()[0]);
                            ControlFunction.showDialog("来自"+ipAdd+"的报错信息:"+hm.getValue()[0]);
                            //JOptionPane.showMessageDialog(null,"来自"+ipAdd+"的报错信息:"+hm.getValue()[0]);
                            break;
                        case "RETURN_GET_MY_IP":
                            ControlFunction.setReceiveMessage(jta,hm.getIpInfo()[0]);
                            //JOptionPane.showMessageDialog(null,"你的IP地址为:"+hm.getIpInfo()[0]);
                            ControlFunction.showDialog("你的IP地址为:"+hm.getIpInfo()[0]);
                            break;
                        case "RETURN_CLIENT_IP":
                            String[] value = hm.getValue();
                            //comBox.removeAll();
                            comBox.setModel(new DefaultComboBoxModel<>(value));
                            comBox.addItem("114.115.153.152");
                            ControlFunction.setReceiveMessage(jta, Arrays.toString(value));
                            //ControlFunction.showDialog(hm.getValue()[0]);
                            //JOptionPane.showMessageDialog(null,hm.getValue());
                            break;
                        case "RETURN_GET_SCREEN":
                            byte[] data = hm.getFileToByte();
                            ByteArrayInputStream bais = new ByteArrayInputStream(data);
                            BufferedImage bi = ImageIO.read(bais);
                            ControlFunction.showImgWindow(bi,hm.getIpInfo()[1]);
                            break;
                        case "NEW_CLIENT":
                            ipAdd = hm.getIpInfo()[0];//获取发送端的IP
                            ControlFunction.setReceiveMessage(jta,"客户端"+ipAdd+"已上线");
                            ControlFunction.showDialog("客户端"+ipAdd+"已上线");
                            //JOptionPane.showMessageDialog(null,"客户端"+ipAdd+"已上线");
                            break;
                        case "RETURN_EXECUTE_CMD":
                            ControlFunction.setReceiveMessage(jta,hm.getValue()[0]);
                            break;
                        case "RETURN_NEW_ARTICLE":
                            //新文章执行后的返回结果
                            ControlFunction.setReceiveMessage(jta,hm.getValue()[0]);
                            ControlFunction.showDialog(hm.getValue()[0]);
                            //JOptionPane.showMessageDialog(null,hm.getValue()[0]);
                            break;
                        case "RETURN_CHAT":
                            ipAdd = hm.getIpInfo()[0];
                            String message = hm.getValue()[0];
                            ControlFunction.setReceiveMessage(jta,ipAdd+" : " +message);
                            break;
                        case "CONFIRM":
                            ControlFunction.setReceiveMessage(jta,"收到超时验证");
                            break;
                        default:
                            ControlFunction.setReceiveMessage(jta,hm.getType()+" > "+hm.getValue()[0]);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                ControlFunction.setReceiveMessage(jta,"接收线程已关闭"+e);
                JOptionPane.showMessageDialog(null,"接收线程已关闭"+e);
                break;
            }
        }
    }
}
