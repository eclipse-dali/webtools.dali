/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
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
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmJoinTableJoiningStrategy 
	extends AbstractXmlContextNode
	implements OrmJoinTableJoiningStrategy
{
	protected XmlJoinTableMapping resource;
	
	protected OrmJoinTable joinTable;
	
	
	public GenericOrmJoinTableJoiningStrategy(
			OrmJoinTableEnabledRelationshipReference parent,
			XmlJoinTableMapping resource) {
		super(parent);
		this.resource = resource;
	}
	
	
	@Override
	public OrmJoinTableEnabledRelationshipReference getParent() {
		return (OrmJoinTableEnabledRelationshipReference) super.getParent();
	}
	
	public OrmJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public OrmRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationshipReference());
	}
	
	public void addStrategy() {
		if (this.joinTable == null) {
			XmlJoinTable resourceJoinTable = OrmFactory.eINSTANCE.createXmlJoinTable();
			this.joinTable = getJpaFactory().buildOrmJoinTable(this, resourceJoinTable);
			this.resource.setJoinTable(resourceJoinTable);
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
	
	public boolean isOverridableAssociation() {
		return getJpaPlatformVariation().isJoinTableOverridable();
	}
	
	
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
		this.resource.setJoinTable(resourceJoinTable);
		return resourceJoinTable;
	}
	
	public void removeResourceJoinTable() {
		this.resource.setJoinTable(null);
	}
	
	protected boolean mayHaveJoinTable() {
		return getResourceJoinTable() != null 
			|| getRelationshipReference().mayHaveDefaultJoinTable();
	}
	
	public XmlJoinTable getResourceJoinTable() {
		return this.resource.getJoinTable();
	}
	
	
	// **************** resource -> context ************************************
	
	public void update() {
		if (mayHaveJoinTable()) {
			if (this.joinTable == null) {
				setJoinTable_(getJpaFactory().buildOrmJoinTable(this, getResourceJoinTable()));
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
		if (this.joinTable != null && getRelationshipMapping().shouldValidateAgainstDatabase()) {
			this.joinTable.validate(messages, reporter);
		}
	}
	
	public TextRange getValidationTextRange() {
		return getRelationshipReference().getValidationTextRange();
	}
}
