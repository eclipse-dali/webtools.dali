/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0
	extends AbstractOrmXmlContextModel<VirtualOverrideRelationship2_0>
	implements VirtualJoinTableRelationshipStrategy, ReadOnlyTable.Owner
{
	protected VirtualJoinTable joinTable;


	public GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0(VirtualOverrideRelationship2_0 parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateJoinTable();
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

	protected void updateJoinTable() {
		ReadOnlyJoinTable overriddenJoinTable = this.getOverriddenJoinTable();
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

	protected ReadOnlyJoinTable getOverriddenJoinTable() {
		JoinTableRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getJoinTable();
	}

	protected VirtualJoinTable buildJoinTable(ReadOnlyJoinTable overriddenJoinTable) {
		return this.getContextModelFactory().buildOrmVirtualJoinTable(this, this, overriddenJoinTable);
	}


	// ********** misc **********

	public VirtualOverrideRelationship2_0 getRelationship() {
		return this.parent;
	}

	protected JoinTableRelationshipStrategy getOverriddenStrategy() {
		ReadOnlyJoinTableRelationship relationship = this.getOverriddenJoinTableRelationship();
		return (relationship == null) ? null : relationship.getJoinTableStrategy();
	}

	protected ReadOnlyJoinTableRelationship getOverriddenJoinTableRelationship() {
		ReadOnlyRelationship relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof ReadOnlyJoinTableRelationship) ? (ReadOnlyJoinTableRelationship) relationship : null;
	}

	protected ReadOnlyRelationship resolveOverriddenRelationship() {
		return this.getRelationship().resolveOverriddenRelationship();
	}

	public String getTableName() {
		return this.joinTable.getName();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationship());
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

	public JptValidator buildTableValidator(ReadOnlyTable table) {
		return this.getRelationship().buildJoinTableValidator((ReadOnlyJoinTable) table);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, owner);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, owner);
	}
}
