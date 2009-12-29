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
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmJoinTableJoiningStrategy 
	extends AbstractOrmJoinTableJoiningStrategy
{
	protected XmlJoinTableMapping resource;
	
	
	public GenericOrmJoinTableJoiningStrategy(
			OrmJoinTableEnabledRelationshipReference parent,
			XmlJoinTableMapping resource) {
		super(parent);
		this.resource = resource;
		this.initialize();
	}
	
	
	@Override
	public OrmJoinTableEnabledRelationshipReference getParent() {
		return (OrmJoinTableEnabledRelationshipReference) super.getParent();
	}
	
	@Override
	public OrmJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	@Override
	public OrmRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}

	@Override
	protected void setResourceJoinTable(XmlJoinTable resourceJoinTable) {
		this.resource.setJoinTable(resourceJoinTable);
	}
	
	public boolean isOverridableAssociation() {
		return getJpaPlatformVariation().isJoinTableOverridable();
	}
	
	
	// **************** join table *********************************************
	
	@Override
	public OrmJoinTable getJoinTable() {
		return this.joinTable;
	}
	
	@Override
	public OrmJoinTable addJoinTable() {
		addStrategy();
		return this.joinTable;
	}
	
	@Override
	protected void setJoinTable_(OrmJoinTable newJoinTable) {
		OrmJoinTable oldJoinTable = this.joinTable;
		this.joinTable = newJoinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, newJoinTable);
	}
	
	@Override
	public XmlJoinTable addResourceJoinTable() {
		XmlJoinTable resourceJoinTable = OrmFactory.eINSTANCE.createXmlJoinTable();
		this.resource.setJoinTable(resourceJoinTable);
		return resourceJoinTable;
	}
	
	public void removeResourceJoinTable() {
		this.resource.setJoinTable(null);
	}
	
	@Override
	protected boolean mayHaveJoinTable() {
		return getResourceJoinTable() != null 
			|| getRelationshipReference().mayHaveDefaultJoinTable();
	}
	
	public XmlJoinTable getResourceJoinTable() {
		return this.resource.getJoinTable();
	}
	
	
	// **************** resource -> context ************************************
	
	@Override
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
	
	public TextRange getValidationTextRange() {
		return getRelationshipReference().getValidationTextRange();
	}
}
