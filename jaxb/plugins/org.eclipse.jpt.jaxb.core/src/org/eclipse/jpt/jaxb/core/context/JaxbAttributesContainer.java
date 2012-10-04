/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;

/**
 * Holds the attributes represented by a particular JavaResourceType and XmlAccessType.
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
public interface JaxbAttributesContainer
		extends JaxbContextNode {

	/**
	 * Return true if this JaxbAtributesContainer contains attributes for the given JavaResourceType.
	 */
	boolean isFor(JavaResourceType javaResourceType);


	/********** attributes **********/

	Iterable<JaxbPersistentAttribute> getAttributes();
	
	int getAttributesSize();
}
