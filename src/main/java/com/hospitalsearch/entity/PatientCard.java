package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "patientcard")
public class PatientCard{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientcard_gen")
    @SequenceGenerator(name = "patientcard_gen", sequenceName = "patientcard_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "patientCard", fetch = FetchType.EAGER)
    List<CardItem> cardItems;

    @OneToOne
    @JoinColumn(name = "patientinfo_id")
    private PatientInfo patientInfo;

    public PatientCard(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public PatientCard() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CardItem> getCardItems() {
        return cardItems;
    }

    public void setCardItems(List<CardItem> cardItems) {
        this.cardItems = cardItems;
    }

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    @Override
    public String toString() {
        return "PatientCard{" +
                "id=" + id +
                '}';
    }
	   
}
