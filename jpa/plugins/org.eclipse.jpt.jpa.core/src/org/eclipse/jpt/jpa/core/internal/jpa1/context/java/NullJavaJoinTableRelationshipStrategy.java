/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.jpa.db.Table;

public class NullJavaJoinTableRelationshipStrategy
	extends AbstractJavaContextModel<JavaJoinTableRelationship>
	implements MappingRelationshipStrategy2_0, JavaJoinTableRelationshipStrategy
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

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}


	// ********** misc **********

	public JavaJoinTableRelationship getRelationship() {
		return this.parent;
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

	public ReadOnlyRelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship) {
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

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		throw new UnsupportedOperationException();
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		throw new UnsupportedOperationException();
	}
}
