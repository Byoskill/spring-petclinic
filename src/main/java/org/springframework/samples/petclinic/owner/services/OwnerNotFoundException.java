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

public class OwnerNotFoundException extends RuntimeException {

    /**
     * Instantiates a new owner not found exception.
     *
     * @param ownerId
     *            the owner id
     */
    public OwnerNotFoundException(final Integer ownerId) {
	super("Owner was not found " + ownerId);
    }

}
