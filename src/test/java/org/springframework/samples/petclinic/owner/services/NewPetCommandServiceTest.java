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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.owner.commands.NewPetCommand;
import org.springframework.test.context.junit4.SpringRunner;

import com.byoskill.spring.cqrs.gate.api.Gate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewPetCommandServiceTest {

    @Autowired
    private Gate gate;

    @Test
    public void testHandle() throws Exception {
	Assert.assertNotNull(gate.dispatch(new NewPetCommand(1)));
    }

}
