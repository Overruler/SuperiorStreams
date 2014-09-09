package utils.lists;

public class Pair<L, R> {
	public final L lhs;
	public final R rhs;

	public static <L, R> Pair<L, R> of(L lhs, R rhs) {
		return new Pair<>(lhs, rhs);
	}
	public Pair(L lhs, R rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public R rhs() {
		return rhs;
	}
	public L lhs() {
		return lhs;
	}
	public <N> Pair<L, N> keepingLhs(N newRhs) {
		return new Pair<>(lhs, newRhs);
	}
	public <N> Pair<N, L> keepingSwappedLhs(N newLhs) {
		return new Pair<>(newLhs, lhs);
	}
	public <N> Pair<N, R> keepingRhs(N newLhs) {
		return new Pair<>(newLhs, rhs);
	}
	public <N> Pair<R, N> keepingSwappedRhs(N newRhs) {
		return new Pair<>(rhs, newRhs);
	}
	public Pair<R, L> swap() {
		return new Pair<>(rhs, lhs);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (lhs == null ? 0 : lhs.hashCode());
		result = prime * result + (rhs == null ? 0 : rhs.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if(lhs == null) {
			if(other.lhs != null) {
				return false;
			}
		} else if(!lhs.equals(other.lhs)) {
			return false;
		}
		if(rhs == null) {
			if(other.rhs != null) {
				return false;
			}
		} else if(!rhs.equals(other.rhs)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "Pair [lhs=" + lhs + ", rhs=" + rhs + "]";
	}
}