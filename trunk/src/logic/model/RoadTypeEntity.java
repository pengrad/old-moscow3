package logic.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * User: Стас
 * Date: 19.09.2010
 * Time: 2:16:12
 */

@javax.persistence.Table(name = "road_type", catalog = "rzd")
@Entity
public class RoadTypeEntity {


    private int idType;

    @javax.persistence.Column(name = "id_type", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    private String name;

    @javax.persistence.Column(name = "name", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadTypeEntity that = (RoadTypeEntity) o;

        if (idType != that.idType) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idType;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    private Collection<RoadEntity> roads;

    @OneToMany(mappedBy = "roadType")
    public Collection<RoadEntity> getRoads() {
        return roads;
    }

    public void setRoads(Collection<RoadEntity> roads) {
        this.roads = roads;
    }
}