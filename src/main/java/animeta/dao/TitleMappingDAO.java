package animeta.dao;

import animeta.model.QTitleMapping;
import animeta.model.TitleMapping;

public class TitleMappingDAO extends AbstractDAO<TitleMapping> {
    public TitleMapping get(String title) {
        QTitleMapping titleMapping = QTitleMapping.titleMapping;
        return queryFactory().selectFrom(titleMapping)
                .where(titleMapping.title.eq(title))
                .fetchOne();
    }
}
