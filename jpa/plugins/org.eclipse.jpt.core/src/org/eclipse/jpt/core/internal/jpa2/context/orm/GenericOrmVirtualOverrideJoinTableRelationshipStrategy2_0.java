/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableRelationship;
import org.eclipse.jpt.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTable;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTableRelationship;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0
	extends AbstractOrmXmlContextNode
	implements OrmVirtualJoinTableRelationshipStrategy
{
	protected OrmVirtualJoinTable joinTable;


	public GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0(OrmVirtualJoinTableRelationship parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateJoinTable();
	}


	// ********** join table **********

	public OrmVirtualJoinTable getJoinTable() {
		return this.joinTable;
	}

	protected void setJoinTable(OrmVirtualJoinTable joinTable) {
		OrmVirtualJoinTable old = this.joinTable;
		this.joinTable = joinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, joinTable);
	}

	protected void updateJoinTable() {
		JoinTable overriddenJoinTable = this.getOverriddenJoinTable();
		if (overriddenJoinTable == null) {
			if (this.joinTable != null) {
				this.setJoinTable(null);
			}
		} else {
			if ((this.joinTable != null) && (this.joinTable.getOverriddenTable() == overriddenJoinTable)) {
				this.joinTable.update();
			} else {
				this.setJoinTable(this.buildJoinTable(overriddenJoinTable));
			}
		}
	}

	protected JoinTable getOverriddenJoinTable() {
		JoinTableRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getJoinTable();
	}

	protected OrmVirtualJoinTable buildJoinTable(JoinTable overriddenJoinTable) {
		return this.getContextNodeFactory().buildOrmVirtualJoinTable(this, overriddenJoinTable);
	}


	// ********** misc **********

	@Override
	public OrmVirtualJoinTableRelationship getParent() {
		return (OrmVirtualJoinTableRelationship) super.getParent();
	}

	public OrmVirtualJoinTableRelationship getRelationship() {
		return this.getParent();
	}

	protected JoinTableRelationshipStrategy getOverriddenStrategy() {
		JoinTableRelationship relationship = this.getOverriddenJoinTableRelationship();
		return (relationship == null) ? null : relationship.getJoinTableStrategy();
	}

	protected JoinTableRelationship getOverriddenJoinTableRelationship() {
		Relationship relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof JoinTableRelationship) ? (JoinTableRelationship) relationship : null;
	}

	protected Relationship resolveOverriddenRelationship() {
		return this.getRelationship().resolveOverriddenRelationship();
	}

	public String getTableName() {
		return this.joinTable.getName();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationship());
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return null;
	}
}
