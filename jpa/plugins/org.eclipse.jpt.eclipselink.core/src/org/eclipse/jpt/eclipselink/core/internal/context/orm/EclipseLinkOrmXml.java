/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXml;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;

public class EclipseLinkOrmXml extends AbstractOrmXml
{
	public EclipseLinkOrmXml(MappingFileRef parent, JpaXmlResource resource) {
		super(parent, resource);
		if (!resource.getContentType().isKindOf(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have eclipselink orm xml content type"); //$NON-NLS-1$ 
		}
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}
	
	@Override
	protected XmlEntityMappings buildEntityMappingsResource() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlEntityMappings();
	}
	
	
	@Override
	protected EntityMappings buildEntityMappings(XmlEntityMappings xmlEntityMappings) {
		return getJpaFactory().buildEclipseLinkEntityMappings(this, xmlEntityMappings);
	}
	
	
	// ********** updating **********
	
	@Override
	public void update(JpaXmlResource resource) {
		if (!resource.getContentType().isKindOf(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have eclipselink orm xml content type"); //$NON-NLS-1$ 
		}
		super.update(resource);
	}

}
