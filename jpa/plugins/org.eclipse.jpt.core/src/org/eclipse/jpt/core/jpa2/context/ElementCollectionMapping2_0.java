/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;

public interface ElementCollectionMapping2_0
	extends AttributeMapping2_0, Fetchable
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.LAZY;	
}
