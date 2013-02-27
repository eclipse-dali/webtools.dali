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
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;

/**
 * <code>orm.xml</code> association override container
 */
public class GenericOrmAssociationOverrideContainer
	extends AbstractOrmOverrideContainer<
			OrmAssociationOverrideContainer.Owner,
			AssociationOverride,
			OrmSpecifiedAssociationOverride,
			OrmVirtualAssociationOverride,
			XmlAssociationOverride
		>
	implements OrmAssociationOverrideContainer2_0
{
	public GenericOrmAssociationOverrideContainer(JpaContextModel parent, OrmAssociationOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.owner.getOverridableTypeMapping());
	}

	public Relationship resolveOverriddenRelationship(String associationOverrideName) {
		return this.owner.resolveOverriddenRelationship(associationOverrideName);
	}

	protected OrmAssociationOverrideContainer2_0.Owner getOwner2_0() {
		return (OrmAssociationOverrideContainer2_0.Owner) this.owner;
	}

	public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner columnOwner) {
		return this.getOwner2_0().buildJoinTableJoinColumnValidator(override, column, columnOwner);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner columnOwner) {
		return this.getOwner2_0().buildJoinTableInverseJoinColumnValidator(override, column, columnOwner);
	}

	public JptValidator buildJoinTableValidator(AssociationOverride override, Table table) {
		return this.getOwner2_0().buildJoinTableValidator(override, table);
	}

	@Override
	protected Iterable<XmlAssociationOverride> getXmlOverrides_() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.owner.getXmlOverrides());
	}

	@Override
	protected XmlAssociationOverride buildXmlOverride() {
		return OrmFactory.eINSTANCE.createXmlAssociationOverride();
	}

	@Override
	protected OrmSpecifiedAssociationOverride buildSpecifiedOverride(XmlAssociationOverride xmlOverride) {
		return this.getContextModelFactory().buildOrmAssociationOverride(this, xmlOverride);
	}

	@Override
	protected void initializeSpecifiedOverride(OrmSpecifiedAssociationOverride specifiedOverride, OrmVirtualAssociationOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected OrmVirtualAssociationOverride buildVirtualOverride(String name) {
		return this.getContextModelFactory().buildOrmVirtualAssociationOverride(this, name);
	}
}
