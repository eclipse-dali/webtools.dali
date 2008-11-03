/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFile;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators.EclipseLinkEntityMappingsTranslator;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkOrmResource
	extends OrmResource
{
	public EclipseLinkOrmResource(URI uri, Renderer renderer) {
		super(uri, renderer);
	}

	@Override
	public XmlEntityMappings getEntityMappings() {
		return (XmlEntityMappings) super.getEntityMappings();
	}
	
	@Override
	public Translator getRootTranslator() {
		return EclipseLinkEntityMappingsTranslator.INSTANCE;
	}

	@Override
	public String getType() {
		return EclipseLinkJpaFile.ECLIPSELINK_ORM_RESOURCE_TYPE;
	}

}
