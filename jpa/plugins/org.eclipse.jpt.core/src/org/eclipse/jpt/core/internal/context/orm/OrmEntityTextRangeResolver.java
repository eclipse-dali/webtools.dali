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
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.internal.context.EntityTextRangeResolver;

public class OrmEntityTextRangeResolver
	implements EntityTextRangeResolver
{
	private OrmEntity entity;
	
	
	public OrmEntityTextRangeResolver(OrmEntity entity) {
		this.entity = entity;
	}
	
	
	public TextRange getTypeMappingTextRange() {
		return this.entity.getValidationTextRange();
	}
	
	public TextRange getIdClassTextRange() {
		return this.entity.getIdClassReference().getValidationTextRange();
	}
	
	public TextRange getAttributeMappingTextRange(String attributeName) {
		return this.getAttributeNamed(attributeName).getValidationTextRange();
	}

	protected OrmReadOnlyPersistentAttribute getAttributeNamed(String attributeName) {
		return this.entity.getPersistentType().getAttributeNamed(attributeName);
	}
}
