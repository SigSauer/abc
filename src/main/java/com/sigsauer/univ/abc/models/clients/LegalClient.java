package com.sigsauer.univ.abc.models.clients;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "legal_clients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "edrpou"),
        })
public class LegalClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String edrpou;

    @Column
    private LocalDate dor;

    @Column
    @NotBlank
    private String address;

    @Column
    private String activity;

    @Column
    @NotBlank
    private String leadName;

    @Column
    @NotBlank
    private String leadMobile;

    @Column
    private String leadEmail;

    @Column
    @NotNull
    private Boolean active;


    public LegalClient(String name, String edrpou, LocalDate dor, String address, String leadName, String leadMobile) {
        this.name = name;
        this.edrpou = edrpou;
        this.dor = dor;
        this.address = address;
        this.leadName = leadName;
        this.leadMobile = leadMobile;
        this.active = true;
    }

    public LegalClient(Long id, String title, String name, String edrpou, LocalDate dor, String address, String activity,
                       String leadName, String leadMobile, String leadEmail, boolean active) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.edrpou = edrpou;
        this.dor = dor;
        this.address = address;
        this.activity = activity;
        this.leadName = leadName;
        this.leadMobile = leadMobile;
        this.leadEmail = leadEmail;
        this.active = active;
    }

    public LegalClient() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdrpou() {
        return edrpou;
    }

    public void setEdrpou(String edrpou) {
        this.edrpou = edrpou;
    }

    public LocalDate getDor() {
        return dor;
    }

    public void setDor(LocalDate dor) {
        this.dor = dor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLeadMobile() {
        return leadMobile;
    }

    public void setLeadMobile(String leadMobile) {
        this.leadMobile = leadMobile;
    }

    public String getLeadEmail() {
        return leadEmail;
    }

    public void setLeadEmail(String leadEmail) {
        this.leadEmail = leadEmail;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
