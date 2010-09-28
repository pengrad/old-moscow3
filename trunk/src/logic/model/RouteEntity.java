package logic.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * User: Стас
 * Date: 28.09.2010
 * Time: 22:09:50
 */

@javax.persistence.Table(name = "route", catalog = "rzd")
@Entity
public class RouteEntity {


    private int idRoute;

    @javax.persistence.Column(name = "id_route", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    private String numberForward;

    @javax.persistence.Column(name = "number_forward", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getNumberForward() {
        return numberForward;
    }

    public void setNumberForward(String numberForward) {
        this.numberForward = numberForward;
    }

    private String numberBack;

    @javax.persistence.Column(name = "number_back", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getNumberBack() {
        return numberBack;
    }

    public void setNumberBack(String numberBack) {
        this.numberBack = numberBack;
    }

    private String cityFrom;

    @javax.persistence.Column(name = "city_from", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    private String cityTo;

    @javax.persistence.Column(name = "city_to", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteEntity that = (RouteEntity) o;

        if (cityFrom != that.cityFrom) return false;
        if (cityTo != that.cityTo) return false;
        if (idRoute != that.idRoute) return false;
        if (numberBack != null ? !numberBack.equals(that.numberBack) : that.numberBack != null) return false;
        if (numberForward != null ? !numberForward.equals(that.numberForward) : that.numberForward != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRoute;
        result = 31 * result + (numberForward != null ? numberForward.hashCode() : 0);
        result = 31 * result + (numberBack != null ? numberBack.hashCode() : 0);
        result = 31 * result + cityFrom.hashCode();
        result = 31 * result + cityTo.hashCode();
        return result;
    }

    private SheduleEntity sheduleBack;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "shedule_back", referencedColumnName = "id_shedule", nullable = false)
    SheduleEntity getSheduleBack() {
        return sheduleBack;
    }

    public void setSheduleBack(SheduleEntity sheduleBack) {
        this.sheduleBack = sheduleBack;
    }

    private SheduleEntity sheduleForward;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "shedule_forward", referencedColumnName = "id_shedule", nullable = false)
    SheduleEntity getSheduleForward() {
        return sheduleForward;
    }

    public void setSheduleForward(SheduleEntity sheduleForward) {
        this.sheduleForward = sheduleForward;
    }
}