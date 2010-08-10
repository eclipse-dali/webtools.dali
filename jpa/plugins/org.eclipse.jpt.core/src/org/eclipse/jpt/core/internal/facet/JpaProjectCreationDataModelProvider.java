/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.Set;
import org.eclipse.wst.common.componentcore.datamodel.FacetProjectCreationDataModelProvider;

public abstract class JpaProjectCreationDataModelProvider
		extends FacetProjectCreationDataModelProvider 
		implements JpaProjectCreationDataModelProperties {
	
	
	protected JpaProjectCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set getPropertyNames() {
		Set names = super.getPropertyNames();
		names.add(EAR_PROJECT_NAME);
		names.add(ADD_TO_EAR);
		return names;
	}
}
