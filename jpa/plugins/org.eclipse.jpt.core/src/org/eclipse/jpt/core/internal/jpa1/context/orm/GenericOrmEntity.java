/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEntity;
import org.eclipse.jpt.core.internal.jpa1.context.GenericEntityValidator;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.orm.XmlEntity;

public class GenericOrmEntity
	extends AbstractOrmEntity

{

	protected final OrmCacheable2_0 cacheable;
	
	public GenericOrmEntity(OrmPersistentType parent, XmlEntity resourceMapping) {
		super(parent, resourceMapping);
		this.cacheable = ((OrmXml2_0ContextNodeFactory) getXmlContextNodeFactory()).buildOrmCacheable(this, resourceMapping);
	}
	
	public OrmCacheable2_0 getCacheable() {
		return this.cacheable;
	}
	
	public boolean calculateDefaultCacheable() {
		if (!isMetadataComplete()) {
			CacheableHolder2_0 javaEntity = (CacheableHolder2_0) getJavaEntity();
			if (javaEntity != null) {
				return javaEntity.getCacheable().isCacheable();
			}
		}
		
		CacheableHolder2_0 parentEntity = (CacheableHolder2_0) getParentEntity();
		if (parentEntity != null) {
			return parentEntity.getCacheable().isCacheable();
		}
		return ((PersistenceUnit2_0) getPersistenceUnit()).calculateDefaultCacheable();
	}
	
	@Override
	public void update() {
		super.update();
		getCacheable().update();
	}


	//********** Validation ********************************************

	@Override
	protected JptValidator buildEntityValidator() {
		return new GenericEntityValidator(this, getJavaResourcePersistentType(), buildTextRangeResolver());
	}
}