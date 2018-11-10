package uci.cs297p.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;
    public ServerResponse(int status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @JsonIgnore
    public boolean isSucc(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static<T> ServerResponse<T> succWithMsgData(String msg, T data){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static<T> ServerResponse<T> succWithMsg(String msg){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static<T> ServerResponse<T> failWithMsg(String msg){
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static<T> ServerResponse<T> failWithCodeMsg(int code, String msg){
        return new ServerResponse<>(code, msg, null);
    }
}
