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
package org.springframework.samples.petclinic.owner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.commands.NewPetCommand;

import com.byoskill.spring.cqrs.annotations.CommandService;
import com.byoskill.spring.cqrs.api.CommandServiceSpec;
import com.byoskill.spring.cqrs.gate.api.EventBusService;

@CommandService
public class NewPetCommandService implements CommandServiceSpec<NewPetCommand, Pet> {

    public static class EventPetCreated {

	private final Pet pet;

	public EventPetCreated(final Pet pet) {
	    this.pet = pet;
	}

	public Pet getPet() {
	    return pet;
	}

	@Override
	public String toString() {
	    return "EventPetCreated [pet=" + pet + "]";
	}

    }

    private final OwnerRepository ownerRepository;

    private final EventBusService eventBusService;

    @Autowired
    public NewPetCommandService(final OwnerRepository ownerRepository, final EventBusService eventBusService) {
	super();
	this.ownerRepository = ownerRepository;
	this.eventBusService = eventBusService;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.byoskill.spring.cqrs.api.CommandServiceSpec#handle(java.lang.Object)
     */
    @Override
    public Pet handle(final NewPetCommand command) throws RuntimeException {
	final Owner owner = ownerRepository.findById(command.getOwnerId());
	if (owner == null) {
	    throw new OwnerNotFoundException(command.getOwnerId());
	}

	final Pet pet = new Pet();
	owner.addPet(pet);

	eventBusService.publishEvent(new EventPetCreated(pet));
	return pet;
    }

}
