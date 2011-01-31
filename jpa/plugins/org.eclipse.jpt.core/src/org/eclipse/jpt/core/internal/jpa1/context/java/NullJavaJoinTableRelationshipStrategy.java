/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumn.Owner;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableRelationship;
import org.eclipse.jpt.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.db.Table;

public class NullJavaJoinTableRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaJoinTableRelationshipStrategy
{
	public NullJavaJoinTableRelationshipStrategy(JavaJoinTableRelationship parent) {
		super(parent);
	}


	// ********** join table **********

	public JavaJoinTable getJoinTable() {
		return null;
	}

	public JoinTableAnnotation getJoinTableAnnotation() {
		return null;
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}


	// ********** misc **********

	@Override
	public JavaJoinTableRelationship getParent() {
		return (JavaJoinTableRelationship) super.getParent();
	}

	public JavaJoinTableRelationship getRelationship() {
		return this.getParent();
	}

	public void initializeFrom(ReadOnlyJoinTableRelationshipStrategy oldStrategy) {
		// NOP
	}

	public void initializeFromVirtual(ReadOnlyJoinTableRelationshipStrategy virtualStrategy) {
		// NOP
	}

	public String getTableName() {
		return null;
	}

	public Table resolveDbTable(String tableName) {
		return null;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return true;
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}

	public String getJoinTableDefaultName() {
		return null;
	}

	public void addStrategy() {
		// NOP
	}

	public void removeStrategy() {
		// NOP
	}

	public boolean isOverridable() {
		return false;
	}

	public boolean validatesAgainstDatabase() {
		return false;
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		throw new UnsupportedOperationException();
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		throw new UnsupportedOperationException();
	}
}
