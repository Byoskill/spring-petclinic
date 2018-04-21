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
package org.springframework.samples.petclinic.owner.commands;

import javax.validation.constraints.NotNull;

/**
 * The Class NewPetCommand describes the action to create a new pet.
 */
public class NewPetCommand {

    /** The owner id. */
    @NotNull
    private Integer ownerId;

    /**
     * Instantiates a new new pet command.
     *
     * @param ownerId
     *            the owner id
     */
    public NewPetCommand(final Integer ownerId) {
	super();
	this.ownerId = ownerId;
    }

    /**
     * Gets the owner id.
     *
     * @return the owner id
     */
    public Integer getOwnerId() {
	return ownerId;
    }

    /**
     * Sets the owner id.
     *
     * @param ownerId
     *            the new owner id
     */
    public void setOwnerId(final Integer ownerId) {
	this.ownerId = ownerId;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "NewPetCommand [ownerId=" + ownerId + "]";
    }
}
