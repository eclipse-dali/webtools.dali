/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;

/**
 * <code>orm.xml</code> attribute override container
 */
public class GenericOrmAttributeOverrideContainer
	extends AbstractOrmOverrideContainer<
			OrmAttributeOverrideContainer.Owner,
			ReadOnlyAttributeOverride,
			OrmSpecifiedAttributeOverride,
			OrmVirtualAttributeOverride,
			XmlAttributeOverride
		>
	implements OrmAttributeOverrideContainer
{
	public GenericOrmAttributeOverrideContainer(JpaContextModel parent, OrmAttributeOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public ReadOnlyColumn resolveOverriddenColumn(String attributeName) {
		return (attributeName == null) ? null : this.owner.resolveOverriddenColumn(attributeName);
	}

	@Override
	protected Iterable<XmlAttributeOverride> getXmlOverrides_() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.owner.getXmlOverrides());
	}

	@Override
	protected XmlAttributeOverride buildXmlOverride() {
		return OrmFactory.eINSTANCE.createXmlAttributeOverride();
	}

	@Override
	protected OrmSpecifiedAttributeOverride buildSpecifiedOverride(XmlAttributeOverride xmlOverride) {
		return this.getContextModelFactory().buildOrmAttributeOverride(this, xmlOverride);
	}

	public void initializeFrom(OrmAttributeOverrideContainer oldContainer) {
		for (OrmSpecifiedAttributeOverride oldOverride : oldContainer.getSpecifiedOverrides()) {
			this.addSpecifiedOverride().initializeFrom(oldOverride);
		}
	}

	@Override
	protected void initializeSpecifiedOverride(OrmSpecifiedAttributeOverride specifiedOverride, OrmVirtualAttributeOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected OrmVirtualAttributeOverride buildVirtualOverride(String name) {
		return this.getContextModelFactory().buildOrmVirtualAttributeOverride(this, name);
	}
}
