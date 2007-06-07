/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;

public class XmlAttributeOverrideContext extends AttributeOverrideContext
{
	
	public XmlAttributeOverrideContext(ParentContext parentContext, IAttributeOverride attributeOverride) {
		super(parentContext, attributeOverride);
		this.attributeOverride = attributeOverride;
	}
	
	@Override
	public ParentContext getParentContext() {
		return (ParentContext) super.getParentContext();
	}
	
	@Override
	protected String buildDefaultColumnName() {
		IColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		if (this.attributeOverride.isVirtual()) {
			if (!((XmlTypeMapping) this.attributeOverride.getOwner().getTypeMapping()).isXmlMetadataComplete()) {
				IAttributeOverride javaAttributeOverride = javaAttributeOverride();
				if (javaAttributeOverride != null) {
					return javaAttributeOverride.getColumn().getName();
				}
			}
			return columnMapping.getColumn().getName();
		}
		
		return columnMapping.getColumn().getName();
	}
	
	@Override
	protected String buildDefaultTableName() {
		IColumnMapping columnMapping = columnMapping();
		if (columnMapping == null) {
			return null;
		}
		if (this.attributeOverride.isVirtual()) {
			if (!((XmlTypeMapping) this.attributeOverride.getOwner().getTypeMapping()).isXmlMetadataComplete()) {
				IAttributeOverride javaAttributeOverride = javaAttributeOverride();
				if (javaAttributeOverride != null) {
					return javaAttributeOverride.getColumn().getTable();
				}
			}
			return columnMapping.getColumn().getTable();
		}
		
		return columnMapping.getColumn().getTable();
	}
	
	public interface ParentContext extends IContext {
		/**
		 * Return the JavaAttributeOverride that corresponds to the xml attribute override
		 * with the given name.  Return null if it does not exist
		 */
		IAttributeOverride javaAttributeOverride(String overrideName);
	}
	
	private IAttributeOverride javaAttributeOverride() {
		return getParentContext().javaAttributeOverride(this.attributeOverride.getName());
	}

}
