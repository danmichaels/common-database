package me.vgv.common.database;

import org.hibernate.dialect.FirebirdDialect;

import java.sql.Types;

/**
 * Настройка особенностей связки Hibernate + Firebird
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class CommonFirebirdDialect extends FirebirdDialect {

	public CommonFirebirdDialect() {
		super();
		registerHibernateType(Types.BIGINT, "long");
		registerColumnType(Types.BIGINT, "bigint");
	}
}
