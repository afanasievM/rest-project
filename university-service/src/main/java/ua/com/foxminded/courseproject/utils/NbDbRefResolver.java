package ua.com.foxminded.courseproject.utils;

import com.mongodb.DBRef;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefProxyHandler;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DbRefResolverCallback;
import org.springframework.data.mongodb.core.convert.ReferenceLookupDelegate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class NbDbRefResolver implements DbRefResolver {

    public static final String ID = "_id";
    public static final String CLASS = "_class";
    /**
     *   If set to 'true' will fake eagerly resolve DbRefs, by creating Documents with _id and _class only fields. It is
     * up to implementation later to call an additional non-blocking calls in order to fill the DbRefs fields.
     *   If set to false will resolve in full DbRef, but in blocking fashion.
     */
    public static final boolean RESOLVE_DB_REFS_BY_ID_ONLY = true;

    @Autowired
    private ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory;

    @Override
    public Object resolveDbRef(MongoPersistentProperty property, DBRef dbref, DbRefResolverCallback callback, DbRefProxyHandler proxyHandler) {
        Object res = callback.resolve(property);
        return res;
    }

    @Override
    public DBRef createDbRef(org.springframework.data.mongodb.core.mapping.DBRef annotation, MongoPersistentEntity<?> entity, Object id) {
        return new DBRef(entity.getCollection(), id);
    }

    @Override
    public Document fetch(DBRef dbRef) {
        StringUtils.hasText(dbRef.getDatabaseName());
        return buildDocumentWithId(dbRef.getId(), getCollection(dbRef.getCollectionName()));
    }

    @Override
    public List<Document> bulkFetch(List<DBRef> refs) {
        if (CollectionUtils.isEmpty(refs)) {
            return Collections.emptyList();
        }

        String collection = refs.iterator().next().getCollectionName();
        List<Object> ids = new ArrayList<>(refs.size());

        for (DBRef ref : refs) {

            if (!collection.equals(ref.getCollectionName())) {
                throw new InvalidDataAccessApiUsageException(
                        "DBRefs must all target the same collection for bulk fetch operation.");
            }

            ids.add(ref.getId());
        }

        MongoCollection<Document> collection1 = getCollection(collection);

        if (RESOLVE_DB_REFS_BY_ID_ONLY) {
            List<Document> result1 = new ArrayList<>();
            ids.forEach(o -> {
                Document document = buildDocumentWithId(o, collection1);
                result1.add(document);
            });
            return result1;
        } else {
            FindPublisher<Document> findPublisher = collection1 //
                    .find(new Document("_id", new Document("$in", ids)));
            List<Document> result = Flux.from(findPublisher)
                    /*
                    .map(document -> {
                        return document;
                    })
                    */
                    .collectList()
                    .block();

            List<Document> result1 = ids.stream() //
                    .flatMap(id -> documentWithId(id, result)) //
                    .collect(Collectors.toList());

            return result1;
        }
    }

    private MongoCollection<Document> getCollection(String collection) {
        MongoDatabase db = reactiveMongoDatabaseFactory.getMongoDatabase().block();
        return db.getCollection(collection);
    }

    @NotNull
    private Document buildDocumentWithId(Object id, MongoCollection<Document> collection) {
        Document document = new Document();
        document.append(ID, id);
        document.append(CLASS, collection.getDocumentClass().getName());
        return document;
    }

    /**
     * Returns document with the given identifier from the given list of {@link Document}s.
     *
     * @param identifier
     * @param documents
     * @return
     */
    private static Stream<Document> documentWithId(Object identifier, Collection<Document> documents) {

        return documents.stream() //
                .filter(it -> it.get(ID).equals(identifier)) //
                .limit(1);
    }

    @Override
    public Object resolveReference(MongoPersistentProperty property, Object source, ReferenceLookupDelegate referenceLookupDelegate, MongoEntityReader entityReader) {
        return null;
    }
}
