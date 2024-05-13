/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
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
package org.eclipse.jpt.jpa.core.internal.jpa3_0.context.persistence;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Factory;
import org.eclipse.jpt.jpa.core.resource.persistence.v3_0.JPA3_0;

public class GenericPersistenceXmlDefinition3_0 extends AbstractPersistenceXmlDefinition {
	// singleton
	private static final PersistenceXmlDefinition INSTANCE = new GenericPersistenceXmlDefinition3_0();

	/**
	 * Return the singleton
	 */
	public static PersistenceXmlDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private GenericPersistenceXmlDefinition3_0() {
		super();
	}

	public JptResourceType getResourceType() {
		return this.getResourceType(XmlPersistence.CONTENT_TYPE, JPA3_0.SCHEMA_VERSION);
	}

	public EFactory getResourceModelFactory() {
		return PersistenceV2_0Factory.eINSTANCE;
	}

	@Override
	protected PersistenceXmlContextModelFactory buildContextModelFactory() {
		return new GenericPersistenceXmlContextModelFactory3_0();
	}
}
