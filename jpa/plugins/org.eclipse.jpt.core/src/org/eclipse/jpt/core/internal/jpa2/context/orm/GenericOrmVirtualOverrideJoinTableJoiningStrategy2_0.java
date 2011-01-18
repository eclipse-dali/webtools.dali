/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTable;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmVirtualOverrideJoinTableJoiningStrategy2_0
	extends AbstractOrmXmlContextNode
	implements OrmVirtualJoinTableJoiningStrategy
{
	protected OrmVirtualJoinTable joinTable;


	public GenericOrmVirtualOverrideJoinTableJoiningStrategy2_0(OrmVirtualJoinTableEnabledRelationshipReference parent) {
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
		JoinTableJoiningStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getJoinTable();
	}

	protected OrmVirtualJoinTable buildJoinTable(JoinTable overriddenJoinTable) {
		return this.getContextNodeFactory().buildOrmVirtualJoinTable(this, overriddenJoinTable);
	}


	// ********** misc **********

	@Override
	public OrmVirtualJoinTableEnabledRelationshipReference getParent() {
		return (OrmVirtualJoinTableEnabledRelationshipReference) super.getParent();
	}

	public OrmVirtualJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}

	protected JoinTableJoiningStrategy getOverriddenStrategy() {
		JoinTableEnabledRelationshipReference relationship = this.getOverriddenJoinTableRelationship();
		return (relationship == null) ? null : relationship.getJoinTableJoiningStrategy();
	}

	protected JoinTableEnabledRelationshipReference getOverriddenJoinTableRelationship() {
		RelationshipReference relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof JoinTableEnabledRelationshipReference) ? (JoinTableEnabledRelationshipReference) relationship : null;
	}

	protected RelationshipReference resolveOverriddenRelationship() {
		return this.getRelationshipReference().resolveOverriddenRelationship();
	}

	public String getTableName() {
		return this.joinTable.getName();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationshipReference());
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return null;
	}
}
