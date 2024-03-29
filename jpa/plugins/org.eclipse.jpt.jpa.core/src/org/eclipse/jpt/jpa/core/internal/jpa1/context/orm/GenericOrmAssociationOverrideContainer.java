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
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;

/**
 * <code>orm.xml</code> association override container
 */
public class GenericOrmAssociationOverrideContainer
	extends AbstractOrmOverrideContainer<
			OrmAssociationOverrideContainer.ParentAdapter,
			AssociationOverride,
			OrmSpecifiedAssociationOverride,
			OrmVirtualAssociationOverride,
			XmlAssociationOverride
		>
	implements OrmAssociationOverrideContainer2_0
{
	public GenericOrmAssociationOverrideContainer(JpaContextModel parent) {
		super(parent);
	}

	public GenericOrmAssociationOverrideContainer(OrmAssociationOverrideContainer.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.parentAdapter.getOverridableTypeMapping());
	}

	public Relationship resolveOverriddenRelationship(String associationOverrideName) {
		return this.parentAdapter.resolveOverriddenRelationship(associationOverrideName);
	}

	protected OrmAssociationOverrideContainer2_0.ParentAdapter getParentAdapter2_0() {
		return (OrmAssociationOverrideContainer2_0.ParentAdapter) this.parentAdapter;
	}

	public JpaValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter columnParentAdapter) {
		return this.getParentAdapter2_0().buildJoinTableJoinColumnValidator(override, column, columnParentAdapter);
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter columnParentAdapter) {
		return this.getParentAdapter2_0().buildJoinTableInverseJoinColumnValidator(override, column, columnParentAdapter);
	}

	public JpaValidator buildJoinTableValidator(AssociationOverride override, Table table) {
		return this.getParentAdapter2_0().buildJoinTableValidator(override, table);
	}

	@Override
	protected Iterable<XmlAssociationOverride> getXmlOverrides_() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.parentAdapter.getXmlOverrides());
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
		specifiedOverride.initializeFrom(virtualOverride);
	}

	@Override
	protected OrmVirtualAssociationOverride buildVirtualOverride(String name) {
		return this.getContextModelFactory().buildOrmVirtualAssociationOverride(this, name);
	}
}
