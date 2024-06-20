package de.fhws.fiw.fds.suttondemo.server.api.queries;

import java.util.Collection;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingContext;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.PartnerUniversity;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;

public class QueryByPartnerUniversityName<R> extends AbstractQuery<R, PartnerUniversity> {
    private String orderBy;
    private String name;
    public QueryByPartnerUniversityName(String name, int offset, int size, String orderBy) {
        this.name = name;
        this.orderBy = orderBy;
        System.out.println(this.orderBy);
        this.pagingBehavior = new PagingBehaviorUsingOffsetSizeOrder<>(offset, size, orderBy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderBy() {
        return orderBy;
    }
    
    public void setOrderBy(String orderBy){
        this.orderBy = orderBy;
    }

    @Override
    protected CollectionModelResult<PartnerUniversity> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        searchParameter.setOrderByAttribute(orderBy);
        return DaoFactory.getInstance().getPartnerUniversityDao().readByName(
            this.name,
            searchParameter);
    }

    public final void addOrderByLink(final PagingContext<R, Collection<PartnerUniversity>> pagingContext) {
        ((PagingBehaviorUsingOffsetSizeOrder<R, PartnerUniversity>) this.pagingBehavior).addReverseOrderByLink(pagingContext);
    }
}
