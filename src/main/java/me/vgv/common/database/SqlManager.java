package me.vgv.common.database;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface SqlManager {

	public Long readLong(String sql);

	public Integer readInt(String sql);

	public String readString(String sql);

	public Boolean readBoolean(String sql);

}
