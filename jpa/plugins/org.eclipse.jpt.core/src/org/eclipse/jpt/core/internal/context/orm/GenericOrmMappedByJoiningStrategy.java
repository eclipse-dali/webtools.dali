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

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedByJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOwnableRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlMappedByMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmMappedByJoiningStrategy 
	extends AbstractXmlContextNode
	implements OrmMappedByJoiningStrategy
{
	protected XmlMappedByMapping resource;
	
	protected String mappedByAttribute;
	
	
	public GenericOrmMappedByJoiningStrategy(
			OrmOwnableRelationshipReference parent,
			XmlMappedByMapping resource) {
		super(parent);
		this.resource = resource;
		this.mappedByAttribute = this.resource.getMappedBy();
	}
	
	
	@Override
	public OrmOwnableRelationshipReference getParent() {
		return (OrmOwnableRelationshipReference) super.getParent();
	}
	
	public OrmOwnableRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public OrmRelationshipMapping getRelationshipMapping() {
		return getParent().getRelationshipMapping();
	}
	
	public boolean relationshipIsOwnedBy(RelationshipMapping otherMapping) {
		String thisEntity = 
			(getRelationshipMapping().getEntity()) == null ?
				null : getRelationshipMapping().getEntity().getName();
		String targetEntity = 
			(getRelationshipMapping().getResolvedTargetEntity() == null) ?
				null : getRelationshipMapping().getResolvedTargetEntity().getName();
		return StringTools.stringsAreEqual(
				thisEntity,
				targetEntity)
			&& StringTools.stringsAreEqual(
				getMappedByAttribute(), 
				otherMapping.getName());
	}
	
	public String getMappedByAttribute() {
		return this.mappedByAttribute;
	}
	
	public void setMappedByAttribute(String newMappedByAttribute) {
		String oldMappedByAttribute = this.mappedByAttribute;
		this.mappedByAttribute = newMappedByAttribute;
		this.resource.setMappedBy(newMappedByAttribute);
		firePropertyChanged(MAPPED_BY_ATTRIBUTE_PROPERTY, oldMappedByAttribute, newMappedByAttribute);
	}
	
	protected void setMappedByAttribute_(String newMappedByAttribute) {
		String oldMappedByAttribute = this.mappedByAttribute;
		this.mappedByAttribute = newMappedByAttribute;
		firePropertyChanged(MAPPED_BY_ATTRIBUTE_PROPERTY, oldMappedByAttribute, newMappedByAttribute);
	}
	
	public void addStrategy() {
		if (this.mappedByAttribute == null) {
			setMappedByAttribute("");
		}
	}
	
	public void removeStrategy() {
		if (this.mappedByAttribute != null) {
			setMappedByAttribute(null);
		}
	}
	
	public void update() {
		setMappedByAttribute_(this.resource.getMappedBy());
	}
	
	public Iterator<String> candidateMappedByAttributeNames() {
		return getRelationshipMapping().allTargetEntityAttributeNames();	
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (getMappedByAttribute() == null) {
			return;
		}
		
		Entity targetEntity = this.getRelationshipMapping().getResolvedTargetEntity();
		if (targetEntity == null) {
			return;  // null target entity is validated elsewhere
		}
		
		PersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(this.mappedByAttribute);
		
		if (attribute == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
					new String[] {this.mappedByAttribute},
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}
		
		AttributeMapping mappedByMapping = attribute.getMapping();
		if ( ! this.getRelationshipReference().mayBeMappedBy(mappedByMapping)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
					new String[] {this.mappedByAttribute}, 
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}
		
		// if mappedByMapping is not a relationship owner, then it should have 
		// been flagged in above rule (mappedByIsValid)
		if (! ((RelationshipMapping) mappedByMapping).isRelationshipOwner()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
					this,
					this.getValidationTextRange()
				)
			);
		}
	}
	
	public TextRange getValidationTextRange() {
		TextRange mappedByTextRange = this.resource.getMappedByTextRange();
		return mappedByTextRange != null ? mappedByTextRange : getRelationshipReference().getValidationTextRange();
	}
}
