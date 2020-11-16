//package com.hbhb.cw.authserver.component;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//import com.hbhb.core.utils.DateUtil;
//import com.hbhb.cw.authserver.exception.AuthException;
//
//import lombok.SneakyThrows;
//
///**
// * 异常信息格式化
// * @author xiaokang
// * @since 2020-10-09
// */
//public class AuthExceptionSerializer extends StdSerializer<AuthException> {
//    private static final long serialVersionUID = 948243978126979218L;
//
//    public AuthExceptionSerializer() {
//        super(AuthException.class);
//    }
//
//    @Override
//    @SneakyThrows
//    public void serialize(AuthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
//        jsonGenerator.writeStartObject();
//        jsonGenerator.writeObjectField("code",e.getCode());
//        jsonGenerator.writeStringField("message", e.getMessage());
//        jsonGenerator.writeStringField("data", null);
//        jsonGenerator.writeStringField("timestamp", DateUtil.getCurrentDate());
//        jsonGenerator.writeEndObject();
//    }
//}
