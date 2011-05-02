package me.vgv.common.database.config;


/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DatabasePoolConfigurationImpl implements DatabasePoolConfiguration {

	private final int initialPoolSize;
	private final int maxActivePoolSize;
	private final int maxIdlePoolSize;
	private final int minIdlePoolSize;
	private final long idleEvictorRunInterval;
	private final long idleConnectionLifetime;

	private DatabasePoolConfigurationImpl(int initialPoolSize, int maxActivePoolSize, int maxIdlePoolSize, int minIdlePoolSize, long idleEvictorRunInterval, long idleConnectionLifetime) {
		this.initialPoolSize = initialPoolSize;
		this.maxActivePoolSize = maxActivePoolSize;
		this.maxIdlePoolSize = maxIdlePoolSize;
		this.minIdlePoolSize = minIdlePoolSize;
		this.idleEvictorRunInterval = idleEvictorRunInterval;
		this.idleConnectionLifetime = idleConnectionLifetime;
	}

	public static class Builder {
		private int initialPoolSize = 1;
		private int maxActivePoolSize = 8;
		private int maxIdlePoolSize = 2;
		private int minIdlePoolSize = 0;
		private long idleEvictorRunInterval = -1; // no eviction
		private long idleConnectionLifetime = 1000 * 60 * 30; // 30 min

		public Builder initialPoolSize(int initialPoolSize) {
			this.initialPoolSize = initialPoolSize;
			return this;
		}

		public Builder maxActivePoolSize(int maxActivePoolSize) {
			this.maxActivePoolSize = maxActivePoolSize;
			return this;
		}

		public Builder maxIdlePoolSize(int maxIdlePoolSize) {
			this.maxIdlePoolSize = maxIdlePoolSize;
			return this;
		}

		public Builder minIdlePoolSize(int minIdlePoolSize) {
			this.minIdlePoolSize = minIdlePoolSize;
			return this;
		}

		public Builder idleEvictorRunInterval(long idleEvictorRunInterval) {
			this.idleEvictorRunInterval = idleEvictorRunInterval;
			return this;
		}

		public Builder idleConnectionLifetime(long idleConnectionLifetime) {
			this.idleConnectionLifetime = idleConnectionLifetime;
			return this;
		}

		public DatabasePoolConfiguration build() {
			return new DatabasePoolConfigurationImpl(initialPoolSize, maxActivePoolSize, maxIdlePoolSize, minIdlePoolSize, idleEvictorRunInterval, idleConnectionLifetime);
		}
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public int getMaxActivePoolSize() {
		return maxActivePoolSize;
	}

	public int getMaxIdlePoolSize() {
		return maxIdlePoolSize;
	}

	public int getMinIdlePoolSize() {
		return minIdlePoolSize;
	}

	public long getIdleEvictorRunInterval() {
		return idleEvictorRunInterval;
	}

	public long getIdleConnectionLifetime() {
		return idleConnectionLifetime;
	}
}
