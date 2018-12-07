package edu.gatech.curator.factory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.gatech.curator.model.NdJson;
import okhttp3.ResponseBody;
import org.hl7.fhir.dstu3.model.AllergyIntolerance;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.instance.model.api.IBaseResource;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NdJsonConverterFactory extends Converter.Factory {

    public static NdJsonConverterFactory create() {
        return create(new ObjectMapper());
    }

    @SuppressWarnings("ConstantConditions")
    public static NdJsonConverterFactory create(ObjectMapper mapper) {
        if (mapper == null) throw new NullPointerException("mapper == null");
        return new NdJsonConverterFactory(mapper);
    }

    private final ObjectMapper mapper;

    public NdJsonConverterFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        ParameterizedType pType = type instanceof ParameterizedType ? ((ParameterizedType) type) : null;
        if (pType != null && pType.getRawType().equals(NdJson.class) && pType.getActualTypeArguments().length == 1) {
            Type actualType = pType.getActualTypeArguments()[0];

            TypeFactory typeFactory = mapper.getTypeFactory();
            JavaType javaType = typeFactory.constructType(actualType);
            Class<?> rawClass = javaType.getRawClass();
            if(Resource.class.isAssignableFrom(rawClass)) {
                return new NdJsonConverter<>((Class<Resource>) rawClass);
            }
        }
        return null;
    }

    private final class NdJsonConverter<T extends Resource> implements Converter<ResponseBody, NdJson<T>> {
        private Class<T> _resourceClass;

        public NdJsonConverter (Class<T> resourceClass) {
            _resourceClass = resourceClass;
        }

        @Override
        public NdJson<T> convert(ResponseBody responseBody) throws IOException {
            NdJson<T> ndJson = new NdJson<>();

            FhirContext ctx = FhirContext.forDstu3();
            IParser jsonParser = ctx.newJsonParser();
            Reader reader = responseBody.charStream();

            String resourceJson = "";
            int intValueOfChar;
            while ((intValueOfChar = reader.read()) != -1) {
                if (intValueOfChar == 10) {
                    T jObject= jsonParser.parseResource(_resourceClass, resourceJson);
                    ndJson.getResources().add(jObject);
                    resourceJson = "";
                } else {
                    resourceJson += (char) intValueOfChar;
                }
            }
            reader.close();
            return ndJson;
        }
    }
}
