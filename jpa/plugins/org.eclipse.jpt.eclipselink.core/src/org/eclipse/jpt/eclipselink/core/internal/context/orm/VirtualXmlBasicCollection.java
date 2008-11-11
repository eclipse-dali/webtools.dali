/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualXmlBasicCollection extends VirtualXmlAttributeMapping<JavaBasicCollectionMapping> implements XmlBasicCollection
{

	public VirtualXmlBasicCollection(OrmTypeMapping ormTypeMapping, JavaBasicCollectionMapping javaBasicCollectionMapping) {
		super(ormTypeMapping, javaBasicCollectionMapping);
	}
}
