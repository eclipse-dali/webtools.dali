/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.persistence;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel;

/**
 * Use this abstract class for context models that are part of an
 * <code>persistence.xml</code> file.
 */
public abstract class AbstractPersistenceXmlContextModel
	extends AbstractJpaContextModel
{
	// ********** constructor **********

	protected AbstractPersistenceXmlContextModel(JpaContextModel parent) {
		super(parent);
	}


	// ********** convenience methods **********

	protected PersistenceXmlDefinition getPersistenceXmlDefinition() {
		return (PersistenceXmlDefinition) this.getJpaPlatform().getResourceDefinition(this.getResourceType());
	}

	protected EFactory getResourceModelFactory() {
		return this.getPersistenceXmlDefinition().getResourceModelFactory();
	}

	protected PersistenceXmlContextModelFactory getContextModelFactory() {
		return this.getPersistenceXmlDefinition().getContextModelFactory();
	}
}
