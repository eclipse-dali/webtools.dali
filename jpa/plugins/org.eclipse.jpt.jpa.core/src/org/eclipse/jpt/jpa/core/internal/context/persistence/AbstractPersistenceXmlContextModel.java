/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.GenericPersistenceXmlDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.GenericPersistenceXmlDefinition2_1;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceXmlContextModelFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.PersistenceXmlContextModelFactory2_1;

/**
 * Use this abstract class for context models that are part of an
 * <code>persistence.xml</code> file.
 */
public abstract class AbstractPersistenceXmlContextModel<P extends JpaContextModel>
	extends AbstractJpaContextModel<P>
{
	// ********** constructor **********

	protected AbstractPersistenceXmlContextModel(P parent) {
		super(parent);
	}


	// ********** convenience methods **********

	protected PersistenceXmlDefinition getPersistenceXmlDefinition() {
		return (PersistenceXmlDefinition) this.getJpaPlatform().getResourceDefinition(this.getResourceType());
	}

	protected EFactory getResourceModelFactory() {
		return this.getPersistenceXmlDefinition().getResourceModelFactory();
	}

	protected boolean isPersistenceXml2_0Compatible() {
		return this.getResourceType().isKindOf(GenericPersistenceXmlDefinition2_0.instance().getResourceType());
	}

	protected boolean isPersistenceXml2_1Compatible() {
		return this.getResourceType().isKindOf(GenericPersistenceXmlDefinition2_1.instance().getResourceType());
	}

	/**
	 * Call {@link #isPersistenceXml2_0Compatible()} before calling this method.
	 */
	protected PersistenceXmlContextModelFactory2_0 getContextModelFactory2_0() {
		return (PersistenceXmlContextModelFactory2_0) this.getContextModelFactory();
	}

	/**
	 * Call {@link #isPersistenceXml2_1Compatible()} before calling this method.
	 */
	protected PersistenceXmlContextModelFactory2_1 getContextModelFactory2_1() {
		return (PersistenceXmlContextModelFactory2_1) this.getContextModelFactory();
	}

	protected PersistenceXmlContextModelFactory getContextModelFactory() {
		return this.getPersistenceXmlDefinition().getContextModelFactory();
	}
}
