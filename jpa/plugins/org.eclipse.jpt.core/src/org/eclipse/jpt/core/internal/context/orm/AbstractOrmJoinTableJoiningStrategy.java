/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmJoinTableJoiningStrategy 
	extends AbstractOrmXmlContextNode
	implements OrmJoinTableJoiningStrategy
{
	protected OrmJoinTable joinTable;
	
	
	protected AbstractOrmJoinTableJoiningStrategy(
			JoinTableEnabledRelationshipReference parent) {
		super(parent);
	}	
	
	public void initializeFrom(JoinTableJoiningStrategy oldStrategy) {
		JoinTable oldJoinTable = (oldStrategy.getJoinTable());
		if (oldJoinTable != null) {
			this.addStrategy();
			this.getJoinTable().setSpecifiedCatalog(oldJoinTable.getSpecifiedCatalog());
			this.getJoinTable().setSpecifiedSchema(oldJoinTable.getSpecifiedSchema());
			this.getJoinTable().setSpecifiedName(oldJoinTable.getSpecifiedName());
		}
	}	
	
	@Override
	public JoinTableEnabledRelationshipReference getParent() {
		return (JoinTableEnabledRelationshipReference) super.getParent();
	}
	
	public JoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public String getTableName() {
		return getJoinTable().getName();
	}

	public Table getDbTable(String tableName) {
		return getJoinTable().getDbTable();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationshipReference());
	}
	
	public void addStrategy() {
		if (this.joinTable == null) {
			XmlJoinTable resourceJoinTable = OrmFactory.eINSTANCE.createXmlJoinTable();
			this.joinTable = getXmlContextNodeFactory().buildOrmJoinTable(this, resourceJoinTable);
			setResourceJoinTable(resourceJoinTable);
			this.firePropertyChanged(JOIN_TABLE_PROPERTY, null, this.joinTable);
		}
	}
	
	public void removeStrategy() {
		if (this.joinTable != null) {
			OrmJoinTable oldJoinTable = this.joinTable;
			this.joinTable = null;
			removeResourceJoinTable();
			this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, null);
		}
	}
	
	protected abstract void setResourceJoinTable(XmlJoinTable resourceJoinTable);

	
	// **************** join table *********************************************
	
	public OrmJoinTable getJoinTable() {
		return this.joinTable;
	}
	
	public OrmJoinTable addJoinTable() {
		addStrategy();
		return this.joinTable;
	}
	
	protected void setJoinTable_(OrmJoinTable newJoinTable) {
		OrmJoinTable oldJoinTable = this.joinTable;
		this.joinTable = newJoinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, newJoinTable);
	}
	
	public XmlJoinTable addResourceJoinTable() {
		XmlJoinTable resourceJoinTable = OrmFactory.eINSTANCE.createXmlJoinTable();
		setResourceJoinTable(resourceJoinTable);
		return resourceJoinTable;
	}
	
	protected boolean mayHaveJoinTable() {
		return getResourceJoinTable() != null 
			|| getRelationshipReference().mayHaveDefaultJoinTable();
	}
	
	
	// **************** resource -> context ************************************
	
	protected void initialize() {
		if (mayHaveJoinTable()) {
			this.joinTable = getXmlContextNodeFactory().buildOrmJoinTable(this, getResourceJoinTable());
		}
	}
	
	public void update() {
		if (mayHaveJoinTable()) {
			if (this.joinTable == null) {
				setJoinTable_(getXmlContextNodeFactory().buildOrmJoinTable(this, getResourceJoinTable()));
			}
			this.joinTable.update();
		}
		else {
			if (this.joinTable != null) {
				// no resource, so no clean up
				setJoinTable_(null);
			}
		}
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.joinTable != null) {
			this.joinTable.validate(messages, reporter);
		}
	}
}
