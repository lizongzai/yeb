package com.xxxx.server.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 自定义Authority反序列化
 */
public class CustomAuthorityDeserializer extends JsonDeserializer {

  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
    JsonNode jsonNode = objectMapper.readTree(jsonParser);
    List<GrantedAuthority> grantedAuthorities = null;
    Iterator<JsonNode> iterator = jsonNode.elements();
    while (iterator.hasNext()) {
      JsonNode next = iterator.next();
      JsonNode authority = next.get("authority");
      grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
    }
    return grantedAuthorities;
  }
}
