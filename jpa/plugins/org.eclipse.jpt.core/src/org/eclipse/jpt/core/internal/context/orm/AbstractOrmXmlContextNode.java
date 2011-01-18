/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;

/**
 * Use this abstract class for context nodes that are part of an
 * <code>orm.xml</code> file.
 * This will not work for a pure {@link org.eclipse.jpt.core.context.MappingFile}
 * implementation.
 */
public abstract class AbstractOrmXmlContextNode
	extends AbstractXmlContextNode
{
	protected AbstractOrmXmlContextNode(JpaContextNode parent) {
		super(parent);
	}


	// ********** convenience methods **********

	protected OrmXmlDefinition getMappingFileDefinition() {
		return (OrmXmlDefinition) this.getJpaPlatform().getResourceDefinition(this.getResourceType());
	}

	protected EFactory getResourceNodeFactory() {
		return this.getMappingFileDefinition().getResourceNodeFactory();
	}

	/**
	 * Call {@link #isJpa2_0Compatible()} before calling this method.
	 */
	protected OrmXml2_0ContextNodeFactory getContextNodeFactory2_0() {
		return (OrmXml2_0ContextNodeFactory) this.getContextNodeFactory();
	}

	protected OrmXmlContextNodeFactory getContextNodeFactory() {
		return this.getMappingFileDefinition().getContextNodeFactory();
	}
}
