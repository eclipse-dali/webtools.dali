/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaBasicMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;

public class EclipseLinkJavaBasicMappingImpl extends GenericJavaBasicMapping
{
	
	public EclipseLinkJavaBasicMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	@Override
	protected JavaConverter buildSpecifiedConverter(String converterType) {
		JavaConverter javaConverter = super.buildSpecifiedConverter(converterType);
		if (javaConverter != null) {
			return javaConverter;
		}
		if (converterType == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			return getJpaFactory().buildEclipseLinkJavaConvert(this, this.resourcePersistentAttribute);
		}
		return null;
	}
	
	@Override
	protected String specifiedConverterType(JavaResourcePersistentAttribute jrpa) {
		String specifiedConverterType = super.specifiedConverterType(jrpa);
		if (specifiedConverterType != null) {
			return specifiedConverterType;
		}
		if (jrpa.getAnnotation(ConvertAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkConvert.ECLIPSE_LINK_CONVERTER;
		}
		return null;
	}

}
