package org.example.exception;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/15  14:05
 */
public class ExceedException extends RuntimeException{

    String message;

    public ExceedException(){
        super();
    }

    public ExceedException(String msg){
        super();
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
