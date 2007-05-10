/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class JavaMultiRelationshipMappingContext extends JavaRelationshipMappingContext
{
	private JoinTableContext joinTableContext;
	
		
	protected JavaMultiRelationshipMappingContext(
			IContext parentContext, JavaMultiRelationshipMapping mapping) {
		super(parentContext, mapping);
		this.joinTableContext = new JoinTableContext(this, mapping.getJoinTable());
	}
	
	@Override
	public void refreshDefaultsInternal(DefaultsContext defaultsContext) {
		super.refreshDefaultsInternal(defaultsContext);
		this.joinTableContext.refreshDefaults(defaultsContext);
		getMapping().getOrderBy().refreshDefaults(defaultsContext);
	}
	
	@Override
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_TABLE_NAME_KEY)) {
			return joinTableDefaultName(defaultsContext);
		}
		return super.getDefault(key, defaultsContext);
	}
	
	protected String joinTableDefaultName(DefaultsContext defaultsContext) {
		IEntity entity = getMapping().getEntity();
		if (entity == null) {
			return null;
		}
		ITable owningTable = entity.getTable();
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

	
	protected JavaMultiRelationshipMapping getMapping() {
		return (JavaMultiRelationshipMapping) super.getMapping();
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (getMapping().getMappedBy() != null) {
			addMappedByMessages(messages);
		}
		else {
			addJoinTableMessages(messages);
		}
	}
	
	protected void addJoinTableMessages(List<IMessage> messages) {
		if (getMapping().getMappedBy() != null) {
			//do not add joinTable problems for the non-owning side, this is defined
			//by the side that specifies mappedBy
			return;
		}
		IJoinTable joinTable = getMapping().getJoinTable();
		boolean doContinue = joinTable.isConnected();
		String schema = joinTable.getSchema();
		
		if (doContinue && ! joinTable.hasResolvedSchema()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, joinTable.getName()}, 
						joinTable, joinTable.getSchemaTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! joinTable.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_TABLE_UNRESOLVED_NAME,
						new String[] {joinTable.getName()}, 
						joinTable, joinTable.getNameTextRange())
				);
			doContinue = false;
		}
		
		for (Iterator<IJoinColumn> stream = joinTable.getJoinColumns().iterator(); stream.hasNext(); ) {
			IJoinColumn joinColumn = stream.next();
			
			if (doContinue && ! joinColumn.isResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, joinColumn.getNameTextRange())
				);
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, joinColumn.getReferencedColumnNameTextRange())
				);
			}
		}
		
		for (Iterator<IJoinColumn> stream = joinTable.getInverseJoinColumns().iterator(); stream.hasNext(); ) {
			IJoinColumn joinColumn = stream.next();
			
			if (doContinue && ! joinColumn.isResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, joinColumn.getNameTextRange())
				);
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, joinColumn.getReferencedColumnNameTextRange())
				);
			}
		}
	}
	
	protected void addMappedByMessages(List<IMessage> messages) {
		JavaMultiRelationshipMapping mapping = getMapping();
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
						mapping, mapping.getMappedByTextRange())
				);
			return;
		}
		
		if (! mapping.mappedByIsValid(attribute.getMapping())) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
						new String[] {mappedBy}, 
						mapping, mapping.getMappedByTextRange())
				);
		}
	}
}
