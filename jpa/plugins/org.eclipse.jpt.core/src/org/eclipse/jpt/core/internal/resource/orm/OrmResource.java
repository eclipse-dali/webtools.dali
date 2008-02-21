/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.internal.resource.orm.translators.EntityMappingsTranslator;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OrmResource extends JpaXmlResource 
{
	public OrmResource(Renderer aRenderer) {
		super(aRenderer);
	}

	public OrmResource(URI uri, Renderer aRenderer) {
		super(uri, aRenderer);
	}
	
	public Translator getRootTranslator() {
		return EntityMappingsTranslator.INSTANCE;
	}
	
	@Override
	public void javaElementChanged(ElementChangedEvent event) {
		// TODO Auto-generated method stub	
	}
	
	public EntityMappings getEntityMappings() {
		return (EntityMappings) getRootObject();
	}
}
