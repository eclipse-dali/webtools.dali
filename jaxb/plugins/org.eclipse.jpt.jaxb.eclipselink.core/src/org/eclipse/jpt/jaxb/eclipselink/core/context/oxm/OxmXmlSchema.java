/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.jaxb.core.context.XmlSchema;

public interface OxmXmlSchema
		extends XmlSchema {
	
	// ***** namespace *****
	
	String DEFAULT_NAMESPACE_PROPERTY = "defaultNamespace"; //$NON-NLS-1$
	
	String getDefaultNamespace();
}
