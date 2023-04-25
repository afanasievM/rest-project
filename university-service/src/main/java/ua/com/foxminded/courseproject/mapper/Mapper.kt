package ua.com.foxminded.courseproject.mapper;

public interface Mapper<D, E> {
    public D toDto(E entity);

    public E toEntity(D dto);
}
