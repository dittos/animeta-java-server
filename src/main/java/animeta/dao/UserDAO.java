package animeta.dao;

import animeta.model.QUser;
import animeta.model.User;

public class UserDAO extends AbstractDAO<User> {
    public User find(int id) {
        return get(id);
    }

    public User find(String username) {
        return queryFactory().selectFrom(QUser.user)
                .where(QUser.user.username.eq(username))
                .fetchOne();
    }
}
