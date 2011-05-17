/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;

/**
 * Use this abstract class for context nodes that are part of an
 * <code>orm.xml</code> file.
 * This will not work for a pure {@link org.eclipse.jpt.jpa.core.context.MappingFile}
 * implementation.
 */
public abstract class AbstractOrmXmlContextNode
	extends AbstractXmlContextNode
{
	protected AbstractOrmXmlContextNode(JpaContextNode parent) {
		super(parent);
	}


	// ********** convenience methods **********

	@Override
	public EntityMappings getMappingFileRoot() {
		return (EntityMappings) super.getMappingFileRoot();
	}

	// TODO bjv need to cascade up containment hierarchy... (need public API:
	// JpaContextNode.getMappingFileDefinition() ?)
	protected OrmXmlDefinition getMappingFileDefinition() {
		OrmXml ormXml = this.getMappingFileRoot().getParent();
		if (ormXml instanceof GenericOrmXml) {
			return ((GenericOrmXml) ormXml).getMappingFileDefinition();
		}
		return (OrmXmlDefinition) this.getJpaPlatform().getResourceDefinition(this.getResourceType());
	}

	protected EFactory getResourceNodeFactory() {
		return this.getMappingFileDefinition().getResourceNodeFactory();
	}

	// TODO bjv need to add API to orm.xml/entity mappings interface...
	protected boolean isOrmXml2_0Compatible() {
		String version = this.getMappingFileRoot().getVersion();
		return (version != null) && version.equals("2.0"); //$NON-NLS-1$
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
