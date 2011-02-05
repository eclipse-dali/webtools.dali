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

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnRelationship;
import org.eclipse.jpt.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.db.Table;

/**
 * Used by
 * {@link org.eclipse.jpt.core.internal.context.java.GenericJavaOneToManyRelationship#buildJoinColumnStrategy()}
 * in a JPA 1.0 project.
 */
public class NullJavaJoinColumnRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaJoinColumnRelationshipStrategy
{
	public NullJavaJoinColumnRelationshipStrategy(JavaJoinColumnRelationship parent) {
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
	public JavaJoinColumnRelationship getParent() {
		return (JavaJoinColumnRelationship) super.getParent();
	}

	public JavaJoinColumnRelationship getRelationship() {
		return this.getParent();
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
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

	public void initializeFrom(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
		// NOP
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
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
