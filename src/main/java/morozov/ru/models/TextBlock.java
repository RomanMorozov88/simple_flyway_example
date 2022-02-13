package morozov.ru.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("TextBlock")
public class TextBlock extends Block {

	private String text;

	public TextBlock() {
		super();
	}

	public TextBlock(String name, int typeCode, String text) {
		super(name, typeCode);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
