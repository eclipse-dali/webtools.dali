/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.jpa2.context.orm.OrmAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmOverrideJoinTableJoiningStrategy2_0
	extends AbstractOrmJoinTableJoiningStrategy
{
	public GenericOrmOverrideJoinTableJoiningStrategy2_0(OrmAssociationOverrideRelationshipReference2_0 parent) {
		super(parent);
	}


	@Override
	public OrmAssociationOverrideRelationshipReference2_0 getRelationshipReference() {
		return (OrmAssociationOverrideRelationshipReference2_0) super.getRelationshipReference();
	}

	public boolean isOverridable() {
		return false;
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipReference().getTypeMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getRelationshipReference().getAssociationOverride().getXmlOverride().getValidationTextRange();
		return (textRange != null) ? textRange : this.getRelationshipReference().getValidationTextRange();
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationshipReference().buildJoinTableJoinColumnValidator(column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationshipReference().buildJoinTableInverseJoinColumnValidator(column, owner, textRangeResolver);
	}

	public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
		return this.getRelationshipReference().buildTableValidator(table, textRangeResolver);
	}
}
