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
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;

public class OrmTypeMappingTextRangeResolver
	implements TypeMappingTextRangeResolver
{
	private OrmTypeMapping typeMapping;

	public OrmTypeMappingTextRangeResolver(OrmTypeMapping typeMapping) {
		this.typeMapping = typeMapping;
	}

	public TextRange getTypeMappingTextRange() {
		return this.typeMapping.getValidationTextRange();
	}
}
