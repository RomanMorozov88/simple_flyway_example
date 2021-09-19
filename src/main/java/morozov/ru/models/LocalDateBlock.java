package morozov.ru.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("LocalDateBlock")
public class LocalDateBlock extends Block {
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate firstDate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate secondDate;
	
	public LocalDateBlock() {
		super();
	}
	public LocalDate getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(LocalDate firstDate) {
		this.firstDate = firstDate;
	}
	public LocalDate getSecondDate() {
		return secondDate;
	}
	public void setSecondDate(LocalDate secondDate) {
		this.secondDate = secondDate;
	}

}
