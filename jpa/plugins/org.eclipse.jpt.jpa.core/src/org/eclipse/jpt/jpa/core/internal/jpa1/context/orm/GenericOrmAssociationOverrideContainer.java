/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;

/**
 * <code>orm.xml</code> association override container
 */
public class GenericOrmAssociationOverrideContainer
	extends AbstractOrmOverrideContainer<
			OrmAssociationOverrideContainer.Owner,
			OrmReadOnlyAssociationOverride,
			OrmAssociationOverride,
			OrmVirtualAssociationOverride,
			XmlAssociationOverride
		>
	implements OrmAssociationOverrideContainer2_0
{
	public GenericOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.owner.getOverridableTypeMapping());
	}

	public ReadOnlyRelationship resolveOverriddenRelationship(String associationOverrideName) {
		return this.owner.resolveOverriddenRelationship(associationOverrideName);
	}

	protected OrmAssociationOverrideContainer2_0.Owner getOwner2_0() {
		return (OrmAssociationOverrideContainer2_0.Owner) this.owner;
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner columnOwner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getOwner2_0().buildJoinTableJoinColumnValidator(override, column, columnOwner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner columnOwner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getOwner2_0().buildJoinTableInverseJoinColumnValidator(override, column, columnOwner, textRangeResolver);
	}

	public JptValidator buildJoinTableValidator(ReadOnlyAssociationOverride override, ReadOnlyTable table, TableTextRangeResolver textRangeResolver) {
		return this.getOwner2_0().buildJoinTableValidator(override, table, textRangeResolver);
	}

	@Override
	protected Iterable<XmlAssociationOverride> getXmlOverrides_() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlAssociationOverride>(this.owner.getXmlOverrides());
	}

	@Override
	protected XmlAssociationOverride buildXmlOverride() {
		return OrmFactory.eINSTANCE.createXmlAssociationOverride();
	}

	@Override
	protected OrmAssociationOverride buildSpecifiedOverride(XmlAssociationOverride xmlOverride) {
		return this.getContextNodeFactory().buildOrmAssociationOverride(this, xmlOverride);
	}

	@Override
	protected void initializeSpecifiedOverride(OrmAssociationOverride specifiedOverride, OrmVirtualAssociationOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected OrmVirtualAssociationOverride buildVirtualOverride(String name) {
		return this.getContextNodeFactory().buildOrmVirtualAssociationOverride(this, name);
	}
}
