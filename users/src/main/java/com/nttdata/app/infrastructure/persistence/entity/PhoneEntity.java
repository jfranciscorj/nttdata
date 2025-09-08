package com.nttdata.app.infrastructure.persistence.entity;

import com.nttdata.app.core.domain.user.PhoneModel;
import jakarta.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(name="city_code")
    private String cityCode;

    @Column(name="country_code")
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public static PhoneEntity fromDomain(PhoneModel phone) {
        PhoneEntity entity = new PhoneEntity();
        entity.number = phone.getNumber();
        entity.cityCode = phone.getCityCode();
        entity.countryCode = phone.getCountryCode();
        return entity;
    }

    public PhoneModel toDomain() {
        PhoneModel model = new PhoneModel();
        model.setId(id);
        model.setNumber(number);
        model.setCityCode(cityCode);
        model.setCountryCode(countryCode);
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
