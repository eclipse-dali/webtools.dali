/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Used by
 * {@link org.eclipse.jpt.jpa.core.internal.context.orm.GenericOrmOneToManyRelationship#buildJoinColumnStrategy()}
 * in a JPA 1.0 project.
 */
public class NullOrmJoinColumnRelationshipStrategy
	extends AbstractOrmXmlContextModel<OrmJoinColumnRelationship>
	implements MappingRelationshipStrategy2_0, OrmJoinColumnRelationshipStrategy
{
	public NullOrmJoinColumnRelationshipStrategy(OrmJoinColumnRelationship parent) {
		super(parent);
	}


	// ********** join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getJoinColumns() {
		return EmptyListIterable.<OrmSpecifiedJoinColumn>instance();
	}

	public int getJoinColumnsSize() {
		return 0;
	}


	// ********** specified join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getSpecifiedJoinColumns() {
		return EmptyListIterable.<OrmSpecifiedJoinColumn>instance();
	}

	public int getSpecifiedJoinColumnsSize() {
		return 0;
	}

	public boolean hasSpecifiedJoinColumns() {
		return false;
	}

	public OrmSpecifiedJoinColumn getSpecifiedJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public OrmSpecifiedJoinColumn addSpecifiedJoinColumn() {
		throw new UnsupportedOperationException();
	}

	public OrmSpecifiedJoinColumn addSpecifiedJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void removeSpecifiedJoinColumn(SpecifiedJoinColumn joinColumn) {
		throw new UnsupportedOperationException();
	}

	public void removeSpecifiedJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void clearSpecifiedJoinColumns() {
		throw new UnsupportedOperationException();
	}

	public void convertDefaultJoinColumnsToSpecified() {
		throw new UnsupportedOperationException();
	}


	// ********** default join column **********

	public OrmSpecifiedJoinColumn getDefaultJoinColumn() {
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

	public OrmJoinColumnRelationship getRelationship() {
		return this.parent;
	}

	public void initializeFrom(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
		// NOP
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
		// NOP
	}

	public RelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship) {
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
