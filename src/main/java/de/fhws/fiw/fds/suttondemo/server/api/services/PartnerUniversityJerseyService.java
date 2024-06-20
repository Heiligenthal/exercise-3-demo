/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.suttondemo.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.api.models.PartnerUniversity;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByPartnerUniversityName;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByModuleName;
import de.fhws.fiw.fds.suttondemo.server.api.states.partneruniversity_modules.*;
import de.fhws.fiw.fds.suttondemo.server.api.states.partneruniversities.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("partneruniversities")
public class PartnerUniversityJerseyService extends AbstractJerseyService {

    public PartnerUniversityJerseyService() {
        super();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllPartnerUniversities(
            @DefaultValue("") @QueryParam("name") final String name,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size,
            @DefaultValue("") @QueryParam("orderBy") String orderBy) {
        try {
            System.out.println(orderBy);
            return new GetAllPartnerUniversities(
                    this.serviceContext,
                    new QueryByPartnerUniversityName<>(name, offset, size, orderBy)
            ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(e.getExceptionMessage(), e.getStatus().getCode());
        }
    }

    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSinglePartnerUniversity(@PathParam("id") final long id) {
        System.out.println("Hallo sieht man das?");
        System.out.println(this.serviceContext);
        System.out.println(id);
        try {


            return new GetSinglePartnerUniversity(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response
                    .status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build()
            );
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSinglePartnerUniversity(final PartnerUniversity partneruniversityModel) {
        try {
            return new PostNewPartnerUniversity(this.serviceContext, partneruniversityModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSinglePartnerUniversity(@PathParam("id") final long id, final PartnerUniversity partneruniversityModel) {
        try {
            return new PutSinglePartnerUniversity(this.serviceContext, id, partneruniversityModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSinglePartnerUniversity(@PathParam("id") final long id) {
        try {
            return new DeleteSinglePartnerUniversity(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @GET
    @Path("{partneruniversityId: \\d+}/modules")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getModulesOfPartnerUniversity(@PathParam("partneruniversityId") final long partneruniversityId,
                                         @DefaultValue("") @QueryParam("name") final String name,
                                         @DefaultValue("0") @QueryParam("offset") int offset,
                                         @DefaultValue("20") @QueryParam("size") int size) {
        try {
            return new GetAllModulesOfPartnerUniversity(this.serviceContext, partneruniversityId, new QueryByModuleName<>(partneruniversityId, name, offset, size)).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @GET
    @Path("{partneruniversityId: \\d+}/modules/{moduleId: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getModuleByIdOfPartnerUniversity(@PathParam("partneruniversityId") final long partneruniversityId,
                                            @PathParam("moduleId") final long moduleId) {
        try {
            return new GetSingleModuleOfPartnerUniversity( this.serviceContext, partneruniversityId, moduleId ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @POST
    @Path("{partneruniversityId: \\d+}/modules")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createNewModuleOfPartnerUniversity(@PathParam("partneruniversityId") final long partneruniversityId, final Module module) {
        try {
            return new PostNewModuleOfPartnerUniversity( this.serviceContext, partneruniversityId, module ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @PUT
    @Path("{partneruniversityId: \\d+}/modules/{moduleId: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateNewModuleOfPartnerUniversity(@PathParam("partneruniversityId") final long partneruniversityId,
                                              @PathParam("moduleId") final long moduleId, final Module module) {
        try {
            return new PutSingleModuleOfPartnerUniversity( this.serviceContext, partneruniversityId, moduleId, module ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @DELETE
    @Path("{partneruniversityId: \\d+}/modules/{moduleId: \\d+}")
    public Response deleteModuleOfPartnerUniversity(@PathParam("partneruniversityId") final long partneruniversityId,
                                           @PathParam("moduleId") final long moduleId) {
        try {
            return new DeleteSingleModuleOfPartnerUniversity( this.serviceContext, moduleId, partneruniversityId ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

}
