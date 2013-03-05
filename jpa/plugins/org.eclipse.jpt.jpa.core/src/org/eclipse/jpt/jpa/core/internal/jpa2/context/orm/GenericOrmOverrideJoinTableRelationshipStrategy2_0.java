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
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSpecifiedOverrideRelationship2_0;

public class GenericOrmOverrideJoinTableRelationshipStrategy2_0
	extends AbstractOrmJoinTableRelationshipStrategy<OrmSpecifiedOverrideRelationship2_0>
{
	public GenericOrmOverrideJoinTableRelationshipStrategy2_0(OrmSpecifiedOverrideRelationship2_0 parent) {
		super(parent);
	}


	@Override
	public OrmSpecifiedOverrideRelationship2_0 getRelationship() {
		return (OrmSpecifiedOverrideRelationship2_0) super.getRelationship();
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

	public JptValidator buildTableValidator(Table table) {
		return this.getRelationship().buildJoinTableValidator((JoinTable) table);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, parentAdapter);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, parentAdapter);
	}
}
