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

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryProviderInstallOperation;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryProviderInstallOperationConfig;


public class TempWtpUserLibraryProviderInstallOperation 
		extends UserLibraryProviderInstallOperation {
	
    @Override
    protected IClasspathEntry createClasspathEntry( final UserLibraryProviderInstallOperationConfig config,
                                                    final String libraryName )
    {
        final TempWtpUserLibraryProviderInstallOperationConfig cfg
            = (TempWtpUserLibraryProviderInstallOperationConfig) config;
        
        IClasspathEntry cpe = super.createClasspathEntry( cfg, libraryName );
        cpe = JavaCore.newContainerEntry( cpe.getPath(), null, cfg.getClasspathAttributes(), false );
        
        return cpe;
    }
}
