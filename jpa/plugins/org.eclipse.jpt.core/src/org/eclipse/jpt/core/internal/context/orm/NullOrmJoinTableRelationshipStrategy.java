/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableRelationship;
import org.eclipse.jpt.core.context.orm.OrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.jpa.db.Table;

public class NullOrmJoinTableRelationshipStrategy
	extends AbstractOrmXmlContextNode
	implements OrmJoinTableRelationshipStrategy
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
		return null;
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		throw new UnsupportedOperationException();
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		throw new UnsupportedOperationException();
	}

	public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
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
