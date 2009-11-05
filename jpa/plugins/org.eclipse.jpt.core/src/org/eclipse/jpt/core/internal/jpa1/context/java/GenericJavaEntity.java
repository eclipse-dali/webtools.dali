/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

public class GenericJavaEntity
	extends AbstractJavaEntity
{
	
	protected final JavaCacheable2_0 cacheable;

	public GenericJavaEntity(JavaPersistentType parent) {
		super(parent);
		this.cacheable = ((JpaFactory2_0) this.getJpaFactory()).buildJavaCacheable(this);
	}
	
	@Override
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		super.initialize(resourcePersistentType);
		this.cacheable.initialize(resourcePersistentType);
	}
	
	@Override
	public void update(JavaResourcePersistentType resourcePersistentType) {
		super.update(resourcePersistentType);
		this.cacheable.update(resourcePersistentType);
	}
	
	//****************** Entity2_0 implementation *******************

	public JavaCacheable2_0 getCacheable() {
		return this.cacheable;
	}
	
	public boolean calculateDefaultCacheable() {		
		CacheableHolder2_0 parentEntity = (CacheableHolder2_0) getParentEntity();
		if (parentEntity != null) {
			return parentEntity.getCacheable().isCacheable();
		}
		return false;//((PersistenceUnit2_0) getPersistenceUnit()).calculateDefaultCacheable();
	}

}
