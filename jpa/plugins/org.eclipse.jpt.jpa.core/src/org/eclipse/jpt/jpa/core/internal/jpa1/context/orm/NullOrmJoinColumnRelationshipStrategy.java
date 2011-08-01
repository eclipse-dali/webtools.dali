/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMappingJoinColumnRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Used by
 * {@link org.eclipse.jpt.jpa.core.internal.context.orm.GenericOrmOneToManyRelationship#buildJoinColumnStrategy()}
 * in a JPA 1.0 project.
 */
public class NullOrmJoinColumnRelationshipStrategy
	extends AbstractOrmXmlContextNode
	implements OrmMappingJoinColumnRelationshipStrategy2_0
{
	public NullOrmJoinColumnRelationshipStrategy(OrmJoinColumnRelationship parent) {
		super(parent);
	}


	// ********** join columns **********

	public ListIterable<OrmJoinColumn> getJoinColumns() {
		return EmptyListIterable.<OrmJoinColumn>instance();
	}

	public int getJoinColumnsSize() {
		return 0;
	}


	// ********** specified join columns **********

	public ListIterable<OrmJoinColumn> getSpecifiedJoinColumns() {
		return EmptyListIterable.<OrmJoinColumn>instance();
	}

	public int getSpecifiedJoinColumnsSize() {
		return 0;
	}

	public boolean hasSpecifiedJoinColumns() {
		return false;
	}

	public OrmJoinColumn getSpecifiedJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public OrmJoinColumn addSpecifiedJoinColumn() {
		throw new UnsupportedOperationException();
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
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

	public OrmJoinColumn getDefaultJoinColumn() {
		return null;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}


	// ********** misc **********

	@Override
	public OrmJoinColumnRelationship getParent() {
		return (OrmJoinColumnRelationship) super.getParent();
	}

	public OrmJoinColumnRelationship getRelationship() {
		return this.getParent();
	}

	public void initializeFrom(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
		// NOP
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
		// NOP
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

	public boolean isTargetForeignKey() {
		return false;
	}

	public TypeMapping getRelationshipTarget() {
		return null;
	}

	public String getTableName() {
		return null;
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

	public TypeMapping getRelationshipSource() {
		return this.getRelationshipMapping().getTypeMapping();
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}
}
