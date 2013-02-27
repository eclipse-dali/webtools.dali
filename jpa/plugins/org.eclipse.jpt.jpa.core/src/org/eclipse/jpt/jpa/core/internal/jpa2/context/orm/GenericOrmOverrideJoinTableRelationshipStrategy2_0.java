/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOverrideRelationship2_0;

public class GenericOrmOverrideJoinTableRelationshipStrategy2_0
	extends AbstractOrmJoinTableRelationshipStrategy<OrmOverrideRelationship2_0>
{
	public GenericOrmOverrideJoinTableRelationshipStrategy2_0(OrmOverrideRelationship2_0 parent) {
		super(parent);
	}


	@Override
	public OrmOverrideRelationship2_0 getRelationship() {
		return (OrmOverrideRelationship2_0) super.getRelationship();
	}

	public boolean isOverridable() {
		return false;
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationship().getTypeMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	public JptValidator buildTableValidator(ReadOnlyTable table) {
		return this.getRelationship().buildJoinTableValidator((ReadOnlyJoinTable) table);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, owner);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, owner);
	}
}
