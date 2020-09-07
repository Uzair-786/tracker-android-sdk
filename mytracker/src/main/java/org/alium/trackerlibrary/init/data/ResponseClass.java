package org.alium.trackerlibrary.init.data;

public class ResponseClass {

    private int code;
    private String message;
    private DataClass data;
    private Boolean success;

    public ResponseClass() {
    }

    public ResponseClass(int code, String message, DataClass data, Boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataClass getData() {
        return data;
    }

    public void setData(DataClass data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    @Override
    public String toString() {
        return "ResponseClass{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }
}
