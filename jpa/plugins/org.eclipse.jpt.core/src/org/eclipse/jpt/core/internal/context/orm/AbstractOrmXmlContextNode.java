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
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;

/**
 * Use this abstract class for context nodes that are part of an OrmXml file.
 * This will not work for purely MappingFile implementations
 */
public abstract class AbstractOrmXmlContextNode
	extends AbstractXmlContextNode
{

	// ********** constructor **********

	protected AbstractOrmXmlContextNode(JpaContextNode parent) {
		super(parent);
	}

	protected OrmXmlDefinition getMappingFileDefinition() {
		return (OrmXmlDefinition) this.getJpaPlatform().getResourceDefinition(this.getResourceType());
	}

	protected EFactory getResourceNodeFactory() {
		return this.getMappingFileDefinition().getResourceNodeFactory();
	}

	protected OrmXml2_0ContextNodeFactory getXmlContextNodeFactory() {
		return (OrmXml2_0ContextNodeFactory) this.getMappingFileDefinition().getContextNodeFactory();
	}
}
