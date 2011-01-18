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

import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>entity-mappings</code> element
 */
public class GenericEntityMappings
	extends AbstractEntityMappings
{
	public GenericEntityMappings(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
	}
}
