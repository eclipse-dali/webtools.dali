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
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Interface to resolve text ranges on persistent type mappings that can define primary keys
 */
public interface PrimaryKeyTextRangeResolver extends TypeMappingTextRangeResolver
{
	
	TextRange getIdClassTextRange();
	
	TextRange getAttributeMappingTextRange(String attributeName);
}
