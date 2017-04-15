package com.example.ejb.jpa.entities.lobs;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name="lob_test")
public class LargeObjectEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//lazily fetching lob object for performance boost
	//check the queries generated by persistence provider
	//when we get object then it is expected that image is is not in selec clause and 
	//when we try to access image attribute then it is expected that persistence provider will fetch the image data by another query.
	//for basic type of mappings, Basic annotation is only way to specify fetch strategy. For relationships, there is fetch type element in 
	//mapping annotations. 
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="image")
	private byte[] image;

	@Basic
	@Column(name="file_name")
	private String fileName;
	
	@Basic
	@Column(name="file_path")
	private String filePath;
	
	@Basic
	@Column(name="file_extension")
	private String fileExtension;
	
	@Basic
	@Column(name="file_size")
	private Long fileSize;
	
	@Basic
	@Column(name="content_type")
	private String contentType;
	
	public LargeObjectEntity(byte[] image, String fileName, String filePath,
			String fileExtension, Long fileSize, String contentType) {
		super();
		this.image = image;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileExtension = fileExtension;
		this.fileSize = fileSize;
		this.contentType = contentType;
	}
	
	

	public LargeObjectEntity() {
		super();
		//Nothing to do here
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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
