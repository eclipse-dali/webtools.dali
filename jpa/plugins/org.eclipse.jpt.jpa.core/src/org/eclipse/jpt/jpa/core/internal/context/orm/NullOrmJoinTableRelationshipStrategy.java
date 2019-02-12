/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.OverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.jpa.db.Table;

public class NullOrmJoinTableRelationshipStrategy
	extends AbstractOrmXmlContextModel<OrmJoinTableRelationship>
	implements SpecifiedMappingRelationshipStrategy2_0, OrmSpecifiedJoinTableRelationshipStrategy
{
	public NullOrmJoinTableRelationshipStrategy(OrmJoinTableRelationship parent) {
		super(parent);
	}


	// ********** join table **********

	public OrmSpecifiedJoinTable getJoinTable() {
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

	public JpaValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		throw new UnsupportedOperationException();
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		throw new UnsupportedOperationException();
	}


	// ********** misc **********

	public OrmJoinTableRelationship getRelationship() {
		return this.parent;
	}

	public void initializeFrom(OrmSpecifiedJoinTableRelationshipStrategy oldStrategy) {
		// NOP
	}

	public void initializeFrom(VirtualJoinTableRelationshipStrategy virtualStrategy) {
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

	public RelationshipStrategy selectOverrideStrategy(OverrideRelationship2_0 overrideRelationship) {
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
