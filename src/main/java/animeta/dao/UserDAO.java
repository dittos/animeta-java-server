package animeta.dao;

import animeta.model.QUser;
import animeta.model.User;

public class UserDAO extends AbstractDAO<User> {
    public User get(int id) {
        return getInternal(id);
    }

    public User get(String username) {
        QUser user = QUser.user;
        return queryFactory().selectFrom(user)
                .where(user.username.eq(username))
                .fetchOne();
    }
}
