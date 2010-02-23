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
import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;

public class NullJavaJoinColumnJoiningStrategy 
	extends AbstractJavaJpaContextNode
	implements JavaJoinColumnJoiningStrategy
{	
	
	protected NullJavaJoinColumnJoiningStrategy(JoinColumnEnabledRelationshipReference parent) {
		super(parent);
	}
	
	public void initializeFrom(JoinColumnJoiningStrategy oldStrategy) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JoinColumnEnabledRelationshipReference getParent() {
		return (JoinColumnEnabledRelationshipReference) super.getParent();
	}
	
	public JoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public void addStrategy() {
		throw new UnsupportedOperationException();
	}
	
	public void removeStrategy() {
		throw new UnsupportedOperationException();
	}
	
	
	// **************** join columns *******************************************
	
	public ListIterator<JavaJoinColumn> joinColumns() {
		throw new UnsupportedOperationException();

	}
	
	public int joinColumnsSize() {
		throw new UnsupportedOperationException();
	}
	
	
	// **************** default join column ************************************
	
	public JavaJoinColumn getDefaultJoinColumn() {
		throw new UnsupportedOperationException();
	}	
	
	// **************** specified join columns *********************************
	
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		throw new UnsupportedOperationException();
	}
	
	public int specifiedJoinColumnsSize() {
		throw new UnsupportedOperationException();
	}
	
	public boolean hasSpecifiedJoinColumns() {
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


	// **************** resource => context ************************************

	public void initialize() {
		//no-op
	}
	
	
	public void update() {
		//no-op
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public String getColumnTableNotValidDescription() {
		throw new UnsupportedOperationException();
	}

	public Table getDbTable(String tableName) {
		throw new UnsupportedOperationException();
	}

	public String getTableName() {
		return null;
	}

	public boolean isOverridableAssociation() {
		return false;
	}

	public boolean tableNameIsInvalid(String tableName) {
		throw new UnsupportedOperationException();
	}

	public TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}
}
