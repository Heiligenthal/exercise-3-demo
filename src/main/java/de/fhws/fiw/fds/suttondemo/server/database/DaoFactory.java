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

package de.fhws.fiw.fds.suttondemo.server.database;


import de.fhws.fiw.fds.suttondemo.server.database.inmemory.LocationStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.PartnerUniversityModuleStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.PartnerUniversityStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.PersonLocationStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.PersonStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.ModuleStorage;

public class DaoFactory {

    private static DaoFactory INSTANCE;

    public static DaoFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DaoFactory();
        }

        return INSTANCE;
    }

    private final PersonDao personDao;

    private final LocationDao locationDao;

    private final PersonLocationDao personLocationDao;

    private final PartnerUniversityDao partnerUniversityDao;

    private final ModuleDao moduleDao;

    private final PartnerUniversityModuleDao partnerUniversityModuleDao;

    private DaoFactory() {
        this.personDao = new PersonStorage();
        this.locationDao = new LocationStorage();
        this.personLocationDao = new PersonLocationStorage(this.locationDao);
        this.partnerUniversityDao = new PartnerUniversityStorage();
        this.moduleDao = new ModuleStorage();
        this.partnerUniversityModuleDao = new PartnerUniversityModuleStorage(this.moduleDao);
    }

    public PersonDao getPersonDao() {
        return this.personDao;
    }

    public LocationDao getLocationDao() {
        return this.locationDao;
    }

    public PersonLocationDao getPersonLocationDao() {
        return this.personLocationDao;
    }

    public PartnerUniversityDao getPartnerUniversityDao() {
        return this.partnerUniversityDao;
    }
    public ModuleDao getModuleDao(){
        return this.moduleDao;
    }
    public PartnerUniversityModuleDao getPartnerUniversityModuleDao() {
        return this.partnerUniversityModuleDao;
    }
}
