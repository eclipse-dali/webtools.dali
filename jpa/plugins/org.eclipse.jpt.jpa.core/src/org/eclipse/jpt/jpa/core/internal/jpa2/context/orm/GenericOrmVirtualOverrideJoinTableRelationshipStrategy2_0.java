/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0
	extends AbstractOrmXmlContextModel<VirtualOverrideRelationship2_0>
	implements VirtualJoinTableRelationshipStrategy, VirtualJoinTable.ParentAdapter
{
	protected VirtualJoinTable joinTable;


	public GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0(VirtualOverrideRelationship2_0 parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateJoinTable(monitor);
	}


	// ********** join table **********

	public VirtualJoinTable getJoinTable() {
		return this.joinTable;
	}

	protected void setJoinTable(VirtualJoinTable joinTable) {
		VirtualJoinTable old = this.joinTable;
		this.joinTable = joinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, joinTable);
	}

	protected void updateJoinTable(IProgressMonitor monitor) {
		JoinTable overriddenJoinTable = this.getOverriddenJoinTable();
		if (overriddenJoinTable == null) {
			if (this.joinTable != null) {
				this.setJoinTable(null);
			}
		} else {
			if ((this.joinTable != null) && (this.joinTable.getOverriddenTable() == overriddenJoinTable)) {
				this.joinTable.update(monitor);
			} else {
				this.setJoinTable(this.buildJoinTable(overriddenJoinTable));
			}
		}
	}

	protected JoinTable getOverriddenJoinTable() {
		JoinTableRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getJoinTable();
	}

	protected VirtualJoinTable buildJoinTable(JoinTable overriddenJoinTable) {
		return this.getContextModelFactory().buildOrmVirtualJoinTable(this, overriddenJoinTable);
	}


	// ********** misc **********

	public VirtualOverrideRelationship2_0 getRelationship() {
		return this.parent;
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

	public VirtualJoinTableRelationshipStrategy getTableParent() {
		return this;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.joinTable != null) {
			this.joinTable.validate(messages, reporter);
		}
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationship().getTypeMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	public JpaValidator buildTableValidator(Table table) {
		return this.getRelationship().buildJoinTableValidator((JoinTable) table);
	}

	public JpaValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, parentAdapter);
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, parentAdapter);
	}
}
