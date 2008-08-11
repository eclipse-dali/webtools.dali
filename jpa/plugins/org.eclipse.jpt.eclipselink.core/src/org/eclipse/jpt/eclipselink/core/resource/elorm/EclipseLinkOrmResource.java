/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.elorm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators.EntityMappingsTranslator;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class EclipseLinkOrmResource extends JpaXmlResource
{
	public EclipseLinkOrmResource(Renderer aRenderer) {
		super(aRenderer);
	}

	public EclipseLinkOrmResource(URI uri, Renderer aRenderer) {
		super(uri, aRenderer);
	}
	
	public Translator getRootTranslator() {
		return EntityMappingsTranslator.INSTANCE;
	}
	
	@Override
	public void javaElementChanged(ElementChangedEvent event) {
		// TODO
	}
	
	@Override
	public void updateFromResource() {
		// TODO
	}
	
	public XmlEntityMappings getEntityMappings() {
		return (XmlEntityMappings) getRootObject();
	}
}
