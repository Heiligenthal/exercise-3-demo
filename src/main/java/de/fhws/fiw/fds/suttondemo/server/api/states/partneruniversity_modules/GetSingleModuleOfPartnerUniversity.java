package de.fhws.fiw.fds.suttondemo.server.api.states.partneruniversity_modules;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class GetSingleModuleOfPartnerUniversity extends AbstractGetRelationState<Response, Module> {

    public GetSingleModuleOfPartnerUniversity(ServiceContext serviceContext, long primaryId, long requestedId) {
        super(serviceContext, primaryId, requestedId);
        this.suttonResponse = new JerseyResponse<>();
    }


    @Override
    protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineHttpCaching() {
        final String modelFromDBEtag = EtagGenerator.createEtag(this.requestedModel.getResult());
        this.suttonResponse.entityTag(modelFromDBEtag);
        this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
    }

    @Override protected SingleModelResult<Module> loadModel( )
    {
        SingleModelResult<Module> location = DaoFactory.getInstance( ).getModuleDao( ).readById( this.requestedId );
        if(isPartnerUniversityLinkedToThisModule()) {
            location.getResult().setPrimaryId(this.primaryId);
        }
        return location;
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( PartnerUniversityModuleUri.REL_PATH_SHOW_ONLY_LINKED,
                PartnerUniversityModuleRelTypes.GET_ALL_LINKED_MODULES,
                getAcceptRequestHeader( ),
                this.primaryId );

        if ( isPartnerUniversityLinkedToThisModule( ) )
        {
            addLink( PartnerUniversityModuleUri.REL_PATH_ID,
                    PartnerUniversityModuleRelTypes.UPDATE_SINGLE_MODULE,
                    getAcceptRequestHeader( ),
                    this.primaryId, this.requestedId );

            addLink( PartnerUniversityModuleUri.REL_PATH_ID,
                    PartnerUniversityModuleRelTypes.DELETE_LINK_FROM_PARTNERUNIVERSITY_TO_MODULE,
                    getAcceptRequestHeader( ),
                    this.primaryId, this.requestedId );
        }
        else
        {
            addLink( PartnerUniversityModuleUri.REL_PATH_ID,
                    PartnerUniversityModuleRelTypes.CREATE_LINK_FROM_PARTNERUNIVERSITY_TO_MODULE,
                    getAcceptRequestHeader( ),
                    this.primaryId, this.requestedId );
        }
    }

    private boolean isPartnerUniversityLinkedToThisModule( )
    {
        return !DaoFactory.getInstance( )
                .getPartnerUniversityModuleDao( )
                .readById( this.primaryId, this.requestedId )
                .isEmpty( );
    }
}
