package de.fhws.fiw.fds.suttondemo.client.rest;


import de.fhws.fiw.fds.sutton.client.rest2.AbstractRestClient;
import de.fhws.fiw.fds.suttondemo.client.models.LocationClientModel;
import de.fhws.fiw.fds.suttondemo.client.models.PartnerUniversityClientModel;
import de.fhws.fiw.fds.suttondemo.client.models.PersonClientModel;
import de.fhws.fiw.fds.suttondemo.client.web.PartnerUniversityWebClient;
import de.fhws.fiw.fds.suttondemo.client.web.PersonWebClient;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DemoRestClient extends AbstractRestClient {
    private static final String BASE_URL = "http://localhost:8080/ex3/api";
    private static final String GET_ALL_PERSONS = "getAllPersons";
    private static final String CREATE_PERSON = "createPerson";
    private static final String GET_ALL_PARTNERUNIVERSITIES = "getAllPartnerUniversities";
    private static final String CREATE_PARTNERUNIVERSITY = "createPartnerUniversity";


    private List<PersonClientModel> currentPersonData;
    private int cursorPersonData = 0;

    private List<LocationClientModel> currentLocationData;
    private int cursorLocationData = 0;

    private List<PartnerUniversityClientModel> currentPartnerUniversityData;
    private int cursorPartnerUniversityData = 0;


    final private PersonWebClient client;

    final private PartnerUniversityWebClient partnerUniversityWebClient;

    public DemoRestClient() {
        super();
        this.client = new PersonWebClient();
        this.currentPersonData = Collections.EMPTY_LIST;
        this.partnerUniversityWebClient = new PartnerUniversityWebClient();
        this.currentPartnerUniversityData = Collections.EMPTY_LIST;
    }

    public void resetDatabase() throws IOException {
        processResponse(this.client.resetDatabaseOnServer(BASE_URL), (response) -> {
        });
        processResponse(this.partnerUniversityWebClient.resetDatabaseOnServer(BASE_URL), (response) -> {
        });
    }

    public void start() throws IOException {
        processResponse(this.client.getDispatcher(BASE_URL), (response) -> {
        });
        processResponse(this.partnerUniversityWebClient.getDispatcher(BASE_URL), (response) -> {
        });
    }

    public boolean isCreatePersonAllowed() {
        return isLinkAvailable(CREATE_PERSON);
    }

    public boolean isCreatePartnerUniversityAllowed() {
        return isLinkAvailable(CREATE_PARTNERUNIVERSITY);
    }

    public void createPerson(PersonClientModel person) throws IOException {
        if (isCreatePersonAllowed()) {
            processResponse(this.client.postNewPerson(getUrl(CREATE_PERSON), person), (response) -> {
                this.currentPersonData = Collections.EMPTY_LIST;
                this.cursorPersonData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public void createPartnerUniversity(PartnerUniversityClientModel partnerUniversity) throws IOException {
        if (isCreatePartnerUniversityAllowed()) {
            processResponse(this.partnerUniversityWebClient.postNewPartnerUniversity(getUrl(CREATE_PARTNERUNIVERSITY), partnerUniversity), (response) -> {
                this.currentPartnerUniversityData = Collections.EMPTY_LIST;
                this.cursorPartnerUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetAllPersonsAllowed() {
        return isLinkAvailable(GET_ALL_PERSONS);
    }

    public boolean isGetAllPartnerUniversitiesAllowed() {
        return isLinkAvailable(GET_ALL_PARTNERUNIVERSITIES);
    }

    public void getAllPersons() throws IOException {
        if (isGetAllPersonsAllowed()) {
            processResponse(this.client.getCollectionOfPersons(getUrl(GET_ALL_PERSONS)), (response) -> {
                this.currentPersonData = new LinkedList(response.getResponseData());
                this.cursorPersonData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public void getAllPartnerUniversities() throws IOException {
        if (isGetAllPartnerUniversitiesAllowed()) {
            processResponse(this.partnerUniversityWebClient.getCollectionOfPartnerUniversities(getUrl(GET_ALL_PARTNERUNIVERSITIES)), (response) -> {
                this.currentPartnerUniversityData = new LinkedList(response.getResponseData());
                this.cursorPartnerUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }


    public boolean isGetSinglePersonAllowed() {
        return !this.currentPersonData.isEmpty() || isLocationHeaderAvailable();
    }

    public boolean isGetSinglePartnerUniversityAllowed() {
        return !this.currentPartnerUniversityData.isEmpty() || isLocationHeaderAvailable();
    }

    public List<PersonClientModel> personData() {
        if (this.currentPersonData.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.currentPersonData;
    }

    public List<PartnerUniversityClientModel> partnerUniversityData() {
        if (this.currentPartnerUniversityData.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.currentPartnerUniversityData;
    }

    public void setPersonCursor(int index) {
        if (0 <= index && index < this.currentPersonData.size()) {
            this.cursorPersonData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPartnerUniversityCursor(int index) {
        if (0 <= index && index < this.currentPartnerUniversityData.size()) {
            this.cursorPartnerUniversityData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getSinglePerson() throws IOException {
        if ( isLocationHeaderAvailable()) {
            getSinglePerson(getLocationHeaderURL());
        }
        else if (!this.currentPersonData.isEmpty()) {
            getSinglePerson(this.cursorPersonData);
        }
        else {
            throw new IllegalStateException();
        }
    }

    public void getSinglePartnerUniversity() throws IOException {
        if ( isLocationHeaderAvailable()) {
            getSinglePartnerUniversity(getLocationHeaderURL());
        }
        else if (!this.currentPartnerUniversityData.isEmpty()) {
            getSinglePartnerUniversity(this.cursorPartnerUniversityData);
        }
        else {
            throw new IllegalStateException();
        }
    }

    public void getSinglePerson(int index) throws IOException {
        getSinglePerson(this.currentPersonData.get(index).getSelfLink().getUrl());
    }

    public void getSinglePartnerUniversity(int index) throws IOException {
        getSinglePartnerUniversity(this.currentPartnerUniversityData.get(index).getSelfLink().getUrl());
    }

    private void getSinglePerson(String url) throws IOException {
        processResponse(this.client.getSinglePerson(url), (response) -> {
            this.currentPersonData = new LinkedList(response.getResponseData());
            this.cursorPersonData = 0;
        });
    }

    private void getSinglePartnerUniversity(String url) throws IOException {
        processResponse(this.partnerUniversityWebClient.getSinglePartnerUniversity(url), (response) -> {
            this.currentPartnerUniversityData = new LinkedList(response.getResponseData());
            this.cursorPartnerUniversityData = 0;
        });
    }
    /*
     *  The rest of the class is omitted for brevity
     */
}
