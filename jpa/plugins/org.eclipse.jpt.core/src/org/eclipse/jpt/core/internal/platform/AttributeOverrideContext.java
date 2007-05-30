/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AttributeOverrideContext extends BaseContext
{
	IAttributeOverride attributeOverride;
	
	ColumnContext columnContext;
	
	public AttributeOverrideContext(IContext parentContext, IAttributeOverride attributeOverride) {
		super(parentContext);
		this.attributeOverride = attributeOverride;
		this.columnContext = buildColumnContext();
	}
	
	@Override
	protected void initialize() {}
	
	protected ColumnContext buildColumnContext() {
		return new ColumnContext(this, this.attributeOverride.getColumn());
	}
	
	public DefaultsContext wrapDefaultsContext(final DefaultsContext defaultsContext) {
		return new DefaultsContext() {
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return defaultsContext.persistentType(fullyQualifiedTypeName);
			}
		
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY)) {
					return buildDefaultColumnName();
				}
				else if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_TABLE_KEY)) {
					String tableName = buildDefaultTableName();
					if (tableName != null) {
						return tableName;
					}
				
				}
				return defaultsContext.getDefault(key);
			}
		};
	}
	
	/**
	 * The mapping that the attribute override is overriding
	 */
	protected IColumnMapping columnMapping() {
		return (IColumnMapping) this.attributeOverride.getOwner().attributeMapping(this.attributeOverride.getName());
	}
	
	protected String buildDefaultColumnName() {
		IColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		return columnMapping.getColumn().getName();
	}
	
	protected String buildDefaultTableName() {
		IColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		return columnMapping.getColumn().getSpecifiedTable();
	}
	
	public void refreshDefaults(DefaultsContext defaultsContext) {
		this.columnContext.refreshDefaults(wrapDefaultsContext(defaultsContext));
	}

	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
	
		addColumnMessages(messages);
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		ITypeMapping typeMapping = attributeOverride.getOwner().getTypeMapping();
		IColumn column = attributeOverride.getColumn();
		String table = column.getTable();
		boolean doContinue = column.isConnected();
		
		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
			if (attributeOverride.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_TABLE,
						new String[] {attributeOverride.getName(), table, column.getName()},
						column, column.tableTextRange())
				);
			}
			else {
				messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
							new String[] {table, column.getName()}, 
							column, column.tableTextRange())
					);
			}
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			if (attributeOverride.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
						new String[] {attributeOverride.getName(), column.getName()}, 
						column, column.nameTextRange())
				);
			}
			else {
				messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
							new String[] {column.getName()}, 
							column, column.nameTextRange())
					);
			}
		}
	}
}
