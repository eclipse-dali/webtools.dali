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

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXml;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt2_0.core.internal.platform.Generic2_0JpaFactory;
import org.eclipse.jpt2_0.core.resource.orm.Orm2_0Factory;
import org.eclipse.jpt2_0.core.resource.orm.XmlEntityMappings;

public class Generic2_0OrmXml
	extends AbstractOrmXml
{	
	
	public Generic2_0OrmXml(MappingFileRef parent, JpaXmlResource resource) {
		super(parent, resource);
		if (!resource.getContentType().isKindOf(JptCorePlugin.ORM2_0_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have2.0  orm xml content type"); //$NON-NLS-1$
		}
	}
	
	@Override
	protected Generic2_0JpaFactory getJpaFactory() {
		return (Generic2_0JpaFactory) super.getJpaFactory();
	}
	
	@Override
	protected XmlEntityMappings buildEntityMappingsResource() {
		return Orm2_0Factory.eINSTANCE.createXmlEntityMappings();
	}
	
	@Override
	protected EntityMappings buildEntityMappings(org.eclipse.jpt.core.resource.orm.XmlEntityMappings xmlEntityMappings) {
		return getJpaFactory().build2_0EntityMappings(this, (XmlEntityMappings) xmlEntityMappings);
	}	
	
	// ********** updating **********
	
	@Override
	public void update(JpaXmlResource resource) {
		if (!resource.getContentType().isKindOf(JptCorePlugin.ORM2_0_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have 2.0 orm xml content type"); //$NON-NLS-1$
		}
		super.update(resource);
	}


}
