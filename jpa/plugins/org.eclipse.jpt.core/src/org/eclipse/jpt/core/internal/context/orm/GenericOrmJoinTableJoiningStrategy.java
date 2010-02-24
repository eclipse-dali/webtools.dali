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

import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping;
import org.eclipse.jpt.core.utility.TextRange;

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
	protected void setResourceJoinTable(XmlJoinTable resourceJoinTable) {
		this.resource.setJoinTable(resourceJoinTable);
	}
	
	public boolean isOverridableAssociation() {
		return getJpaPlatformVariation().isJoinTableOverridable();
	}
	
	public boolean shouldValidateAgainstDatabase() {
		return getRelationshipMapping().shouldValidateAgainstDatabase();
	}
	
	// **************** join table *********************************************
	
	public void removeResourceJoinTable() {
		this.resource.setJoinTable(null);
	}
	
	public XmlJoinTable getResourceJoinTable() {
		return this.resource.getJoinTable();
	}

	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange() {
		return getRelationshipReference().getValidationTextRange();
	}
}
