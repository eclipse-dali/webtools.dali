/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.AbstractOrmXmlContextNodeFactory;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmXml;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

public class GenericOrmXmlContextNodeFactory extends AbstractOrmXmlContextNodeFactory
{
	
	public OrmXml buildMappingFile(MappingFileRef parent, JpaXmlResource xmlResource) {
		return new GenericOrmXml(parent, xmlResource);
	}
	

}
