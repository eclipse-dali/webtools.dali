/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v1_1.context;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTextRangeResolver;

public class EclipseLinkTypeMappingValidator
	extends AbstractEclipseLinkTypeMappingValidator<TypeMapping>
{
	public EclipseLinkTypeMappingValidator(TypeMapping typeMapping, JavaResourceType jrt, TypeMappingTextRangeResolver textRangeResolver) {
		super(typeMapping, jrt, textRangeResolver);
	}
}
