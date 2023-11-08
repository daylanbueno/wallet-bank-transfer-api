package com.ontop.WalletBankTransferAPI.infra.exceptios;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RetreiveMessageErrorDecoder  implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404 || 500 == response.status()) {
            try {
                String responseBody = getResponseBody(response);
                if (responseBody != null) {
                    Map<String, Object> errorResponse = objectMapper.readValue(responseBody, Map.class);
                    String errorMessage = (String) errorResponse.get("message");
                    if (errorMessage != null && response.status() == 404) {
                        return new IllegalArgumentException(errorMessage);
                    }
                    if (errorMessage != null && response.status() == 500) {
                        return new ExternalErrorException(errorMessage);
                    }
                }
            } catch (IOException e) {
               throw new Exception("Error convert response to json");
            }
            return new IllegalArgumentException("not found");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }

    private String getResponseBody(Response response) {
        if (response.body() != null) {
            try (BufferedReader reader = new BufferedReader(response.body().asReader())) {
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
                return body.toString();
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
