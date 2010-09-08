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

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.JoinColumn.Owner;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.jpa1.context.InverseJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
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

	protected PersistentAttribute getPersistentAttribute() {
		return getRelationshipMapping().getPersistentAttribute();
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new JoinColumnValidator(this.getPersistentAttribute(), column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new InverseJoinColumnValidator(this.getPersistentAttribute(), column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}
}
