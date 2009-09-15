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

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentType;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;

public class GenericOrmPersistentType2_0
	extends AbstractOrmPersistentType
{
	
	public GenericOrmPersistentType2_0(EntityMappings parent, XmlTypeMapping resourceMapping) {
		super(parent, resourceMapping);
	}
	
	@Override
	protected Attributes createResourceAttributes() {
		return Orm2_0Factory.eINSTANCE.createAttributes();
	}

}
