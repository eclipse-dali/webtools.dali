/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTableContainer;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmJoinTableRelationshipStrategy<P extends OrmJoinTableRelationship>
	extends AbstractOrmXmlContextModel<P>
	implements OrmSpecifiedJoinTableRelationshipStrategy, Table.Owner
{
	protected OrmSpecifiedJoinTable joinTable;


	protected AbstractOrmJoinTableRelationshipStrategy(P parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		if (this.joinTable != null) {
			this.joinTable.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.updateJoinTable();
	}


	// ********** join table **********

	public OrmSpecifiedJoinTable getJoinTable() {
		return this.joinTable;
	}

	protected void setJoinTable(OrmSpecifiedJoinTable joinTable) {
		OrmSpecifiedJoinTable old = this.joinTable;
		this.joinTable = joinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, joinTable);
	}

	protected void updateJoinTable() {
		if (this.buildsJoinTable()) {
			if (this.joinTable == null) {
				this.setJoinTable(this.buildJoinTable());
			} else {
				this.joinTable.update();
			}
		} else {
			if (this.joinTable != null) {
				this.setJoinTable(null);
			}
		}
	}

	/**
	 * The strategy can have a join table if either the XML table is present
	 * or the [mapping] relationship supports a default join table.
	 */
	protected boolean buildsJoinTable() {
		return (this.getXmlJoinTable() != null) ||
				this.getRelationship().mayHaveDefaultJoinTable();
	}

	protected OrmSpecifiedJoinTable buildJoinTable() {
		return this.getContextModelFactory().buildOrmJoinTable(this, this);
	}


	// ********** XML join table **********

	public XmlJoinTable getXmlJoinTable() {
		return this.getXmlJoinTableContainer().getJoinTable();
	}

	public XmlJoinTable buildXmlJoinTable() {
		XmlJoinTable xmlJoinTable = OrmFactory.eINSTANCE.createXmlJoinTable();
		this.getXmlJoinTableContainer().setJoinTable(xmlJoinTable);
		return xmlJoinTable;
	}

	public void removeXmlJoinTable() {
		this.getXmlJoinTableContainer().setJoinTable(null);
	}

	protected XmlJoinTableContainer getXmlJoinTableContainer() {
		return this.getRelationship().getXmlContainer();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.joinTable != null) {
			this.joinTable.validate(messages, reporter);
		}
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.joinTable != null) {
			result = this.joinTable.getCompletionProposals(pos);
		}
		return result;
	}


	// ********** misc **********

	public OrmJoinTableRelationship getRelationship() {
		return this.parent;
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public void initializeFrom(JoinTableRelationshipStrategy oldStrategy) {
		JoinTable oldJoinTable = oldStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.joinTable.initializeFrom(oldJoinTable);
		}
	}

	public void initializeFromVirtual(JoinTableRelationshipStrategy virtualStrategy) {
		JoinTable oldJoinTable = virtualStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.joinTable.initializeFromVirtual(oldJoinTable);
		}
	}

	public String getTableName() {
		return (this.joinTable == null) ? null : this.joinTable.getName();
	}

	public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
		return (this.joinTable == null) ? null : this.joinTable.getDbTable();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return ObjectTools.notEquals(this.getTableName(), tableName);
	}

	public String getColumnTableNotValidDescription() {
		return JptJpaCoreValidationArgumentMessages.DOES_NOT_MATCH_JOIN_TABLE;
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationship());
	}

	public void addStrategy() {
		if (this.joinTable == null) {
			this.setJoinTable(this.buildJoinTable());
			this.buildXmlJoinTable();
		}
	}

	public void removeStrategy() {
		if (this.joinTable != null) {
			this.setJoinTable(null);
			this.removeXmlJoinTable();
		}
	}
}
