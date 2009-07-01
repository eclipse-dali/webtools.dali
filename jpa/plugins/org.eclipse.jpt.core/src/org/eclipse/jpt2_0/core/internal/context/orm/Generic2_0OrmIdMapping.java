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

import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmIdMapping;
import org.eclipse.jpt2_0.core.internal.platform.Generic2_0JpaFactory;
import org.eclipse.jpt2_0.core.resource.orm.XmlId;

public class Generic2_0OrmIdMapping extends AbstractOrmIdMapping<XmlId>
{
	
	public Generic2_0OrmIdMapping(OrmPersistentAttribute parent, XmlId resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	protected Generic2_0JpaFactory getJpaFactory() {
		return (Generic2_0JpaFactory) super.getJpaFactory();
	}
	
	@Override
	protected OrmGeneratorContainer buildGeneratorContainer() {
		return getJpaFactory().build2_0OrmGeneratorContainer(this, this.resourceAttributeMapping);
	}
}
