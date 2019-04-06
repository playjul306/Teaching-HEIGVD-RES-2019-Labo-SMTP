package model.mail;

import java.util.List;

public class Message {

    private String from;
    private List<String> rcptTo;
    private List<String> copyCarbon;
    private String subject;
    private String data;

    //--------------Getter---------------

    public String getFrom(){
        return from;
    }

    public String getSubject(){
        return subject;
    }

    public String getData(){
        return data;
    }

    public List<String> getRcptTo(){
        return rcptTo;
    }

    public List<String> getCopyCarbon(){
        return copyCarbon;
    }

    //--------------Setter---------------

    public void setFrom(String from){
        this.from = from;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setRcptTo(List<String> rcptTo){
        this.rcptTo = rcptTo;
    }

    public  void setCopyCarbon(List<String> copyCarbon){
        this.copyCarbon = copyCarbon;
    }
}
