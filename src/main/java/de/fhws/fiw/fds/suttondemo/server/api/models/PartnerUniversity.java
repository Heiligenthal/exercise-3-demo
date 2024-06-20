package de.fhws.fiw.fds.suttondemo.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@JsonRootName("partneruniversity")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "partneruniversity")

public class PartnerUniversity extends AbstractModel{
    private String name;
    private String country;
    private String departmentName;
    private String departmentUrl;
    private String contactPerson;
    private int studentsToSend;
    private int studentsToAccept;
    private LocalDate nextSpringSemesterStart;
    private LocalDate nextAutumnSemesterStart;
    @SuttonLink(
        value = "partneruniversities/${id}/modules",
        rel = "getModulesOfPartnerUniversity"
    )
    private transient Link modules;
    @SuttonLink(
        value = "partneruniversities/${id}",
        rel = "self"
    )
    private transient Link selfLink;

    // Default constructor
    public PartnerUniversity() {}

    // Parameterized constructor
    public PartnerUniversity(final String name,final  String country,final String departmentName, 
                             final String departmentUrl, final String contactPerson, final int studentsToSend, 
                             final int studentsToAccept, final LocalDate nextSpringSemesterStart, 
                             final LocalDate nextAutumnSemesterStart) {
        this.name = name;
        this.country = country;
        this.departmentName = departmentName;
        this.departmentUrl = departmentUrl;
        this.contactPerson = contactPerson;
        this.studentsToSend = studentsToSend;
        this.studentsToAccept = studentsToAccept;
        this.nextSpringSemesterStart = nextSpringSemesterStart;
        this.nextAutumnSemesterStart = nextAutumnSemesterStart;
    }

    // Getters and Setters
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentUrl() {
        return departmentUrl;
    }

    public void setDepartmentUrl(String departmentUrl) {
        this.departmentUrl = departmentUrl;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getStudentsToSend() {
        return studentsToSend;
    }

    public void setStudentsToSend(int studentsToSend) {
        this.studentsToSend = studentsToSend;
    }

    public int getStudentsToAccept() {
        return studentsToAccept;
    }

    public void setStudentsToAccept(int studentsToAccept) {
        this.studentsToAccept = studentsToAccept;
    }

    public LocalDate getNextSpringSemesterStart() {
        return nextSpringSemesterStart;
    }

    public void setNextSpringSemesterStart(LocalDate nextSpringSemesterStart) {
        this.nextSpringSemesterStart = nextSpringSemesterStart;
    }

    public LocalDate getNextAutumnSemesterStart() {
        return nextAutumnSemesterStart;
    }

    public void setNextAutumnSemesterStart(LocalDate nextAutumnSemesterStart) {
        this.nextAutumnSemesterStart = nextAutumnSemesterStart;
    }

    public Link getModules() {
        return modules;
    }

    public void setModules(Link modules) {
        this.modules = modules;
    }
    @Override
    public String toString() {
        System.out.println("toString");
        return "PartnerUniversity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentUrl='" + departmentUrl + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", studentsToSend=" + studentsToSend +
                ", studentsToAccept=" + studentsToAccept +
                ", nextSpringSemesterStart=" + nextSpringSemesterStart +
                ", nextAutumnSemesterStart=" + nextAutumnSemesterStart +
                '}';
    }
    
    
}
