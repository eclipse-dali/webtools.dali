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
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class XmlMultiRelationshipMappingContext
	extends XmlRelationshipMappingContext
{
	private JoinTableContext joinTableContext;
	
	protected XmlMultiRelationshipMappingContext(
			IContext parentContext, XmlMultiRelationshipMappingInternal mapping) {
		super(parentContext, mapping);
		this.joinTableContext = new JoinTableContext(this, mapping.getJoinTable());
	}
	
	@Override
	public void refreshDefaults(final DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		this.joinTableContext.refreshDefaults(defaultsContext);
		multiRelationshipMapping().getOrderBy().refreshDefaults(defaultsContext);
	}
	
	@Override
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_TABLE_NAME_KEY)) {
			return joinTableDefaultName(defaultsContext);
		}
		return super.getDefault(key, defaultsContext);
	}
	
	protected String joinTableDefaultName(DefaultsContext defaultsContext) {
		ITable owningTable = multiRelationshipMapping().getEntity().getTable();
		if (owningTable == null) {
			return null;
		}
		IEntity targetEntity = targetEntity(defaultsContext);
		if (targetEntity == null) {
			return null;
		}
		ITable targetTable = targetEntity.getTable();
		return (targetTable == null) ? null : owningTable.getName() + "_" + targetTable.getName();
	}

	protected XmlMultiRelationshipMappingInternal multiRelationshipMapping() {
		return (XmlMultiRelationshipMappingInternal) relationshipMapping();
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
		}
	}
}
