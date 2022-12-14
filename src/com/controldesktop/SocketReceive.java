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
                            ipAdd = hm.getIpInfo()[0];//??????????????????IP
                            ControlFunction.setReceiveMessage(jta,"??????"+ipAdd+"???????????????:"+hm.getValue()[0]);
                            ControlFunction.showDialog("??????"+ipAdd+"???????????????:"+hm.getValue()[0]);
                            //JOptionPane.showMessageDialog(null,"??????"+ipAdd+"???????????????:"+hm.getValue()[0]);
                            break;
                        case "RETURN_GET_MY_IP":
                            ControlFunction.setReceiveMessage(jta,hm.getIpInfo()[0]);
                            //JOptionPane.showMessageDialog(null,"??????IP?????????:"+hm.getIpInfo()[0]);
                            ControlFunction.showDialog("??????IP?????????:"+hm.getIpInfo()[0]);
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
                            ipAdd = hm.getIpInfo()[0];//??????????????????IP
                            ControlFunction.setReceiveMessage(jta,"?????????"+ipAdd+"?????????");
                            ControlFunction.showDialog("?????????"+ipAdd+"?????????");
                            //JOptionPane.showMessageDialog(null,"?????????"+ipAdd+"?????????");
                            break;
                        case "RETURN_EXECUTE_CMD":
                            ControlFunction.setReceiveMessage(jta,hm.getValue()[0]);
                            break;
                        case "RETURN_NEW_ARTICLE":
                            //?????????????????????????????????
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
                            ControlFunction.setReceiveMessage(jta,"??????????????????");
                            break;
                        default:
                            ControlFunction.setReceiveMessage(jta,hm.getType()+" > "+hm.getValue()[0]);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                ControlFunction.setReceiveMessage(jta,"?????????????????????"+e);
                JOptionPane.showMessageDialog(null,"?????????????????????"+e);
                break;
            }
        }
    }
}
