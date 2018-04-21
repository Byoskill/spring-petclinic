/*
 * Copyright (C) 2017 Sylvain Leroy - BYOSkill Company All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the MIT license with
 * this file. If not, please write to: sleroy at byoskill.com, or visit : www.byoskill.com
 *
 */
package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.visit.Visit;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "petId", fetch = FetchType.EAGER)
    private Set<Visit> visits = new LinkedHashSet<>();

    public void addVisit(final Visit visit) {
	getVisitsInternal().add(visit);
	visit.setPetId(getId());
    }

    public Date getBirthDate() {
	return birthDate;
    }

    public Owner getOwner() {
	return owner;
    }

    public PetType getType() {
	return type;
    }

    public List<Visit> getVisits() {
	final List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
	PropertyComparator.sort(sortedVisits,
		new MutableSortDefinition("date", false, false));
	return Collections.unmodifiableList(sortedVisits);
    }

    protected Set<Visit> getVisitsInternal() {
	if (visits == null) {
	    visits = new HashSet<>();
	}
	return visits;
    }

    public void setBirthDate(final Date birthDate) {
	this.birthDate = birthDate;
    }

    protected void setOwner(final Owner owner) {
	this.owner = owner;
    }

    public void setType(final PetType type) {
	this.type = type;
    }

    protected void setVisitsInternal(final Set<Visit> visits) {
	this.visits = visits;
    }

    @Override
    public String toString() {
	return "Pet [birthDate=" + birthDate + ", type=" + type + ", owner=" + owner + ", visits=" + visits + "]";
    }

}
