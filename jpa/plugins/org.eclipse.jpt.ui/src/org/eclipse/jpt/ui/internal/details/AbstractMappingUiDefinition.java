/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the 
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.ui.details.MappingUiDefinition;

public abstract class AbstractMappingUiDefinition<M, T>
	implements MappingUiDefinition<M, T>
{
	protected AbstractMappingUiDefinition() {
		super();
	}
	
	
	public boolean isEnabledFor(M mappableObject) {
		return true;
	}
}
