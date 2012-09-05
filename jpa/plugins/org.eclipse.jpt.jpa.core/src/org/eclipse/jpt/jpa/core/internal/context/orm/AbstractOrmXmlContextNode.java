/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXml2_0Definition;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;

/**
 * Use this abstract class for context nodes that are part of an
 * <code>orm.xml</code> file.
 * This will not work for a pure {@link org.eclipse.jpt.jpa.core.context.MappingFile}
 * implementation.
 */
public abstract class AbstractOrmXmlContextNode
	extends AbstractJpaContextNode
{
	protected AbstractOrmXmlContextNode(JpaContextNode parent) {
		super(parent);
	}


	// ********** convenience methods **********

	@Override
	public EntityMappings getMappingFileRoot() {
		return (EntityMappings) super.getMappingFileRoot();
	}

	protected OrmXml getOrmXml() {
		return this.getMappingFileRoot().getOrmXml();
	}

	protected OrmXmlDefinition getMappingFileDefinition() {
		return this.getOrmXml().getDefinition();
	}

	protected EFactory getResourceNodeFactory() {
		return this.getMappingFileDefinition().getResourceNodeFactory();
	}

	protected boolean isOrmXml2_0Compatible() {
		return this.getResourceType().isKindOf(GenericOrmXml2_0Definition.instance().getResourceType());
	}

	/**
	 * Call {@link #isOrmXml2_0Compatible()} before calling this method.
	 */
	protected OrmXml2_0ContextNodeFactory getContextNodeFactory2_0() {
		return (OrmXml2_0ContextNodeFactory) this.getContextNodeFactory();
	}

	protected OrmXmlContextNodeFactory getContextNodeFactory() {
		return this.getMappingFileDefinition().getContextNodeFactory();
	}
}
