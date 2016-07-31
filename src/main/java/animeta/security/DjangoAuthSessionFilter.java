package animeta.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;

import com.fasterxml.jackson.databind.ObjectMapper;
import django.Signing;

public class DjangoAuthSessionFilter implements ContainerRequestFilter {
    public static String PROPERTY_NAME = "DjangoAuthSession";

    private final ObjectMapper mapper = new ObjectMapper();
    private final String sessionCookieName = "sessionid";
    private final String secretKey;
    private final int maxAge = Integer.MAX_VALUE; // FIXME

    public DjangoAuthSessionFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override public void filter(ContainerRequestContext requestContext) throws IOException {
        Cookie sessionCookie = requestContext.getCookies().get(sessionCookieName);
        if (sessionCookie == null) {
            return;
        }
        DjangoAuthSession session = Signing.loadString(sessionCookie.getValue(), secretKey, "django.contrib.sessions.backends.signed_cookies", data -> mapper.readValue(data, DjangoAuthSession.class), maxAge);
        requestContext.setProperty(PROPERTY_NAME, session);
    }
}
