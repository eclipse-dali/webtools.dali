/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOverrideRelationship2_0;

public class GenericOrmOverrideJoinTableRelationshipStrategy2_0
	extends AbstractOrmJoinTableRelationshipStrategy
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

	public JptValidator buildTableValidator(ReadOnlyTable table, TableTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableValidator((ReadOnlyJoinTable) table, textRangeResolver);
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, owner, textRangeResolver);
	}
}