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
import java.util.List;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.CacheImpl.CacheAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ConvertImpl.ConvertAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ConverterImpl.ConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ExistenceCheckingImpl.ExistenceCheckingAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.MutableImpl.MutableAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ReadTransformerImpl.ReadTransformerAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.StructConverterImpl.StructConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.TransformationImpl.TransformationAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.TypeConverterImpl.TypeConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.WriteTransformerImpl.WriteTransformerAnnotationDefinition;

public class EclipseLinkJpaAnnotationProvider
	extends GenericJpaAnnotationProvider
{
	
	@Override
	protected void addTypeAnnotationDefinitionsTo(Collection<AnnotationDefinition> definitions) {
		super.addTypeAnnotationDefinitionsTo(definitions);
		definitions.add(CacheAnnotationDefinition.instance());
		definitions.add(ExistenceCheckingAnnotationDefinition.instance());
	}

	@Override
	//bug 245996 addresses how the attribute mapping annotations should be ordered
	protected void addAttributeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		super.addAttributeMappingAnnotationDefinitionsTo(definitions);
		definitions.add(TransformationAnnotationDefinition.instance());
	}
	
	@Override
	protected void addAttributeAnnotationDefinitionsTo(Collection<AnnotationDefinition> definitions) {
		super.addAttributeAnnotationDefinitionsTo(definitions);
		definitions.add(ConvertAnnotationDefinition.instance());
		definitions.add(ConverterAnnotationDefinition.instance());
		definitions.add(MutableAnnotationDefinition.instance());
		definitions.add(ReadTransformerAnnotationDefinition.instance());		
		definitions.add(StructConverterAnnotationDefinition.instance());		
		definitions.add(TypeConverterAnnotationDefinition.instance());		
		definitions.add(WriteTransformerAnnotationDefinition.instance());		
	}
	
}
