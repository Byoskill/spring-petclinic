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

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.commands.NewPetCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.byoskill.spring.cqrs.gate.api.Gate;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
class PetController {

    private static final String	  VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetRepository	  pets;
    private final OwnerRepository owners;
    private final Gate		  gate;

    @Autowired
    public PetController(final PetRepository pets, final OwnerRepository owners, final Gate gate) {
	this.pets = pets;
	this.owners = owners;
	this.gate = gate;
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") final int ownerId) {
	return owners.findById(ownerId);
    }

    // @GetMapping("/pets/new")
    // public String initCreationForm(final Owner owner, final ModelMap model) {
    // final Pet pet = new Pet();
    // owner.addPet(pet);
    // model.put("pet", pet);
    // return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    // }

    @GetMapping("/pets/new")
    public String initCreationForm(@PathVariable("ownerId") final int ownerId,
	    final ModelMap model) {
	final NewPetCommand newPetCommand = new NewPetCommand(ownerId);
	final Pet pet = gate.dispatch(newPetCommand, Pet.class);
	model.put("pet", pet);
	return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @InitBinder("owner")
    public void initOwnerBinder(final WebDataBinder dataBinder) {
	dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(final WebDataBinder dataBinder) {
	dataBinder.setValidator(new PetValidator());
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) {
	final Pet pet = pets.findById(petId);
	model.put("pet", pet);
	return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
	return pets.findPetTypes();
    }

    @PostMapping("/pets/new")
    public String processCreationForm(final Owner owner, @Valid final Pet pet, final BindingResult result,
	    final ModelMap model) {
	if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
	    result.rejectValue("name", "duplicate", "already exists");
	}
	owner.addPet(pet);
	if (result.hasErrors()) {
	    model.put("pet", pet);
	    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	} else {
	    pets.save(pet);
	    return "redirect:/owners/{ownerId}";
	}
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid final Pet pet, final BindingResult result, final Owner owner,
	    final ModelMap model) {
	if (result.hasErrors()) {
	    pet.setOwner(owner);
	    model.put("pet", pet);
	    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	} else {
	    owner.addPet(pet);
	    pets.save(pet);
	    return "redirect:/owners/{ownerId}";
	}
    }

}
