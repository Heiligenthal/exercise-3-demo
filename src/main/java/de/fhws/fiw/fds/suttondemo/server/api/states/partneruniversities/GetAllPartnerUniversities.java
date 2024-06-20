/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.suttondemo.server.api.states.partneruniversities;

import java.util.Collection;

import de.fhws.fiw.fds.sutton.server.api.queries.PagingContext;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.suttondemo.server.api.models.PartnerUniversity;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByPartnerUniversityName;
import jakarta.ws.rs.core.Response;

public class GetAllPartnerUniversities extends AbstractGetCollectionState<Response, PartnerUniversity> {

    public GetAllPartnerUniversities(ServiceContext serviceContext, QueryByPartnerUniversityName<Response> query) {
        super(serviceContext, query);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PartnerUniversityUri.REL_PATH, PartnerUniversityRelTypes.CREATE_PARTNERUNIVERSITY, getAcceptRequestHeader());
        addLink(PartnerUniversityUri.REL_PATH_ORDER_BY, PartnerUniversityRelTypes.GET_ALL_PARTNERUNIVERSITIES, getAcceptRequestHeader());
        addLink(PartnerUniversityUri.REL_PATH_FILTER_BY, PartnerUniversityRelTypes.GET_ALL_PARTNERUNIVERSITIES, getAcceptRequestHeader());
    }

    protected void definePagingLinks() {
        final PagingContext<Response, Collection<PartnerUniversity>> pagingContext = createPagingContext();
        System.out.println(pagingContext);

        this.query.addPrevPageLink(pagingContext);
        this.query.addNextPageLink(pagingContext);
        ((QueryByPartnerUniversityName<Response>)this.query).addOrderByLink(pagingContext);
    }

    private PagingContext<Response, Collection<PartnerUniversity>> createPagingContext() {
        return new PagingContext<>(this.uriInfo, this.suttonResponse, getAcceptRequestHeader());
    }
}
