/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.GenericOrmXmlDefinition2_1;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmXmlContextModelFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmXmlContextModelFactory2_1;

/**
 * Use this abstract class for context models that are part of an
 * <code>orm.xml</code> file.
 * Do not use this for a pure {@link org.eclipse.jpt.jpa.core.context.MappingFile}
 * implementation.
 */
public abstract class AbstractOrmXmlContextModel<P extends JpaContextModel>
	extends AbstractJpaContextModel<P>
{
	protected AbstractOrmXmlContextModel(P parent) {
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

	protected EFactory getResourceModelFactory() {
		return this.getMappingFileDefinition().getResourceModelFactory();
	}

	protected boolean isOrmXml2_0Compatible() {
		return this.getResourceType().isKindOf(GenericOrmXmlDefinition2_0.instance().getResourceType());
	}

	protected boolean isOrmXml2_1Compatible() {
		return this.getResourceType().isKindOf(GenericOrmXmlDefinition2_1.instance().getResourceType());
	}

	/**
	 * Call {@link #isOrmXml2_0Compatible()} before calling this method.
	 */
	protected OrmXmlContextModelFactory2_0 getContextModelFactory2_0() {
		return (OrmXmlContextModelFactory2_0) this.getContextModelFactory();
	}

	/**
	 * Call {@link #isOrmXml2_1Compatible()} before calling this method.
	 */
	protected OrmXmlContextModelFactory2_1 getContextModelFactory2_1() {
		return (OrmXmlContextModelFactory2_1) this.getContextModelFactory();
	}

	protected OrmXmlContextModelFactory getContextModelFactory() {
		return this.getMappingFileDefinition().getContextModelFactory();
	}
}
