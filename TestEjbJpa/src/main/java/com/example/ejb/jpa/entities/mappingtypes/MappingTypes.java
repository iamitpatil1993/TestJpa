package com.example.ejb.jpa.entities.mappingtypes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.ejb.jpa.utils.Gender;
import com.example.ejb.jpa.utils.User;
/*
 * 
* Created this entity to check how Schema-AutoGeneration maps java types to mysql types. To test turn on auto schema generation to update or create.
*
*/
@Entity
public class MappingTypes implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//Primitive Java types:
	@Basic
	private int buildInt;
	
	@Basic
	private short buildShort;
	
	@Basic
	private long buildLong;
	
	@Basic
	private byte buildByte;
	
	@Basic
	private float buildFloat;
	
	@Basic
	private double buildDouble;
	
	@Basic
	private char builtChar;
	
	@Basic
	private boolean buildBoolean;

	
	//Wrapper classes of primitive Java typesprivate Integer wrapperInt;
	@Basic
	private Short wrapperShort;
	
	@Basic
	private Long wrapperLong;
	
	@Basic
	private Byte wrapperByte;
	
	@Basic
	private Float wrapperFloat;
	
	@Basic
	private Double wrapperDouble;
	
	@Basic
	private Character wrapperChar;
	
	@Basic
	private Boolean wrapperBoolean;
	
	
	//Byte and character array types:
	@Basic
	@Lob
	private byte[] image;
	
	@Basic
	@Lob
	private char[] resume;
	
	
	//Large numeric types:
	@Basic
	private BigInteger bigInteger;
	
	@Basic
	private BigDecimal bigDecimal;
	
	
	//String
	@Basic
	private String string;
	
	
	//Java temporal types
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar calendar;
	
	//Serializable object
	@Basic
	private User serializable;
	
	//Enumerated
	//EnumType.ORDINAL is Default, so no need to specify here
	@Basic
	@Enumerated
	private Gender enumeratedOrdinal;
	
	@Basic
	@Enumerated(EnumType.STRING)
	private Gender enumeratedString;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//getters and setters
	
	public int getBuildInt() {
		return buildInt;
	}

	public void setBuildInt(int buildInt) {
		this.buildInt = buildInt;
	}

	public short getBuildShort() {
		return buildShort;
	}

	public void setBuildShort(short buildShort) {
		this.buildShort = buildShort;
	}

	public long getBuildLong() {
		return buildLong;
	}

	public void setBuildLong(long buildLong) {
		this.buildLong = buildLong;
	}

	public byte getBuildByte() {
		return buildByte;
	}

	public void setBuildByte(byte buildByte) {
		this.buildByte = buildByte;
	}

	public float getBuildFloat() {
		return buildFloat;
	}

	public void setBuildFloat(float buildFloat) {
		this.buildFloat = buildFloat;
	}

	public double getBuildDouble() {
		return buildDouble;
	}

	public void setBuildDouble(double buildDouble) {
		this.buildDouble = buildDouble;
	}

	public char getBuiltChar() {
		return builtChar;
	}

	public void setBuiltChar(char builtChar) {
		this.builtChar = builtChar;
	}

	public boolean isBuildBoolean() {
		return buildBoolean;
	}

	public void setBuildBoolean(boolean buildBoolean) {
		this.buildBoolean = buildBoolean;
	}

	public Short getWrapperShort() {
		return wrapperShort;
	}

	public void setWrapperShort(Short wrapperShort) {
		this.wrapperShort = wrapperShort;
	}

	public Long getWrapperLong() {
		return wrapperLong;
	}

	public void setWrapperLong(Long wrapperLong) {
		this.wrapperLong = wrapperLong;
	}

	public Byte getWrapperByte() {
		return wrapperByte;
	}

	public void setWrapperByte(Byte wrapperByte) {
		this.wrapperByte = wrapperByte;
	}

	public Float getWrapperFloat() {
		return wrapperFloat;
	}

	public void setWrapperFloat(Float wrapperFloat) {
		this.wrapperFloat = wrapperFloat;
	}

	public Double getWrapperDouble() {
		return wrapperDouble;
	}

	public void setWrapperDouble(Double wrapperDouble) {
		this.wrapperDouble = wrapperDouble;
	}

	public Character getWrapperChar() {
		return wrapperChar;
	}

	public void setWrapperChar(Character wrapperChar) {
		this.wrapperChar = wrapperChar;
	}

	public Boolean getWrapperBoolean() {
		return wrapperBoolean;
	}

	public void setWrapperBoolean(Boolean wrapperBoolean) {
		this.wrapperBoolean = wrapperBoolean;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public char[] getResume() {
		return resume;
	}

	public void setResume(char[] resume) {
		this.resume = resume;
	}

	public BigInteger getBigInteger() {
		return bigInteger;
	}

	public void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}

	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public User getSerializable() {
		return serializable;
	}

	public void setSerializable(User serializable) {
		this.serializable = serializable;
	}

	public Gender getEnumeratedOrdinal() {
		return enumeratedOrdinal;
	}

	public void setEnumeratedOrdinal(Gender enumeratedOrdinal) {
		this.enumeratedOrdinal = enumeratedOrdinal;
	}

	public Gender getEnumeratedString() {
		return enumeratedString;
	}

	public void setEnumeratedString(Gender enumeratedString) {
		this.enumeratedString = enumeratedString;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "MappingTypes [id=" + id + ", buildInt=" + buildInt
				+ ", buildShort=" + buildShort + ", buildLong=" + buildLong
				+ ", buildByte=" + buildByte + ", buildFloat=" + buildFloat
				+ ", buildDouble=" + buildDouble + ", builtChar=" + builtChar
				+ ", buildBoolean=" + buildBoolean + ", wrapperShort="
				+ wrapperShort + ", wrapperLong=" + wrapperLong
				+ ", wrapperByte=" + wrapperByte + ", wrapperFloat="
				+ wrapperFloat + ", wrapperDouble=" + wrapperDouble
				+ ", wrapperChar=" + wrapperChar + ", wrapperBoolean="
				+ wrapperBoolean + ", image=" + Arrays.toString(image)
				+ ", resume=" + Arrays.toString(resume) + ", bigInteger="
				+ bigInteger + ", bigDecimal=" + bigDecimal + ", string="
				+ string + ", calendar=" + calendar + ", serializable="
				+ serializable + ", enumeratedOrdinal=" + enumeratedOrdinal
				+ ", enumeratedString=" + enumeratedString + "]";
	}
}
