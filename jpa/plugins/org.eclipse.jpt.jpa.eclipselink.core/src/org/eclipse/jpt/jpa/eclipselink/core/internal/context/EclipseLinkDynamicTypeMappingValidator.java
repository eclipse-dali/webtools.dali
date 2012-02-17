/*******************************************************************************
 *  Copyright (c) 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkDynamicTypeMappingValidator
	extends AbstractEclipseLinkTypeMappingValidator<TypeMapping>
{
	public EclipseLinkDynamicTypeMappingValidator(TypeMapping typeMapping) {
		super(typeMapping, null, null);
	}

	@Override
	protected void validateType(List<IMessage> messages) {
		//no validation for a dynamic type
	}
}
