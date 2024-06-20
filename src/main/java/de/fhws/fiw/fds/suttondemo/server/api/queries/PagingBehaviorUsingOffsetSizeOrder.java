package de.fhws.fiw.fds.suttondemo.server.api.queries;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Hyperlinks;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingContext;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public class PagingBehaviorUsingOffsetSizeOrder<R, T extends AbstractModel> extends PagingBehaviorUsingOffsetSize<R,T>{
    public static final String QUERY_PARAM_ORDER_BY = "orderBy";
    String orderByQueryParamName = QUERY_PARAM_ORDER_BY;
    String orderBy;

    public PagingBehaviorUsingOffsetSizeOrder(String offsetQueryParamName, String sizeQueryParamName, int offset,
            int size, String orderByQueryParamName, String orderBy) {
        super(offsetQueryParamName, sizeQueryParamName, offset, size);
        this.orderBy = orderBy;
        this.orderByQueryParamName = orderByQueryParamName;
    }
    public PagingBehaviorUsingOffsetSizeOrder(int offset, int size, String orderBy) {
        super(offset, size);
        this.orderBy = orderBy;
    }
    public String getOrderBy(){
        return this.orderBy;
    }
    public void setOrderBy(String orderBy){
        this.orderBy = orderBy;
    }
    public final boolean hasReverseOrderByLink(){
        System.out.println(this.orderBy);
        return (this.orderBy != "" && this.orderBy != null && !this.orderBy.isEmpty());
    }

    public final void addReverseOrderByLink(final PagingContext<R, Collection<T>> pagingContext) {
        if (hasReverseOrderByLink()) {
            Hyperlinks.addLink(pagingContext.getResponseBuilder(),
                    getReverseOrderByUri(pagingContext.getUriInfo()),
                    "reverseOrder",
                    pagingContext.getMediaType());
        }
    }

    

    
    protected URI getReverseOrderByUri(final SuttonUriInfo uriInfo) {
        String decodedOrderBy = this.orderBy;
        
        System.out.println("Is orderBy empty: " + decodedOrderBy.isEmpty());
        System.out.println("Decoded orderBy: " + decodedOrderBy);
    
        String reverseOrderBy;
        if (decodedOrderBy.startsWith("+") || decodedOrderBy.startsWith("%2B")) {
            reverseOrderBy = "-" + decodedOrderBy.substring(1);
        } else if (decodedOrderBy.startsWith("-")) {
            reverseOrderBy = "+" + decodedOrderBy.substring(1);
        } else {
            reverseOrderBy = decodedOrderBy; // If it doesn't start with + or -, leave it unchanged
        }
    
        System.out.println("Reversed orderBy: " + reverseOrderBy);
    
        String encodedReverseOrderBy;
        try {
            encodedReverseOrderBy = URLEncoder.encode(reverseOrderBy, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding reverseOrderBy parameter", e);
        }
    
        System.out.println("Encoded reversed orderBy: " + encodedReverseOrderBy);
    
        Map<String, String> queryParams = getQueryParamsAsMap(this.offset, this.size, encodedReverseOrderBy);
    
        final String uriTemplate = uriInfo.createURIWithQueryParamTemplates(
                this.offsetQueryParamName,
                this.sizeQueryParamName,
                this.orderByQueryParamName
        );
    
        return uriInfo.getURI(uriTemplate, queryParams);
    }
    
    
    
        private Map<String, String> getQueryParamsAsMap(final int offset, final int size, final String orderBy) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(this.offsetQueryParamName,""+ offset);
        queryParams.put(this.sizeQueryParamName, ""+size);
        queryParams.put(this.orderByQueryParamName, orderBy);
        System.out.println(queryParams);
        return queryParams;
    }
}