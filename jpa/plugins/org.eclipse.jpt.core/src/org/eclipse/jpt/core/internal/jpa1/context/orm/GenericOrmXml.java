/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXml;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

public class GenericOrmXml
	extends AbstractOrmXml
{	
	
	public GenericOrmXml(MappingFileRef parent, JpaXmlResource resource) {
		super(parent, resource);
		if (!resource.getContentType().isKindOf(JptCorePlugin.ORM_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have orm xml content type"); //$NON-NLS-1$
		}
	}
	
	@Override
	protected XmlEntityMappings buildEntityMappingsResource() {
		return OrmFactory.eINSTANCE.createXmlEntityMappings();
	}
	
	@Override
	protected EntityMappings buildEntityMappings(XmlEntityMappings xmlEntityMappings) {
		return getJpaFactory().buildEntityMappings(this, xmlEntityMappings);
	}	
	
	// ********** updating **********
	
	@Override
	public void update(JpaXmlResource resource) {
		if (!resource.getContentType().isKindOf(JptCorePlugin.ORM_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have orm xml content type"); //$NON-NLS-1$
		}
		super.update(resource);
	}


}
