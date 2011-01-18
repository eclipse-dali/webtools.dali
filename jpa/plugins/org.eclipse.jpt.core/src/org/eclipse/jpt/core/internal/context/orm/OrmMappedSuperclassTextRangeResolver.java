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

import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class OrmMappedSuperclassTextRangeResolver
	implements PrimaryKeyTextRangeResolver
{
	private OrmMappedSuperclass mappedSuperclass;
	
	
	public OrmMappedSuperclassTextRangeResolver(OrmMappedSuperclass mappedSuperclass) {
		this.mappedSuperclass = mappedSuperclass;
	}
	
	
	public TextRange getTypeMappingTextRange() {
		return this.mappedSuperclass.getValidationTextRange();
	}
	
	public TextRange getIdClassTextRange() {
		return this.mappedSuperclass.getIdClassReference().getValidationTextRange();
	}
	
	public TextRange getAttributeMappingTextRange(String attributeName) {
		return this.getAttributeNamed(attributeName).getValidationTextRange();
	}

	protected OrmReadOnlyPersistentAttribute getAttributeNamed(String attributeName) {
		return this.mappedSuperclass.getPersistentType().getAttributeNamed(attributeName);
	}
}
