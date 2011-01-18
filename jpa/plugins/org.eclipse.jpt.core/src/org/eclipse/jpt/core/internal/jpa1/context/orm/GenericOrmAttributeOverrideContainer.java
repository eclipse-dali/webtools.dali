/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * <code>orm.xml</code> attribute override container
 */
public class GenericOrmAttributeOverrideContainer
	extends AbstractOrmOverrideContainer<
			OrmAttributeOverrideContainer.Owner,
			OrmReadOnlyAttributeOverride,
			OrmAttributeOverride,
			OrmVirtualAttributeOverride,
			XmlAttributeOverride
		>
	implements OrmAttributeOverrideContainer
{
	public GenericOrmAttributeOverrideContainer(XmlContextNode parent, OrmAttributeOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public Column resolveOverriddenColumn(String attributeName) {
		return (attributeName == null) ? null : this.owner.resolveOverriddenColumn(attributeName);
	}

	@Override
	protected Iterable<XmlAttributeOverride> getXmlOverrides_() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlAttributeOverride>(this.owner.getXmlOverrides());
	}

	@Override
	protected XmlAttributeOverride buildXmlOverride() {
		return OrmFactory.eINSTANCE.createXmlAttributeOverride();
	}

	@Override
	protected OrmAttributeOverride buildSpecifiedOverride(XmlAttributeOverride xmlOverride) {
		return this.getContextNodeFactory().buildOrmAttributeOverride(this, xmlOverride);
	}

	public void initializeFrom(OrmAttributeOverrideContainer oldContainer) {
		for (OrmAttributeOverride oldOverride : CollectionTools.iterable(oldContainer.specifiedOverrides())) {
			this.addSpecifiedOverride().initializeFrom(oldOverride);
		}
	}

	@Override
	protected void initializeSpecifiedOverride(OrmAttributeOverride specifiedOverride, OrmVirtualAttributeOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected OrmVirtualAttributeOverride buildVirtualOverride(String name) {
		return this.getContextNodeFactory().buildOrmVirtualAttributeOverride(this, name);
	}
}
