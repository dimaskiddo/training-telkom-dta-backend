package co.id.telkom.digitalent.response;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;

public class BuilderResponse {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    public static void responseWriter(HttpServletResponse response, DataResponse dataResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String finalResponse = objectMapper.writeValueAsString(dataResponse);

            response.setStatus(dataResponse.getCode());
            response.setContentType("application/json");

            response.getWriter().write(finalResponse);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
