package ua.com.foxminded.courseproject.mapper;

import java.util.Optional;

public interface OptionalMapper<D, E> extends Mapper<D, E> {

    default Optional<D> toOptionalDto(Optional<E> entity) {
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toDto(entity.get()));
    }

}

