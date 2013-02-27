/*******************************************************************************
* Copyright (c) 2012, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.GenericPersistenceXml2_1Definition;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Factory;

public class EclipseLink2_5PersistenceXmlDefinition
	extends AbstractPersistenceXmlDefinition
{
	// singleton
	private static final PersistenceXmlDefinition INSTANCE = new EclipseLink2_5PersistenceXmlDefinition();

	/**
	 * Return the singleton
	 */
	public static PersistenceXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLink2_5PersistenceXmlDefinition() {
		super();
	}
	
	public EFactory getResourceNodeFactory() {
		return PersistenceV2_0Factory.eINSTANCE;
	}

	public JptResourceType getResourceType() {
		return GenericPersistenceXml2_1Definition.instance().getResourceType();
	}

	@Override
	protected PersistenceXmlContextNodeFactory buildContextNodeFactory() {
		return new EclipseLink2_5PersistenceXmlContextNodeFactory();
	}
}
