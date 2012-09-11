/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.jpa.db.Table;

public class NullOrmJoinTableRelationshipStrategy
	extends AbstractOrmXmlContextNode
	implements MappingRelationshipStrategy2_0, OrmJoinTableRelationshipStrategy
{
	public NullOrmJoinTableRelationshipStrategy(OrmJoinTableRelationship parent) {
		super(parent);
	}


	// ********** join table **********

	public OrmJoinTable getJoinTable() {
		return null;
	}


	// ********** XML join table **********

	public XmlJoinTable getXmlJoinTable() {
		return null;
	}

	public XmlJoinTable buildXmlJoinTable() {
		throw new UnsupportedOperationException();
	}

	public void removeXmlJoinTable() {
		throw new UnsupportedOperationException();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
		throw new UnsupportedOperationException();
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
		throw new UnsupportedOperationException();
	}


	// ********** misc **********

	@Override
	public OrmJoinTableRelationship getParent() {
		return (OrmJoinTableRelationship) super.getParent();
	}

	public OrmJoinTableRelationship getRelationship() {
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
}
