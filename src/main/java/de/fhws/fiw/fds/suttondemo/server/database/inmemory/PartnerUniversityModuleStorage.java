package de.fhws.fiw.fds.suttondemo.server.database.inmemory;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryRelationStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.database.ModuleDao;
import de.fhws.fiw.fds.suttondemo.server.database.PartnerUniversityModuleDao;

public class PartnerUniversityModuleStorage extends AbstractInMemoryRelationStorage<Module> implements PartnerUniversityModuleDao {
    final private ModuleDao moduleStorage;

    public PartnerUniversityModuleStorage(ModuleDao moduleStorage) {
        this.moduleStorage = moduleStorage;
    }

    @Override
    protected IDatabaseAccessObject<Module> getSecondaryStorage() {
        return this.moduleStorage;
    }

    @Override
    public CollectionModelResult<Module> readByName(long partnerUniversityId, String name, SearchParameter searchParameter) {
        return InMemoryPaging.page(
                this.readAllLinkedByPredicate(partnerUniversityId, (m) -> name.isEmpty() || m.getName().equals(name)),
                searchParameter.getOffset(), searchParameter.getSize()
        );
    }

    @Override
    public CollectionModelResult<Module> readAllLinked(long partnerUniversityId, SearchParameter searchParameter) {
        return InMemoryPaging.page(
                this.readAllLinkedByPredicate(partnerUniversityId, (m) -> true),
                searchParameter.getOffset(), searchParameter.getSize()
        );
    }

    @Override
    public void resetDatabase() {
        this.storage.clear();
        this.moduleStorage.readAll().getResult().clear();
    }

    @Override
    public void initializeDatabase() {
        // Implementieren Sie die Initialisierung der Datenbank, falls erforderlich
    }
}
