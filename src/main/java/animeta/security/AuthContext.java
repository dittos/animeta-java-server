package animeta.security;

import animeta.model.User;

public interface AuthContext {
    User getUser();
    boolean isAuthenticated();

    static AuthContext unauthenticated() {
        return new AuthContext() {
            @Override public User getUser() {
                return null;
            }

            @Override public boolean isAuthenticated() {
                return false;
            }
        };
    }
}
