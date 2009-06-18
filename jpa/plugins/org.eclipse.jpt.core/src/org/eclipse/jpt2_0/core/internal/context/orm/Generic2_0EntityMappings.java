/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt2_0.core.internal.platform.Generic2_0JpaFactory;
import org.eclipse.jpt2_0.core.resource.orm.XmlEntityMappings;

public class Generic2_0EntityMappings
	extends AbstractEntityMappings
{
	
	public Generic2_0EntityMappings(Generic2_0OrmXml parent, XmlEntityMappings xmlEntityMapping) {
		super(parent, xmlEntityMapping);
	}
	
	@Override
	protected PersistenceUnitMetadata buildPersistenceUnitMetadata() {
		return 	getJpaFactory().build2_0PersistenceUnitMetadata(this, (XmlEntityMappings) this.xmlEntityMappings);
	}
	
	@Override
	protected OrmPersistentType buildPersistentType(XmlTypeMapping resourceMapping) {
		return getJpaFactory().build2_0OrmPersistentType(this, resourceMapping);
	}
	
	// **************** JpaNode impl *******************************************
	
	@Override
	protected Generic2_0JpaFactory getJpaFactory() {
		return (Generic2_0JpaFactory) super.getJpaFactory();
	}
	
	@Override
	public Generic2_0OrmXml getParent() {
		return (Generic2_0OrmXml) super.getParent();
	}	

}
