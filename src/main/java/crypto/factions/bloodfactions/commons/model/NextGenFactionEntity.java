package crypto.factions.bloodfactions.commons.model;

import crypto.factions.bloodfactions.commons.annotation.db.ColumnName;
import crypto.factions.bloodfactions.commons.annotation.db.MemoryOnlyField;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public interface NextGenFactionEntity {
    UUID getId();

    String getName();

    @SneakyThrows
    default Map<String, Object> getAsMap() {
        Map<String, Object> attributesMap = new HashMap<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(this);
            String name = field.getName();

            // Check if the field has the column name annotation.
            ColumnName column = field.getAnnotation(ColumnName.class);
            if (column != null) {
                name = column.value();
            }

            // Field must be ignored.
            MemoryOnlyField memoryOnlyField = field.getAnnotation(MemoryOnlyField.class);
            if(Objects.nonNull(memoryOnlyField)){
                field.setAccessible(false);
                continue;
            }

            attributesMap.put(name, value);
            field.setAccessible(false);
        }

        return attributesMap;
    }
}
