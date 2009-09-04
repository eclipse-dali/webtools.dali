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

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXml;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * JPA 1.0 <code>orm.xml</code>
 */
public class GenericOrmXml2_0
	extends AbstractOrmXml
{	
	
	public GenericOrmXml2_0(MappingFileRef2_0 parent, JpaXmlResource resource) {
		super(parent, resource);
		this.checkResource(resource);
	}
	
	@Override
	protected XmlEntityMappings buildEntityMappingsResource() {
		return Orm2_0Factory.eINSTANCE.createXmlEntityMappings();
	}
	
	protected void checkResource(JpaXmlResource resource) {
		if ( ! resource.getContentType().isKindOf(JptCorePlugin.ORM2_0_XML_CONTENT_TYPE)) {
			throw new IllegalArgumentException("resource does not contain 2.0 orm xml content type: " + resource); //$NON-NLS-1$
		}
	}
	
	// ********** updating **********
	
	@Override
	public void update(JpaXmlResource resource) {
		this.checkResource(resource);
		super.update(resource);
	}

}
