/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;

public abstract class XmlRelationshipMappingContext extends XmlAttributeContext
{
	protected XmlRelationshipMappingContext(IContext parentContext, XmlRelationshipMapping mapping) {
		super(parentContext, mapping);
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
	
	protected XmlRelationshipMapping relationshipMapping() {
		return (XmlRelationshipMapping) attributeMapping();
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		relationshipMapping().refreshDefaults(defaultsContext);
	}
	
	protected IRelationshipMapping javaRelationshipMapping() {
		IAttributeMapping javaAttributeMapping = javaAttributeMapping();
		if (javaAttributeMapping instanceof IRelationshipMapping) {
			return ((IRelationshipMapping) javaAttributeMapping);
		}
		return null;
	}
	
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_TARGET_ENTITY_KEY)) {
			IRelationshipMapping javaMapping = javaRelationshipMapping();
			if (javaMapping != null) {
				if (!relationshipMapping().isVirtual() && relationshipMapping().getPersistentType().getMapping().isXmlMetadataComplete()) {
					return javaMapping.getDefaultTargetEntity();
				}
				return javaMapping.getTargetEntity();
			}
			Attribute attribute = relationshipMapping().getPersistentAttribute().getAttribute();
			if (attribute != null) {
				IType iType = relationshipMapping().getPersistentType().findJdtType();
				if (iType != null) {
					return relationshipMapping().javaDefaultTargetEntity(defaultsContext.astRoot());
				}
			}
		}
		return super.getDefault(key, defaultsContext);	
	}
}
