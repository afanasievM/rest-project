package ua.com.foxminded.courseproject.mapper

interface Mapper<D, E, M> {
    fun toDto(entity: E?): D?
    fun toEntity(dto: D?): E?
    fun documentToEntity(doc: M): E
    fun entityToDocument(entity: E): M
}

