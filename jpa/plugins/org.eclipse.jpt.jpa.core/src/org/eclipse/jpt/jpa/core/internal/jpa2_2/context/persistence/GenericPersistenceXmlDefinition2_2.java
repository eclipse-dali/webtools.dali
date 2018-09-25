/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.GenericPersistenceXmlContextModelFactory2_1;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Factory;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_2.JPA2_2;

public class GenericPersistenceXmlDefinition2_2 extends AbstractPersistenceXmlDefinition {
	// singleton
	private static final PersistenceXmlDefinition INSTANCE = new GenericPersistenceXmlDefinition2_2();

	/**
	 * Return the singleton
	 */
	public static PersistenceXmlDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private GenericPersistenceXmlDefinition2_2() {
		super();
	}

	public JptResourceType getResourceType() {
		return this.getResourceType(XmlPersistence.CONTENT_TYPE, JPA2_2.SCHEMA_VERSION);
	}

	public EFactory getResourceModelFactory() {
		return PersistenceV2_0Factory.eINSTANCE;
	}

	@Override
	protected PersistenceXmlContextModelFactory buildContextModelFactory() {
		return new GenericPersistenceXmlContextModelFactory2_2();
	}
}
