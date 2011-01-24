/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

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
	implements OrmAssociationOverrideContainer
{
	public GenericOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.owner.getOverridableTypeMapping());
	}

	public Relationship resolveOverriddenRelationship(String associationOverrideName) {
		return this.owner.resolveOverriddenRelationship(associationOverrideName);
	}

	public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner columnOwner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildJoinTableJoinColumnValidator(override, column, columnOwner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner columnOwner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildJoinTableInverseJoinColumnValidator(override, column, columnOwner, textRangeResolver);
	}

	public JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver) {
		return this.owner.buildTableValidator(override, table, textRangeResolver);
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
