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
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic;
import org.eclipse.jpt.core.internal.content.orm.XmlBasic;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlBasicContext extends XmlAttributeContext
{
	private ColumnContext columnContext;
	
	public XmlBasicContext(IContext parentContext, XmlBasic xmlBasic) {
		super(parentContext, xmlBasic);
		this.columnContext = new ColumnContext(this, xmlBasic.getColumn());
	}
	
	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		this.columnContext.refreshDefaults(defaultsContext);
	}
	
	@Override
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY)) {
			if (attributeMapping().getPersistentAttribute().isVirtual()) {
				if (javaBasicMapping() != null) {
					if (!attributeMapping().getPersistentType().getMapping().isXmlMetadataComplete()) {
						return javaBasicMapping().getColumn().getName();
					}
					return javaBasicMapping().getColumn().getDefaultName();
				}
			}
			//doesn't matter what's in the java @Column annotation because it is completely
			//overriden as soon as you specify the attribute in xml.
			return attributeMapping().getPersistentAttribute().getName();
		}

		return super.getDefault(key, defaultsContext);
	}
	
	protected JavaBasic javaBasicMapping() {
		IAttributeMapping attributeMapping = javaAttributeMapping();
		if (attributeMapping.getKey() == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			return (JavaBasic) attributeMapping;
		}
		return null;
	}
	
	protected XmlBasic getBasic() {
		return (XmlBasic) attributeMapping();
	}
	
	public void addToMessages(List<IMessage> messages) {
		if (entityOwned()) {
			addColumnMessages(messages);
		}
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		XmlBasic basic = getBasic();
		ITypeMapping typeMapping = basic.typeMapping();
		IColumn column = basic.getColumn();
		String table = column.getTable();
		boolean doContinue = column.isConnected();
		
		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
			if (basic.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
						new String[] {basic.getPersistentAttribute().getName(), table, column.getName()},
						column, column.getTableTextRange())
				);
			}
			else {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.getTableTextRange())
				);
			}
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			if (basic.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {basic.getPersistentAttribute().getName(), column.getName()}, 
						column, column.getNameTextRange())
				);
			}
			else {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.getNameTextRange())
				);
			}
		}
	}
}
