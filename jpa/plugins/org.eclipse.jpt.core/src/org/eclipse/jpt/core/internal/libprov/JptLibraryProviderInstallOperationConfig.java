/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.libprov;

import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public interface JptLibraryProviderInstallOperationConfig {
	
	ILibraryProvider getLibraryProvider();
	
	IProjectFacetVersion getProjectFacetVersion();
}
