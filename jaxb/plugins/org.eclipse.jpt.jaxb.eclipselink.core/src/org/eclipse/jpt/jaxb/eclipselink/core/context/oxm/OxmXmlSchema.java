/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
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

import org.eclipse.jpt.jaxb.core.context.XmlSchema;

public interface OxmXmlSchema
		extends XmlSchema {
	
	// ***** namespace *****
	
	String DEFAULT_NAMESPACE_PROPERTY = "defaultNamespace"; //$NON-NLS-1$
	
	String getDefaultNamespace();
}
