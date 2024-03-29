package logic.model;

import javax.persistence.*;

/**
 * User: Стас
 * Date: 27.09.2010
 * Time: 2:28:11
 */

@IdClass(SheduleDaysEntityPK.class)
@Table(name = "shedule_days", catalog = "rzd")
@Entity
public class SheduleDaysEntity {

    public SheduleDaysEntity() {        
    }

    public SheduleDaysEntity(int day, SheduleEntity shedule) {
        this.day = day;
        setShedule(shedule);
    }

    private int idShedule;

    @Column(name = "id_shedule", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdShedule() {
        return idShedule;
    }

    public void setIdShedule(int idShedule) {
        this.idShedule = idShedule;
    }

    private int day;

    @Column(name = "day", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SheduleDaysEntity that = (SheduleDaysEntity) o;

        if (day != that.day) return false;
        if (idShedule != that.idShedule) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idShedule;
        result = 31 * result + day;
        return result;
    }

    private SheduleEntity shedule;

    @ManyToOne
    public
    @JoinColumn(name = "id_shedule", referencedColumnName = "id_shedule", nullable = false, insertable = false, updatable = false)
    SheduleEntity getShedule() {
        return shedule;
    }

    public void setShedule(SheduleEntity shedule) {
        this.shedule = shedule;
        this.idShedule = shedule.getIdShedule();
    }
}
