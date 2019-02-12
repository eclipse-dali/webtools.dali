/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;

/**
 * <code>orm.xml</code> attribute override container
 */
public class GenericOrmAttributeOverrideContainer
	extends AbstractOrmOverrideContainer<
			OrmAttributeOverrideContainer.ParentAdapter,
			AttributeOverride,
			OrmSpecifiedAttributeOverride,
			OrmVirtualAttributeOverride,
			XmlAttributeOverride
		>
	implements OrmAttributeOverrideContainer
{
	public GenericOrmAttributeOverrideContainer(JpaContextModel parent) {
		super(parent);
	}

	public GenericOrmAttributeOverrideContainer(OrmAttributeOverrideContainer.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}


	public Column resolveOverriddenColumn(String attributeName) {
		return (attributeName == null) ? null : this.parentAdapter.resolveOverriddenColumn(attributeName);
	}

	@Override
	protected Iterable<XmlAttributeOverride> getXmlOverrides_() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.parentAdapter.getXmlOverrides());
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
		specifiedOverride.initializeFrom(virtualOverride);
	}

	@Override
	protected OrmVirtualAttributeOverride buildVirtualOverride(String name) {
		return this.getContextModelFactory().buildOrmVirtualAttributeOverride(this, name);
	}
}
