/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JoinTableContext extends BaseContext
{
	private IJoinTable table;
	
	private Collection<JoinColumnContext> joinColumnContexts;
	
	private Collection<JoinColumnContext> inverseJoinColumnContexts;
	
	public JoinTableContext(IContext parentContext, IJoinTable table) {
		super(parentContext);
		this.table = table;
		this.joinColumnContexts = buildJoinColumnContexts();
		this.inverseJoinColumnContexts = buildInverseJoinColumnContexts();
	}
	
	@Override
	protected void initialize() {}
	
	protected Collection<JoinColumnContext> buildJoinColumnContexts() {
		Collection<JoinColumnContext> contexts = new ArrayList<JoinColumnContext>();
		for (IJoinColumn joinColumn : this.table.getJoinColumns() ) {
			contexts.add(buildJoinColumnContext(joinColumn));
		}
		return contexts;
	}
	
	protected JoinColumnContext buildJoinColumnContext(IJoinColumn joinColumn) {
		return new JoinColumnContext(this, joinColumn);
	}
	
	protected Collection<JoinColumnContext> buildInverseJoinColumnContexts() {
		Collection<JoinColumnContext> contexts = new ArrayList<JoinColumnContext>();
		for (IJoinColumn joinColumn : this.table.getInverseJoinColumns() ) {
			contexts.add(buildInverseJoinColumnContext(joinColumn));
		}
		return contexts;
	}
	
	protected JoinColumnContext buildInverseJoinColumnContext(IJoinColumn joinColumn) {
		return new JoinColumnContext(this, joinColumn);
	}	
	
	public IJoinTable getTable() {
		return this.table;
	}
	
	@Override
	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaults(defaultsContext, monitor);
		this.table.refreshDefaults(wrapDefaultsContext(defaultsContext));
		DefaultsContext joinColumnsDefaultsContext = wrapDefaultsContextForJoinColumn(defaultsContext);
		for (JoinColumnContext context : this.joinColumnContexts) {
			context.refreshDefaults(joinColumnsDefaultsContext, monitor);
		}
		DefaultsContext inverseJoinColumnsDefaultsContext = wrapDefaultsContextForInverseJoinColumn(defaultsContext);
		for (JoinColumnContext context : this.inverseJoinColumnContexts) {
			context.refreshDefaults(inverseJoinColumnsDefaultsContext, monitor);
		}
	}
	
	protected DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_TABLE_NAME_KEY)) {
					return joinTableDefaultName(this);
				}
				return super.getDefault(key);
			}
		};		
	}

	protected DefaultsContext wrapDefaultsContextForJoinColumn(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				/**
				 * by default, the join column is, obviously, in the join table;
				 * not sure whether it can be anywhere else...
				 */
				if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_TABLE_KEY)) {
					return getTable().getName();
				}
				return super.getDefault(key);
			}
		};
	}
	
	protected DefaultsContext wrapDefaultsContextForInverseJoinColumn(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_TABLE_KEY)) {
					return getTable().getName();
				}
				return super.getDefault(key);
			}
		};
	}

	protected String joinTableDefaultName(DefaultsContext defaultsContext) {
		String tableName = relationshipMapping().typeMapping().getTableName();
		if (tableName == null) {
			return null;
		}
		IEntity targetEntity = targetEntity(defaultsContext);
		if (targetEntity == null) {
			return null;
		}
		ITable targetTable = targetEntity.getTable();
		return (targetTable == null) ? null : tableName + "_" + targetTable.getName();
	}
	
	protected IEntity targetEntity(DefaultsContext defaultsContext) {
		String targetEntity = relationshipMapping().fullyQualifiedTargetEntity(defaultsContext.astRoot());
		if (targetEntity == null) {
			return null;
		}
		IPersistentType persistentType = defaultsContext.persistentType(targetEntity);
		if (persistentType == null) {
			return null;
		}
		ITypeMapping typeMapping = persistentType.getMapping();
		if (typeMapping instanceof IEntity) {
			return (IEntity) typeMapping;
		}
		return null;
	}

	protected IRelationshipMapping relationshipMapping() {
		return this.table.relationshipMapping();
	}
	
	/** used internally as a mechanism to short circuit continued message adding */
	private boolean doContinue;
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		addTableMessages(messages);
		
		if (doContinue) {
			for (JoinColumnContext context : joinColumnContexts) {
				context.addToMessages(messages);
			}
			
			for (JoinColumnContext context : inverseJoinColumnContexts) {
				context.addToMessages(messages);
			}
		}
	}
	
	protected void addTableMessages(List<IMessage> messages) {
		doContinue = table.isConnected();
		String schema = table.getSchema();
		XmlRelationshipMapping mapping = (XmlRelationshipMapping) table.relationshipMapping();
		
		if (doContinue && ! table.hasResolvedSchema()) {
			if (mapping.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA,
						new String[] {mapping.getPersistentAttribute().getName(), schema, table.getName()}, 
						table, table.schemaTextRange())
				);
				
			}
			else {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, table.getName()}, 
						table, table.schemaTextRange())
				);
			}
			doContinue = false;
		}
		
		if (doContinue && ! table.isResolved()) {
			if (mapping.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME,
						new String[] {mapping.getPersistentAttribute().getName(), table.getName()}, 
						table, table.nameTextRange())
				);
			}
			else {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_TABLE_UNRESOLVED_NAME,
						new String[] {table.getName()}, 
						table, table.nameTextRange())
				);
			}
		}
	}
}
