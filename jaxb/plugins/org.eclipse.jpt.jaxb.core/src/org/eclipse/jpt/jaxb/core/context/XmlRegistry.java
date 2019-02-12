/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.context.java.JavaClass;



/**
 * Represents a JAXB registry
 * (A class with an explicit @XmlRegistry annotation)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface XmlRegistry
		extends JaxbContextNode {
	
	// ***** element factory methods ******
	
	String ELEMENT_FACTORY_METHODS_COLLECTION = "elementFactoryMethods"; //$NON-NLS-1$
	
	Iterable<JaxbElementFactoryMethod> getElementFactoryMethods();
	
	int getElementFactoryMethodsSize();
	
	
	// ***** misc *****
	
	JavaClass getJaxbClass();
}
