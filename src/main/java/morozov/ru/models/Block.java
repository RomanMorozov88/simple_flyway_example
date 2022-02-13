package morozov.ru.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "className")
@JsonSubTypes({
	@JsonSubTypes.Type(value = TextBlock.class, name = "TextBlock"),
	@JsonSubTypes.Type(value = LocalDateBlock.class, name = "LocalDateBlock"), 
	})
public class Block {

	private int id;
	private int idPack;
	private String name;
	private int typeCode;

	public Block() {
		super();
	}

	public Block(String name, int typeCode) {
		super();
		this.name = name;
		this.typeCode = typeCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPack() {
		return idPack;
	}

	public void setIdPack(int idPack) {
		this.idPack = idPack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}
	
	

}
