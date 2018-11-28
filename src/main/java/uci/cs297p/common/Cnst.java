package uci.cs297p.common;

public class Cnst {
    public static final String CURRENT_USER = "CURRENT_USER";
/*    public enum Role{
        ROLE_CUSTOMER(0, "CUSTOMER"), ROLE_ADMIN(1, "ADMIN");
        private final int code;
        private final String desc;
        Role(int code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public int getCode(){ return code;}
        public String getDesc(){ return desc;}
    }*/

    public interface Role{
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public interface CollectionStatus{
        int COLLECTION_FALSE = 0;
        int COLLECTION_TRUE = 1;
    }
}
