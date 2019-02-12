/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;

import org.eclipse.wst.common.frameworks.datamodel.IDataModelProperties;

public interface JaxbFacetDataModelProperties
		extends IDataModelProperties {
	
	static final String PREFIX_ 
			= JaxbFacetDataModelProperties.class.getSimpleName() + "."; //$NON-NLS-1$
	
	public static final String PLATFORM
			= PREFIX_ + "PLATFORM"; //$NON-NLS-1$
	
	public static final String LIBRARY_INSTALL_DELEGATE
			= PREFIX_ + "LIBRARY_INSTALL_DELEGATE"; //$NON-NLS-1$
}
