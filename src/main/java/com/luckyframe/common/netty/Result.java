package com.luckyframe.common.netty;

import java.io.Serializable;


public class Result implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;
    private Object message;
    private String uniId;
    private FileUploadFile fileUploadFile;
    public Result(int code, Object message, String uniId,FileUploadFile fileUploadFile) {
        this.code = code;
        this.message = message;
        this.uniId = uniId;
        this.fileUploadFile=fileUploadFile;
    }

    public FileUploadFile getFileUploadFile() {
        return fileUploadFile;
    }

    public void setFileUploadFile(FileUploadFile fileUploadFile) {
        this.fileUploadFile = fileUploadFile;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getUniId() {
        return uniId;
    }

    public void setUniId(String uniId) {
        this.uniId = uniId;
    }
}
