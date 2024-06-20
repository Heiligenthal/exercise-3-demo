package de.fhws.fiw.fds.suttondemo.server.database;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.PartnerUniversity;

public interface PartnerUniversityDao extends IDatabaseAccessObject<PartnerUniversity>{
    CollectionModelResult<PartnerUniversity> readByName(String name,
                                                             SearchParameter searchParameter);

    void resetDatabase();

}
