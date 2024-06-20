package de.fhws.fiw.fds.sutton.server;


import com.github.javafaker.Faker;
import de.fhws.fiw.fds.suttondemo.client.models.PartnerUniversityClientModel;
import de.fhws.fiw.fds.suttondemo.client.rest.DemoRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEx3AppIT {
    final private Faker faker = new Faker();
    private DemoRestClient client;

    @BeforeEach
    public void setUp() throws IOException{
       this.client = new DemoRestClient();
       this.client.resetDatabase();
    }

    @Test
    public void test_dispatcher_is_available() throws IOException {
        client.start();
        assertEquals(200, client.getLastStatusCode());
    }

    @Test
    public void test_dispatcher_is_get_all_partner_universities_allowed() throws IOException {
        client.start();
        assertTrue(client.isGetAllPartnerUniversitiesAllowed());
    }

    @Test
    public void test_create_partner_university_is_create_partner_university_allowed() throws IOException {
        client.start();
        assertTrue(client.isCreatePartnerUniversityAllowed());
    }

    @Test void test_create_partner_university() throws IOException
    {
        client.start();

        var partnerUniversity = new PartnerUniversityClientModel();
        partnerUniversity.setName("Musteruni");
        partnerUniversity.setCountry("Musterland");
        partnerUniversity.setDepartmentName("Musterdepartment");
        partnerUniversity.setContactPerson("Mustermann");
        partnerUniversity.setDepartmentUrl("muster@mann.de");
        partnerUniversity.setNextAutumnSemesterStart(LocalDate.of( 1990, 1, 1));
        partnerUniversity.setNextSpringSemesterStart(LocalDate.of( 1990, 8, 1));
        partnerUniversity.setStudentsToAccept(10);
        partnerUniversity.setStudentsToSend(2);
        client.createPartnerUniversity(partnerUniversity);
        assertEquals(201, client.getLastStatusCode());
    }

    @Test void test_create_partner_university_and_get_new_partner_university() throws IOException
    {
        client.start();

        var partnerUniversity = new PartnerUniversityClientModel();
        partnerUniversity.setName("Musteruni");
        partnerUniversity.setCountry("Musterland");
        partnerUniversity.setDepartmentName("Musterdepartment");
        partnerUniversity.setContactPerson("Mustermann");
        partnerUniversity.setDepartmentUrl("muster@mann.de");
        partnerUniversity.setNextAutumnSemesterStart(LocalDate.of( 1990, 1, 1));
        partnerUniversity.setNextSpringSemesterStart(LocalDate.of( 1990, 8, 1));
        partnerUniversity.setStudentsToAccept(10);
        partnerUniversity.setStudentsToSend(2);
        client.createPartnerUniversity(partnerUniversity);

        client.createPartnerUniversity(partnerUniversity);
        assertEquals(201, client.getLastStatusCode());
        assertTrue( client.isGetSinglePartnerUniversityAllowed() );

        client.getSinglePartnerUniversity();
        assertEquals(200, client.getLastStatusCode());

        var partnerUniversityFromServer = client.partnerUniversityData().getFirst();
        assertEquals( "Mustermann", partnerUniversityFromServer.getName() );
    }

    @Test void test_create_5_partner_universities_and_get_all() throws IOException
    {
        /*
         * The next statements look strange, because we call the dispatcher in all
         * iterations. But this is how the client works. The dispatcher is the entry point
         * and we need to call it in order to get the URL to create a new partnerUniversity.
         */
        for( int i=0; i<5; i++ ) {
            client.start();

            var partnerUniversity = new PartnerUniversityClientModel();
            partnerUniversity.setName(faker.university().name());
            partnerUniversity.setCountry(faker.country().name());
            partnerUniversity.setDepartmentName(faker.university().prefix());
            partnerUniversity.setContactPerson(faker.name().fullName());
            partnerUniversity.setDepartmentUrl(faker.internet().emailAddress());
            partnerUniversity.setNextAutumnSemesterStart(LocalDate.of( 1990, 1, 1));
            partnerUniversity.setNextSpringSemesterStart(LocalDate.of( 1990, 8, 1));
            partnerUniversity.setStudentsToAccept(faker.number().numberBetween(0, 100));
            partnerUniversity.setStudentsToSend(faker.number().numberBetween(0, 100));

            client.createPartnerUniversity(partnerUniversity);
            assertEquals(201, client.getLastStatusCode());
        }

        /* Now we call the dispatcher to get the URL to get all partner_universities */
        client.start();
        assertTrue( client.isGetAllPartnerUniversitiesAllowed() );

        client.getAllPartnerUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(5, client.partnerUniversityData().size());

        /* Set the cursor to the first partnerUniversity, not really necessary, but to make it clear here */
        client.setPartnerUniversityCursor(0);
        client.getSinglePartnerUniversity();
        assertEquals(200, client.getLastStatusCode());
    }
}
