package com.controldesktop;

import com.alibaba.fastjson.JSON;

import java.sql.*;

class IRead{
    private String title;
    private String body;
    private String cTime;
    private long mTime;
    private int readType;
    private String readNotice;
    private String label;
    private int type;
    private int readNum;
    public String toAndString(){
        return "title='" + title + '\'' +
                " and body='" + body + '\'' +
                " and cTime='" + cTime + '\'' +
                " and mTime=" + mTime +
                " and readType=" + readType +
                " and readNotice='" + readNotice + '\'' +
                " and label='" + label + '\'' +
                " and type=" + type +
                " and readNum=" + readNum ;
    }

    public String toCommaString(){
        return  "title='" + title + '\'' +
                ",body='" + body + '\'' +
                ",cTime='" + cTime + '\'' +
                ",mTime=" + mTime +
                ",readType=" + readType +
                ",readNotice='" + readNotice + '\'' +
                ",label='" + label + '\'' +
                ",type=" + type +
                ",readNum=" + readNum;
    }
    public String toJSON() {
        return "{" +
                "\"title\":\"" + title + '\"' +
                ",\"body\":\"" + body + '\"' +
                ",\"cTime\":\"" + cTime + '\"' +
                ",\"mTime\":" + mTime +
                ",\"readType\":" + readType +
                ",\"readNotice\":\"" + readNotice + '\"' +
                ",\"label\":\"" + label + '\"' +
                ",\"type\":" + type +
                ",\"readNum\":" + readNum +
                '}';
    }


    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getcTime() {
        return cTime;
    }

    public long getmTime() {
        return mTime;
    }

    public int getReadType() {
        return readType;
    }

    public String getReadNotice() {
        return readNotice;
    }

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public void setReadNotice(String readNotice) {
        this.readNotice = readNotice;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public void setReadType(int readType) {
        this.readType = readType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

}

class LinkToMySQL{
    Connection con;
    Statement stat;
    ResultSet resultSet;
    public LinkToMySQL() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/analizepy","root","nichenyang");
        stat = con.createStatement();
    }
    public void CloseAll(){
        try{
            con.close();
            stat.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

class StartToMySQL extends LinkToMySQL{
    public StartToMySQL() throws SQLException, ClassNotFoundException {
        super();
    }
    public IRead getResult(String value) throws SQLException {
        resultSet = stat.executeQuery("SELECT * FROM iread WHERE title='"+value+"'");
        IRead iRead = new IRead();
        while (resultSet.next()){
            iRead.setTitle(resultSet.getString("title"));
            iRead.setBody(resultSet.getString("body"));
            iRead.setcTime(resultSet.getString("ctime"));
            iRead.setmTime(resultSet.getLong("mtime"));
            iRead.setReadType(resultSet.getInt("readtype"));
            iRead.setReadNotice(resultSet.getString("readnotice"));
            iRead.setLabel(resultSet.getString("label"));
            iRead.setType(resultSet.getInt("type"));
            iRead.setReadNum(resultSet.getInt("readnum"));
        }
        return iRead;
    }
    public void updateIRead(IRead newIRead,IRead oldIRead) throws SQLException {
        stat.executeUpdate("UPDATE iread SET "+newIRead.toCommaString()+" WHERE " + oldIRead.toAndString());
    }

    public void Close(){
        super.CloseAll();
    }

}

public class MySQLTest {
    /*
    * 要懂得包装的好处，在包装完数据库方法后可以方便的执行增删改查功能，目前仅写了查询和更新，仅通过实例化包装iread和连接数据库就可以实现了。
    *
    *
    *
    * */
    public MySQLTest() throws SQLException, ClassNotFoundException {
        StartToMySQL stom = new StartToMySQL();
        IRead oldIRead = stom.getResult("Nichenyang");
        String value = JSON.toJSONString(oldIRead);
        System.out.println(value);
        System.out.println(oldIRead.toJSON());
        stom.Close();

//        System.out.println(iRead.toString());
//        System.out.println("-------------------------------------------------------------");
//        String json_value = JSON.toJSONString(iRead);
//        System.out.println(json_value);
//        System.out.println("-------------------------------------------------------------");

    }
}
