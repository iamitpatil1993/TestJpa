package com.example.pojo;

public class Document {

	private String fileName;
	private String filePath;
	private String fileExtension;
	private byte[] bytes;
	private Long fileSize;
	private String contentType;	
	
	public Document(String fileName, String filePath, String fileExtension,
			byte[] bytes, Long fileSize, String contentType) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileExtension = fileExtension;
		this.bytes = bytes;
		this.fileSize = fileSize;
		this.contentType = contentType;
	}
	
	public Document() {
		super();
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
