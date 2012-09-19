/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context;

import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;

public interface ELJaxbPackage
		extends JaxbPackage {
	
	static final String OXM_FILE_PROPERTY = "oxmFile";  //$NON-NLS-1$
	
	/**
	 * The oxm file associated with this package.
	 * This will be the first oxm file encountered that specifies this package.
	 * May be null.
	 */
	OxmFile getOxmFile();
}
