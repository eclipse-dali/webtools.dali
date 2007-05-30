/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;

public class XmlAttributeOverrideContext extends AttributeOverrideContext
{
	
	public XmlAttributeOverrideContext(IContext parentContext, IAttributeOverride attributeOverride) {
		super(parentContext, attributeOverride);
		this.attributeOverride = attributeOverride;
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
	
	private IAttributeOverride javaAttributeOverride() {
		JavaEntity javaEntity = ((XmlEntityContext) getParentContext()).getJavaEntity();
		return javaEntity.attributeOverrideNamed(this.attributeOverride.getName());
	}

}
