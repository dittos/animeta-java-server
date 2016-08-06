package animeta.dao;

import animeta.model.QTitleMapping;
import animeta.model.TitleMapping;

public class TitleMappingDAO extends AbstractDAO<TitleMapping> {
    public TitleMapping find(String title) {
        return queryFactory().selectFrom(QTitleMapping.titleMapping)
                .where(QTitleMapping.titleMapping.title.eq(title))
                .fetchOne();
    }
}
