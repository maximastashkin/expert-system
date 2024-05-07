package ru.rsreu.demo.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rsreu.alcohol.CacheContext;
import ru.rsreu.expert.system.data.Context;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersistenceStorage {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final JdbcTemplate jdbcTemplate;

    public void saveContext(Context context) {
        String contextValue = serializeContext(context);
        jdbcTemplate.update("insert into context values (?)", contextValue);
    }

    @SneakyThrows
    private String serializeContext(Context context) {
        return OBJECT_MAPPER.writeValueAsString(context);
    }

    public List<? extends Context> getAllContexts() {
        List<String> contextsValues = jdbcTemplate.query(
                "select * from context",
                (rs, rowNum) -> rs.getString(1)
        );
        return contextsValues.stream().map(this::deserializeCacheContext).toList();
    }

    @SneakyThrows
    private CacheContext deserializeCacheContext(String contextValue) {
        return OBJECT_MAPPER.readValue(contextValue, CacheContext.class);
    }
}
