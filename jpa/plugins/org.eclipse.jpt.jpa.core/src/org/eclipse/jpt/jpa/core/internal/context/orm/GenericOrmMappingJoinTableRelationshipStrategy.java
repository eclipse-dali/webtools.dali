/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappingJoinTableRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.InverseJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OverrideRelationship2_0;

public class GenericOrmMappingJoinTableRelationshipStrategy
	extends AbstractOrmJoinTableRelationshipStrategy<OrmMappingJoinTableRelationship>
	implements SpecifiedMappingRelationshipStrategy2_0
{
	public GenericOrmMappingJoinTableRelationshipStrategy(OrmMappingJoinTableRelationship parent) {
		super(parent);
	}


	public boolean isOverridable() {
		return this.getJpaPlatformVariation().isJoinTableOverridable();
	}

	public RelationshipStrategy selectOverrideStrategy(OverrideRelationship2_0 overrideRelationship) {
		return overrideRelationship.getJoinTableStrategy();
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	protected SpecifiedPersistentAttribute getPersistentAttribute() {
		return getRelationshipMapping().getPersistentAttribute();
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return new JoinColumnValidator(this.getPersistentAttribute(), column, parentAdapter, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return new InverseJoinColumnValidator(this.getPersistentAttribute(), column, parentAdapter, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildTableValidator(Table table) {
		return new JoinTableValidator(this.getPersistentAttribute(), (JoinTable) table);
	}
}
