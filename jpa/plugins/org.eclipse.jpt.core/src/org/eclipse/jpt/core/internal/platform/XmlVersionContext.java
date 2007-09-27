/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlVersionContext
	extends XmlAttributeContext
{
	private ColumnContext columnContext;
	
	public XmlVersionContext(
			IContext parentContext, XmlVersion xmlVersion) {
		super(parentContext, xmlVersion);
		this.columnContext = new ColumnContext(this, xmlVersion.getColumn());
	}
	
	@Override
	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaults(defaultsContext, monitor);
		this.columnContext.refreshDefaults(defaultsContext, monitor);
	}
	
	@Override
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY)) {
			if (attributeMapping().getPersistentAttribute().isVirtual()) {
				if (javaVersionMapping() != null) {
					if (!attributeMapping().getPersistentType().getMapping().isXmlMetadataComplete()) {
						return javaVersionMapping().getColumn().getName();
					}
					return javaVersionMapping().getColumn().getDefaultName();
				}
			}
			//doesn't matter what's in the java @Column annotation because it is completely
			//overriden as soon as you specify the attribute in xml.
			return attributeMapping().getPersistentAttribute().getName();
		}

		return super.getDefault(key, defaultsContext);
	}
	
	protected JavaVersion javaVersionMapping() {
		IAttributeMapping attributeMapping = javaAttributeMapping();
		if (attributeMapping.getKey() == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			return (JavaVersion) attributeMapping;
		}
		return null;
	}
	
	protected XmlVersion getVersion() {
		return (XmlVersion) attributeMapping();
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (entityOwned()) {
			addColumnMessages(messages);
		}
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		XmlVersion version = getVersion();
		ITypeMapping typeMapping = version.typeMapping();
		IColumn column = version.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.isConnected();
		
		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
			if (version.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
						new String[] {version.getPersistentAttribute().getName(), table, column.getName()},
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
			if (version.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {version.getPersistentAttribute().getName(), column.getName()}, 
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