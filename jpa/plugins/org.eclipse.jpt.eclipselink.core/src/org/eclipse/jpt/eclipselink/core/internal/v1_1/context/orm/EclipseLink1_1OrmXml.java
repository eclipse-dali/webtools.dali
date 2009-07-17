/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXml;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.EclipseLink1_1JpaFactory;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.EclipseLink1_1OrmFactory;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlEntityMappings;

public class EclipseLink1_1OrmXml extends AbstractOrmXml
{
	public EclipseLink1_1OrmXml(MappingFileRef parent, JpaXmlResource resource) {
		super(parent, resource);
		if (!resource.getContentType().isKindOf(JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have eclipselink 1.1 orm xml content type"); //$NON-NLS-1$ 
		}
	}
	
	@Override
	protected EclipseLink1_1JpaFactory getJpaFactory() {
		return (EclipseLink1_1JpaFactory) super.getJpaFactory();
	}
	
	@Override
	protected XmlEntityMappings buildEntityMappingsResource() {
		return EclipseLink1_1OrmFactory.eINSTANCE.createXmlEntityMappings();
	}
	
	@Override
	protected EntityMappings buildEntityMappings(org.eclipse.jpt.core.resource.orm.XmlEntityMappings xmlEntityMappings) {
		return getJpaFactory().buildEclipseLink1_1EntityMappings(this, (XmlEntityMappings) xmlEntityMappings);
	}
	
	
	// ********** updating **********
	
	@Override
	public void update(JpaXmlResource resource) {
		if (!resource.getContentType().isKindOf(JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have eclipselink 1.1 orm xml content type"); //$NON-NLS-1$ 
		}
		super.update(resource);
	}
}
