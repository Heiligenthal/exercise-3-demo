package de.fhws.fiw.fds.suttondemo.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SecondarySelfLink;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

@JsonRootName("module")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "module")
public class Module extends AbstractModel{
    private String name;
    private int semester;
    private int creditPoints;
    @SecondarySelfLink(
            primaryPathElement = "partneruniversities",
            secondaryPathElement = "modules"
    )
    private transient Link selfLinkOnSecond;
    @SelfLink(pathElement = "modules")
    private transient Link selfLink;

    // Default constructor
    public Module() {}

    // Parameterized constructor
    public Module(String name, int semester, int creditPoints) {
        System.out.println("modul");
        System.out.println(selfLinkOnSecond.toString());
        this.name = name;
        this.semester = semester;
        this.creditPoints = creditPoints;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }
    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                ", creditpoints=" + creditPoints +
                ", self{" +
                    "href=" + selfLink.getHref() +
                    "rel=" + selfLink.getRel() +
                    "type=" + selfLink.getType() +
                    "}" +
                '}';
    }
}
