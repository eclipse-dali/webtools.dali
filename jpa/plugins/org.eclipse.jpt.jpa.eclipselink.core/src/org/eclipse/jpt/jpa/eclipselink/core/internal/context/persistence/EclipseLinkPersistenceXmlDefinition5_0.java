/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa3_2.context.persistence.GenericPersistenceXmlDefinition3_2;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Factory;

/**
 * Binds the EclipseLink persistence unit properties to a Jakarta Persistence
 * 3.2 <code>persistence.xml</code>, which is what an EclipseLink 5.0 project uses.
 */
public class EclipseLinkPersistenceXmlDefinition5_0
	extends AbstractPersistenceXmlDefinition
{
	// singleton
	private static final PersistenceXmlDefinition INSTANCE = new EclipseLinkPersistenceXmlDefinition5_0();

	/**
	 * Return the singleton
	 */
	public static PersistenceXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkPersistenceXmlDefinition5_0() {
		super();
	}

	public EFactory getResourceModelFactory() {
		return PersistenceV2_0Factory.eINSTANCE;
	}

	public JptResourceType getResourceType() {
		return GenericPersistenceXmlDefinition3_2.instance().getResourceType();
	}

	@Override
	protected PersistenceXmlContextModelFactory buildContextModelFactory() {
		return new EclipseLinkPersistenceXmlContextModelFactory5_0();
	}
}
