package uci.cs297p.common;

public enum ResponseCode {
    SUCCESS(0, "SUCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARG(2, "ILLEGAL_ARG");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){ return code;}
    public String getDesc(){ return desc;}
}
