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
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Attributes;

public interface IXmlAttributeMappingProvider
{
	String key();
	
	XmlAttributeMapping buildAttributeMapping(IJpaBaseContextFactory factory, XmlPersistentAttribute parent);
	
	
	/**
	 * create and orm resource mapping and add it to the attributes.  Also
	 * set the attributeName on the new resource mapping.
	 */
	AttributeMapping createAndAddOrmResourceMapping(XmlPersistentAttribute xmlPersistentAttribute, Attributes attributes);

}
