package animeta.security;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.hk2.api.Factory;

import animeta.dao.UserDAO;
import animeta.model.User;

public class AuthContextFactory implements Factory<AuthContext> {
    private final UserDAO userDAO;
    private final ContainerRequestContext requestContext;

    @Inject public AuthContextFactory(UserDAO userDAO, ContainerRequestContext requestContext) {
        this.userDAO = userDAO;
        this.requestContext = requestContext;
    }

    @Override public AuthContext provide() {
        DjangoAuthSession session = (DjangoAuthSession) requestContext.getProperty(DjangoAuthSessionFilter.PROPERTY_NAME);
        if (session == null) {
            return AuthContext.unauthenticated();
        }
        return new AuthContext() {
            private boolean loaded = false;
            private User user;

            @Override public User getUser() {
                loadUser();
                return user;
            }

            @Override public boolean isAuthenticated() {
                loadUser();
                return user != null;
            }

            private void loadUser() {
                if (loaded) {
                    return;
                }
                user = userDAO.get(Integer.valueOf(session.getUserId()));
                loaded = true;
            }
        };
    }

    @Override public void dispose(AuthContext authContext) {
    }
}
