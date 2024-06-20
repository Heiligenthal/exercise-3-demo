package de.fhws.fiw.fds.suttondemo.server.api.states.modules;

import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.api.states.partneruniversities.PartnerUniversityRelTypes;
import de.fhws.fiw.fds.suttondemo.server.api.states.partneruniversities.PartnerUniversityUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class DeleteSingleModule extends AbstractDeleteState<Response, Module> {

    public DeleteSingleModule(ServiceContext serviceContext, long modelIdToDelete) {
        super(serviceContext, modelIdToDelete);
    }


    @Override protected SingleModelResult<Module> loadModel( )
    {
        return DaoFactory.getInstance( ).getModuleDao( ).readById( this.modelIdToDelete );
    }

    @Override protected NoContentResult deleteModel( )
    {
        DaoFactory.getInstance( ).getPartnerUniversityModuleDao( ).deleteRelationsToSecondary( this.modelIdToDelete );
        return DaoFactory.getInstance( ).getPartnerUniversityDao( ).delete( this.modelIdToDelete );
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( PartnerUniversityUri.REL_PATH, PartnerUniversityRelTypes.GET_ALL_PARTNERUNIVERSITIES, getAcceptRequestHeader( ) );
    }
}
