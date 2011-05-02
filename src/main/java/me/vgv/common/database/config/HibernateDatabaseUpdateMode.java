package me.vgv.common.database.config;

/**
 * Тип обновления схемы базы, который будет использовать hibernate.
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public enum HibernateDatabaseUpdateMode {

	NONE(""),

	UPDATE("update"),

	CREATE("create"),

	CREATE_DROP("create-drop"),

	VALIDATE("validate");

	private final String hibernateParameter;

	HibernateDatabaseUpdateMode(String hibernateParameter) {
		this.hibernateParameter = hibernateParameter;
	}

	public String getHibernateParameter() {
		return hibernateParameter;
	}
}
