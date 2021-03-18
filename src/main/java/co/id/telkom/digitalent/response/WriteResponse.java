package co.id.telkom.digitalent.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

public class WriteResponse {

    private static final String MESSAGE_SUCCESS = "SUCCESS";
    private static final String MESSAGE_FAILED = "FAILED";

    private static void responseWriter(HttpServletResponse response, Object data, Integer code) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String finalResponse = objectMapper.writeValueAsString(data);

            response.setStatus(code);
            response.setContentType("application/json");

            response.getWriter().write(finalResponse);
            response.getWriter().flush();
        } catch (Exception e) {
            responseInternalServerError(response, e.getMessage().toUpperCase());
            throw new RuntimeException(e);
        }
    }

    public static void responseSuccessOK(HttpServletResponse response, String message) {
        if (message.isEmpty()) {
            message = "SUCCESS";
        }

        SuccessResponse<String> successResponse = new SuccessResponse<>();

        successResponse.setCode(HttpServletResponse.SC_OK);
        successResponse.setStatus(MESSAGE_SUCCESS);
        successResponse.setMessage(message);

        responseWriter(response, successResponse, successResponse.getCode());
    }

    public static void responseSuccessWithData(HttpServletResponse response, DataResponse data) {
        data.setStatus(MESSAGE_SUCCESS);
        responseWriter(response, data, data.getCode());
    }

    public static void responseBadRequest(HttpServletResponse response, String error) {
        if (error.isEmpty()) {
            error = "BAD REQUEST";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setCode(HttpServletResponse.SC_BAD_REQUEST);
        errorResponse.setStatus(MESSAGE_FAILED);
        errorResponse.setError(error);

        responseWriter(response, errorResponse, errorResponse.getCode());
    }

    public static void responseUnauthorized(HttpServletResponse response, String error) {
        if (error.isEmpty()) {
            error = "UNAUTHORIZED";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.setStatus(MESSAGE_FAILED);
        errorResponse.setError(error);

        responseWriter(response, errorResponse, errorResponse.getCode());
    }

    public static void responseForbidden(HttpServletResponse response, String error) {
        if (error.isEmpty()) {
            error = "FORBIDDEN";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setCode(HttpServletResponse.SC_FORBIDDEN);
        errorResponse.setStatus(MESSAGE_FAILED);
        errorResponse.setError(error);

        responseWriter(response, errorResponse, errorResponse.getCode());
    }

    public static void responseNotFound(HttpServletResponse response, String error) {
        if (error.isEmpty()) {
            error = "NOT FOUND";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setCode(HttpServletResponse.SC_NOT_FOUND);
        errorResponse.setStatus(MESSAGE_FAILED);
        errorResponse.setError(error);

        responseWriter(response, errorResponse, errorResponse.getCode());
    }

    public static void responseInternalServerError(HttpServletResponse response, String error) {
        if (error.isEmpty()) {
            error = "INTERNAL SERVER ERROR";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        errorResponse.setStatus(MESSAGE_FAILED);
        errorResponse.setError(error);

        responseWriter(response, errorResponse, errorResponse.getCode());
    }

    public static void responseBadGateway(HttpServletResponse response, String error) {
        if (error.isEmpty()) {
            error = "BAD GATEWAY";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setCode(HttpServletResponse.SC_BAD_GATEWAY);
        errorResponse.setStatus(MESSAGE_FAILED);
        errorResponse.setError(error);

        responseWriter(response, errorResponse, errorResponse.getCode());
    }
}
