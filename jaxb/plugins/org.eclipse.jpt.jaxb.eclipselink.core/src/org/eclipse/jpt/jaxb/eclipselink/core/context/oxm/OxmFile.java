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
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface OxmFile 
		extends JaxbContextNode {
	
	OxmFileDefinition getDefinition();
	
	JptXmlResource getOxmResource();
	
	String getPackageName();
	
	
	// ***** package *****
	
	static final String PACKAGE_PROPERTY = "package"; //$NON-NLS-1$
	
	ELJaxbPackage getJaxbPackage();
	
	/** NB: not API.  Used internally only. */
	void setPackage(ELJaxbPackage newPackage);
	
	
	// ***** xml bindings *****
	
	static final String XML_BINDINGS_PROPERTY = "xmlBindings"; //$NON-NLS-1$
	
	OxmXmlBindings getXmlBindings();
}
