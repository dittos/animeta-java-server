package animeta.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.Providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParamConverterProvider implements ParamConverterProvider {
    private final ObjectMapper objectMapper;
    private final ServiceLocator serviceLocator;

    @Inject
    public JsonParamConverterProvider(ObjectMapper objectMapper, ServiceLocator serviceLocator) {
        this.objectMapper = objectMapper;
        this.serviceLocator = serviceLocator;
    }

    @Override public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        Set<ParamConverterProvider> providerSet = Providers.getProviders(serviceLocator, ParamConverterProvider.class);
        for (ParamConverterProvider provider : providerSet) {
            if (provider == this) {
                continue;
            }
            ParamConverter<T> converter = provider.getConverter(rawType, genericType, annotations);
            if (converter != null) {
                return converter;
            }
        }

        return new ParamConverter<T>() {
            @Override public T fromString(String value) {
                try {
                    return objectMapper.readValue(value, rawType);
                } catch (IOException e) {
                    throw new ProcessingException(e);
                }
            }

            @Override public String toString(T value) {
                try {
                    return objectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new ProcessingException(e);
                }
            }
        };
    }
}
