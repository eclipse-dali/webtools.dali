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
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmJoinTableJoiningStrategy extends AbstractXmlContextNode
	implements JoinTableJoiningStrategy
{
	protected XmlJoinTableMapping resource;
	
	protected OrmJoinTable joinTable;
	
	
	public OrmJoinTableJoiningStrategy(
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
	
	public void addStrategy() {
		if (this.joinTable == null) {
			setJoinTable_(getJpaFactory().buildOrmJoinTable(this, this.resource));
			addJoinTableResource();
		}
	}
	
	public void removeStrategy() {
		if (this.joinTable != null) {
			setJoinTable_(null);
			removeJoinTableResource();
		}
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
	
	protected XmlJoinTable addJoinTableResource() {
		XmlJoinTable resourceTable = OrmFactory.eINSTANCE.createXmlJoinTableImpl();
		this.resource.setJoinTable(resourceTable);
		return resourceTable;
	}
	
	protected void removeJoinTableResource() {
		this.resource.setJoinTable(null);
	}
	
	protected boolean mayHaveDefaultJoinTable() {
		return getJoinTableResource() != null 
			|| getRelationshipReference().mayHaveDefaultJoinTable();
	}
	
	protected XmlJoinTable getJoinTableResource() {
		return this.resource.getJoinTable();
	}
	
	
	// **************** resource -> context ************************************
	
	public void update() {
		if (mayHaveDefaultJoinTable()) {
			if (this.joinTable == null) {
				setJoinTable_(getJpaFactory().buildOrmJoinTable(this, this.resource));
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
