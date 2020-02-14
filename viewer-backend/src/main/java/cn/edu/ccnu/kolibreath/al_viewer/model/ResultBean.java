package cn.edu.ccnu.kolibreath.al_viewer.model;

public class  ResultBean<T> {
    private T data;
    private String message;
    private int code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static <T> ResultBean success(T data){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(200);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return resultBean;
    }

    public static  ResultBean error(int code, String message){
        ResultBean resultBean= new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        resultBean.setData(null);
        return resultBean;
    }
}

