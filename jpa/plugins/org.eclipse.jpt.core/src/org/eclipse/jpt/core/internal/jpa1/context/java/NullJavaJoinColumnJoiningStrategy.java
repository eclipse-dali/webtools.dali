/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * Used by
 * {@link org.eclipse.jpt.core.internal.context.java.GenericJavaOneToManyRelationship#buildJoinColumnStrategy()}
 * in a JPA 1.0 project.
 */
public class NullJavaJoinColumnJoiningStrategy
	extends AbstractJavaJpaContextNode
	implements JavaJoinColumnJoiningStrategy
{
	public NullJavaJoinColumnJoiningStrategy(JavaJoinColumnEnabledRelationshipReference parent) {
		super(parent);
	}


	// ********** join columns **********

	public ListIterator<JavaJoinColumn> joinColumns() {
		return EmptyListIterator.<JavaJoinColumn>instance();
	}

	public int joinColumnsSize() {
		return 0;
	}


	// ********** specified join columns **********

	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return EmptyListIterator.<JavaJoinColumn>instance();
	}

	public int specifiedJoinColumnsSize() {
		return 0;
	}

	public boolean hasSpecifiedJoinColumns() {
		return false;
	}

	public JavaJoinColumn getSpecifiedJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public JavaJoinColumn addSpecifiedJoinColumn() {
		throw new UnsupportedOperationException();
	}

	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		throw new UnsupportedOperationException();
	}

	public void removeSpecifiedJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}


	// ********** default join column **********

	public JavaJoinColumn getDefaultJoinColumn() {
		return null;
	}


	// ********** misc **********

	@Override
	public JavaJoinColumnEnabledRelationshipReference getParent() {
		return (JavaJoinColumnEnabledRelationshipReference) super.getParent();
	}

	public JavaJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getMapping();
	}

	public String getTableName() {
		return null;
	}

	public TypeMapping getRelationshipSource() {
		return this.getRelationshipMapping().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return null;
	}

	public boolean isTargetForeignKey() {
		return false;
	}

	public void initializeFrom(ReadOnlyJoinColumnJoiningStrategy oldStrategy) {
		// NOP
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnJoiningStrategy oldStrategy) {
		// NOP
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

	public boolean tableNameIsInvalid(String tableName) {
		return true;
	}

	public Table resolveDbTable(String tableName) {
		return null;
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}
}
