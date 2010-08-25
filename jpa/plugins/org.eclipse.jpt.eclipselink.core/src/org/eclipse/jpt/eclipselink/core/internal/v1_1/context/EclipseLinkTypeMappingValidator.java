/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context;

import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

public class EclipseLinkTypeMappingValidator
	extends AbstractEclipseLinkTypeMappingValidator<TypeMapping>
{
	public EclipseLinkTypeMappingValidator(TypeMapping typeMapping, JavaResourcePersistentType jrpt, TypeMappingTextRangeResolver textRangeResolver) {
		super(typeMapping, jrpt, textRangeResolver);
	}
}
