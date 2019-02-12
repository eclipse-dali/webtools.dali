/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context;

import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;

public interface ELJaxbContextRoot
		extends JaxbContextRoot {
	
	// ***** oxm files *****
	
	static String OXM_FILES_COLLECTION = "oxmFiles"; //$NON-NLS-1$
	
	/**
	 * The set of oxm files.
	 */
	Iterable<OxmFile> getOxmFiles();
	
	int getOxmFilesSize();
	
	/**
	 * Return the (first) oxm file with the given package name
	 */
	OxmFile getOxmFile(String packageName);
	
	
	// ***** misc *****
	
	OxmTypeMapping getOxmTypeMapping(String typeName);
}
