package com.sigsauer.univ.abc.models.clients;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "natural_clients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "mobile"),
                @UniqueConstraint(columnNames = "tin"),
                @UniqueConstraint(columnNames = "docNumber")
        })
public class NaturalClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //GENERAL

    @Column
    @NotBlank
    private String mobile;

    @Column
    private String email;

    //PERSONALS

    @Column
    @NotBlank
    private String name;

    @Column
    private boolean sex;

    @Column
    private LocalDate dob;

    //DOCUMENTS

    @Column
    @NotBlank
    private String tin;

    @Column
    @NotBlank
    private String docNumber;

    @Column
    @NotBlank
    private String docIssuers;

    //ADDRESSES

    @Column
    private String regAddress;
    @Column
    private String resAddress;

    //EMPLOYMENT

    @Column
    private String work;
    @Column
    private String workName;

    @Column()
    @NotNull
    private boolean active;

    public NaturalClient() {
    }

    public NaturalClient(Long id, String mobile, String email, String name, boolean sex, LocalDate dob, String tin,
                         String docNumber, String docIssuers, String regAddress, String resAddress, String work,
                         String workName, boolean active) {
        this.id = id;
        this.mobile = mobile;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.tin = tin;
        this.docNumber = docNumber;
        this.docIssuers = docIssuers;
        this.regAddress = regAddress;
        this.resAddress = resAddress;
        this.work = work;
        this.workName = workName;
        this.active = active;
    }

    public NaturalClient(String mobile, String name, boolean sex,
                         LocalDate dob, String tin, String docNumber, String docIssuers) {
        this.mobile = mobile;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.tin = tin;
        this.docNumber = docNumber;
        this.docIssuers = docIssuers;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocIssuers() {
        return docIssuers;
    }

    public void setDocIssuers(String docIssuers) {
        this.docIssuers = docIssuers;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getResAddress() {
        return resAddress;
    }

    public void setResAddress(String resAddress) {
        this.resAddress = resAddress;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
