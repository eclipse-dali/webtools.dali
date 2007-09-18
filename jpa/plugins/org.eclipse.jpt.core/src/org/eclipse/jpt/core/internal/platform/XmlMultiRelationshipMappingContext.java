/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.INonOwningMapping;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class XmlMultiRelationshipMappingContext
	extends XmlRelationshipMappingContext implements XmlJoinTableContext.ParentContext
{
	private XmlJoinTableContext joinTableContext;
	
	protected XmlMultiRelationshipMappingContext(
			IContext parentContext, XmlMultiRelationshipMappingInternal mapping) {
		super(parentContext, mapping);
		this.joinTableContext = new XmlJoinTableContext(this, mapping.getJoinTable());
	}
	
	@Override
	public void refreshDefaults(final DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaults(defaultsContext, monitor);
		this.joinTableContext.refreshDefaults(defaultsContext, monitor);
	}

	protected XmlMultiRelationshipMappingInternal multiRelationshipMapping() {
		return (XmlMultiRelationshipMappingInternal) relationshipMapping();
	}
	
	@Override
	public IMultiRelationshipMapping javaRelationshipMapping() {
		IAttributeMapping javaAttributeMapping = javaAttributeMapping();
		if (javaAttributeMapping instanceof IMultiRelationshipMapping) {
			return ((IMultiRelationshipMapping) javaAttributeMapping);
		}
		return null;
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (multiRelationshipMapping().getMappedBy() != null) {
			addMappedByMessages(messages);
		}
		else if (entityOwned()) {
			addJoinTableMessages(messages);
		}
	}
	
	protected void addJoinTableMessages(List<IMessage> messages) {
		joinTableContext.addToMessages(messages);
	}
	
	protected void addMappedByMessages(List<IMessage> messages) {
		XmlMultiRelationshipMapping mapping = multiRelationshipMapping();
		String mappedBy = mapping.getMappedBy();
		
		if (mapping.isJoinTableSpecified()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_MAPPED_BY_WITH_JOIN_TABLE,
						mapping.getJoinTable(), mapping.getJoinTable().validationTextRange())
				);
						
		}
		
		IEntity targetEntity = mapping.getResolvedTargetEntity();
		
		if (targetEntity == null) {
			// already have validation messages for that
			return;
		}
		
		IPersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(mappedBy);
		
		if (attribute == null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
						new String[] {mappedBy}, 
						mapping, mapping.mappedByTextRange())
				);
			return;
		}
		
		if (! mapping.mappedByIsValid(attribute.getMapping())) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
						new String[] {mappedBy}, 
						mapping, mapping.mappedByTextRange())
				);
			return;
		}
		
		INonOwningMapping mappedByMapping;
		try {
			mappedByMapping = (INonOwningMapping) attribute.getMapping();
		} catch (ClassCastException cce) {
			// there is no error then
			return;
		}
		
		if (mappedByMapping.getMappedBy() != null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
						mapping, mapping.mappedByTextRange())
				);
		}
	}
}
