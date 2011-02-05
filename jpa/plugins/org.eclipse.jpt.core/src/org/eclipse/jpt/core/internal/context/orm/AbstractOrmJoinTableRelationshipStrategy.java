/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableRelationship;
import org.eclipse.jpt.core.context.orm.OrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlJoinTableContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmJoinTableRelationshipStrategy
	extends AbstractOrmXmlContextNode
	implements OrmJoinTableRelationshipStrategy, Table.Owner
{
	protected OrmJoinTable joinTable;


	protected AbstractOrmJoinTableRelationshipStrategy(OrmJoinTableRelationship parent) {
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

	public OrmJoinTable getJoinTable() {
		return this.joinTable;
	}

	protected void setJoinTable(OrmJoinTable joinTable) {
		OrmJoinTable old = this.joinTable;
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

	protected OrmJoinTable buildJoinTable() {
		return this.getContextNodeFactory().buildOrmJoinTable(this, this);
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


	// ********** misc **********

	@Override
	public OrmJoinTableRelationship getParent() {
		return (OrmJoinTableRelationship) super.getParent();
	}

	public OrmJoinTableRelationship getRelationship() {
		return this.getParent();
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public void initializeFrom(ReadOnlyJoinTableRelationshipStrategy oldStrategy) {
		ReadOnlyJoinTable oldJoinTable = oldStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.joinTable.initializeFrom(oldJoinTable);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinTableRelationshipStrategy virtualStrategy) {
		ReadOnlyJoinTable oldJoinTable = virtualStrategy.getJoinTable();
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
		return Tools.valuesAreDifferent(this.getTableName(), tableName);
	}

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.DOES_NOT_MATCH_JOIN_TABLE;
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
