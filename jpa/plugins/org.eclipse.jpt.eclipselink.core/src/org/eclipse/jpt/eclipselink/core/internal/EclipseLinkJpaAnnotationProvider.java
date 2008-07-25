/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import java.util.Collection;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.CacheImpl.CacheAnnotationDefinition;

public class EclipseLinkJpaAnnotationProvider
	extends GenericJpaAnnotationProvider
{
	
	@Override
	protected void addTypeAnnotationDefinitionsTo(Collection<AnnotationDefinition> definitions) {
		super.addTypeAnnotationDefinitionsTo(definitions);
		definitions.add(CacheAnnotationDefinition.instance());
	}

}
