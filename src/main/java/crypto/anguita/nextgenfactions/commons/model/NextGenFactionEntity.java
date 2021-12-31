package crypto.anguita.nextgenfactions.commons.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public interface NextGenFactionEntity {
    UUID getId();
    String getName();
    default Map<String, Object> getAsMap(){
        Map<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("id", this.getId().toString());
        attributesMap.put("name", this.getName());
        return attributesMap;
    }
}
