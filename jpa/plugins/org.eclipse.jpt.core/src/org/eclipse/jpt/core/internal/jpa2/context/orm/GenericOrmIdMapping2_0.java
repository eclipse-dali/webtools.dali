/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmIdMapping;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlId;

public class GenericOrmIdMapping2_0 extends AbstractOrmIdMapping<XmlId>
{
	
	public GenericOrmIdMapping2_0(OrmPersistentAttribute parent, XmlId resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	protected JpaFactory2_0 getJpaFactory() {
		return (JpaFactory2_0) super.getJpaFactory();
	}
	
}
