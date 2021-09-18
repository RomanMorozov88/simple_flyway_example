package morozov.ru.enums;

public enum TypeEnum {
	
	TEXT_BLOCK(1),
    LOCAL_DATE_BLOCK(2);

    private int code;

    TypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
