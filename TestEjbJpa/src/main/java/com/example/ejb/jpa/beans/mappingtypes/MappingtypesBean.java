package com.example.ejb.jpa.beans.mappingtypes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.mappingtypes.MappingTypes;
import com.example.ejb.jpa.utils.Gender;
import com.example.ejb.jpa.utils.User;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MappingtypesBean {

private final static Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(MappingtypesBean.class);
	}
	
	@PersistenceContext(unitName="JPADB")
	EntityManager em;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create() throws IOException {
		/*
		Primitive Java types: byte, int, short, long, boolean, char, float, double
		• Wrapper classes of primitive Java types: Byte, Integer, Short, Long, Boolean,
		Character, Float, Double
		• Byte and character array types: byte[], Byte[], char[], Character[]
		• Large numeric types: java.math.BigInteger, java.math.BigDecimal
		• Strings: java.lang.String
		• Java temporal types: java.util.Date, java.util.Calendar
		• JDBC temporal types: java.sql.Date, java.sql.Time, java.sql.TimestampCHAPTER 4 ■ OBJECT-RELATIONAL MAPPING
		• Enumerated type*/
		MappingTypes mappingTypes = new MappingTypes();
		
		mappingTypes.setBuildBoolean(true);
		mappingTypes.setBuildByte((byte)21);
		mappingTypes.setBuildDouble(23123.32);
		mappingTypes.setBuildFloat(2342343.23f);
		mappingTypes.setBuildInt(3123);
		mappingTypes.setBuildLong(312312l);
		mappingTypes.setBuiltChar('3');
		mappingTypes.setBuildShort((short)23);
		
		
		
		mappingTypes.setWrapperBoolean(false);
		mappingTypes.setWrapperByte((byte)32);
		mappingTypes.setWrapperChar('a');
		mappingTypes.setWrapperDouble(24234.34);
		mappingTypes.setWrapperFloat(32323.23f);
		mappingTypes.setWrapperLong(3423l);
		mappingTypes.setWrapperShort((short)232);
		
		
		/*//load image frile from resources folder;
		File file = loadResourceFromPath("file.png");
		LOGGER.info("file : " + file.getPath() + " size : " + file.isHidden());
		
		byte[] imageInByte=Files.readAllBytes(file.toPath());
		
		mappingTypes.setImage(imageInByte);*/
		
		mappingTypes.setBigInteger(new BigInteger("212312312312"));
		mappingTypes.setBigDecimal(new BigDecimal("4324234.343"));
		
		mappingTypes.setString("amit");
		mappingTypes.setCalendar(Calendar.getInstance());
		mappingTypes.setEnumeratedOrdinal(Gender.MALE);
		mappingTypes.setEnumeratedString(Gender.FEMALE);
		
		
		User user = new User("amir", Calendar.getInstance().getTime());
		mappingTypes.setSerializable(user);
		
		em.persist(mappingTypes);
		
		LOGGER.info("MappingtypesBean id : " + mappingTypes.getId());
		
	}
	
	
	private File loadResourceFromPath(String path) {
		
		LOGGER.info("Insdie loadResourceFromPath : " + path);
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(path).getFile());
	}
	
	public void read(Integer id) {
		
		if(null != id) {
			
			MappingTypes mappingTypes = em.find(MappingTypes.class, id);
			if(mappingTypes != null) {
				
				LOGGER.info("mappingTYpes : " + mappingTypes);
			}
			else {
				LOGGER.info("mappingTYpes not found for id " + id);
			}
				
		}
		else {
			LOGGER.info("Id not provided");
		}
			
	}
}
