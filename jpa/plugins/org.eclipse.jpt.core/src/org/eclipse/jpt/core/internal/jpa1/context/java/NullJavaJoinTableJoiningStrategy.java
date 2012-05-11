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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;

public class NullJavaJoinTableJoiningStrategy 
	extends AbstractJavaJpaContextNode
	implements JavaJoinTableJoiningStrategy
{	

	public NullJavaJoinTableJoiningStrategy(JoinTableEnabledRelationshipReference parent) {
		super(parent);
	}

	public void initializeFrom(JoinTableJoiningStrategy oldStrategy) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JoinTableEnabledRelationshipReference getParent() {
		return (JoinTableEnabledRelationshipReference) super.getParent();
	}

	public JoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}

	public void addStrategy() {
		//do nothing
	}

	public void removeStrategy() {
		//do nothing, no join table to remove
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

	public JoinTableAnnotation getAnnotation() {
		return null;
	}

	public JavaJoinTable getJoinTable() {
		return null;
	}

	public String getJoinTableDefaultName() {
		return null;
	}

	public boolean shouldValidateAgainstDatabase() {
		return false;
	}
}
