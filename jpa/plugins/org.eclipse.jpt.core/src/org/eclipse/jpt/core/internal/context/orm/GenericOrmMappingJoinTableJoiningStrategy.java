/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.orm.OrmMappingJoinTableRelationship;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.InverseJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinTableValidator;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmMappingJoinTableJoiningStrategy
	extends AbstractOrmJoinTableJoiningStrategy
{
	public GenericOrmMappingJoinTableJoiningStrategy(OrmMappingJoinTableRelationship parent) {
		super(parent);
	}


	public boolean isOverridable() {
		return this.getJpaPlatformVariation().isJoinTableOverridable();
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		return this.getRelationshipReference().getValidationTextRange();
	}

	protected PersistentAttribute getPersistentAttribute() {
		return getRelationshipMapping().getPersistentAttribute();
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new JoinColumnValidator(this.getPersistentAttribute(), column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new InverseJoinColumnValidator(this.getPersistentAttribute(), column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
		return new JoinTableValidator(getPersistentAttribute(), (JoinTable) table, textRangeResolver);
	}
}
