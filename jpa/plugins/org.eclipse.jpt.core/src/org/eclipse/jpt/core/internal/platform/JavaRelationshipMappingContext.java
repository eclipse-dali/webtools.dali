/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;

public abstract class JavaRelationshipMappingContext extends JavaAttributeContext
{	
	protected JavaRelationshipMappingContext(IContext parentContext, JavaRelationshipMapping mapping) {
		super(parentContext, mapping);
	}
	
	protected IRelationshipMapping getMapping() {
		return (IRelationshipMapping) super.getMapping();
	}
	
	@Override
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_TARGET_ENTITY_KEY)) {
			return getMapping().fullyQualifiedTargetEntity(defaultsContext.astRoot());
		}
		return super.getDefault(key, defaultsContext);
	}
	
}
