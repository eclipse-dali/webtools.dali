/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.platform.base.IJpaBaseContextFactory;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;


public interface IXmlTypeMappingProvider
{
	String key();
	
	XmlTypeMapping buildTypeMapping(IJpaBaseContextFactory factory, XmlPersistentType parent);
	
	/**
	 * create and orm resource mapping and add it to the entityMappings.  Also
	 * set the className on the new resource mapping.
	 */
	void createAndAddOrmResourceMapping(EntityMappings entityMappings, String className);

}
