package com.example.ejb.jpa.beans.lobs;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.lobs.LargeObjectEntity;
import com.example.pojo.Document;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LargeObjectTestBean {

	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(LargeObjectTestBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer uploadDocument(Document document) {

		Integer docId = null;
		if(document != null) {

			LargeObjectEntity largeObjectEntity = new LargeObjectEntity(document.getBytes(), document.getFileName(), document.getFilePath(), document.getFileExtension(), document.getFileSize(), document.getContentType());
			em.persist(largeObjectEntity);

			docId = largeObjectEntity.getId();
			LOGGER.info("image saved successfully : " + largeObjectEntity.getId());
		}
		return docId;
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Document getDocument(Integer docId) {
		
		Document document = null;
		
		//here it is expected that image attribute should not be in select clause since it will be lazily fetched only when explicitly accessed
		//so here persistence privider will execute 1st select query
		LargeObjectEntity entity = em.find(LargeObjectEntity.class, docId);
		
		if(null != entity) {
			LOGGER.info("document found with id " + docId);
			
			//at this point we are accessing document image attribute, so it is expected that persistence provider will fetch image data at this point lazily.
			//so here persistence provider will execute 2nd select query
			document = new Document(entity.getFileName(), entity.getFilePath(), entity.getFileExtension(), entity.getImage(), entity.getFileSize(), entity.getContentType());
		} else {
			LOGGER.info("document not found. Invalid Doc Id" + docId);
		}
		return document;
	}

}
