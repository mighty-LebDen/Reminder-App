package remainder.mapper;

public interface Mapper<F, T> {
	T map(F from);
	default T map(F fromObject, T toObject) {
		return toObject;
	}
}
