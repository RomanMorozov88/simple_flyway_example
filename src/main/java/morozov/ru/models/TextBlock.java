package morozov.ru.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("TextBlock")
public class TextBlock extends Block {

	private String text;

	public TextBlock() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
