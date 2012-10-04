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

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Maintains a list of classes (class names here) to be added to the JAXB context.
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

public interface XmlSeeAlso
		extends JaxbContextNode {
	
	// **************** value *************************************************
	
	public static final String CLASSES_LIST = "classes";
	
	ListIterable<String> getClasses();
	
	int getClassesSize();
	
	void addClass(int index, String clazz);
	
	void removeClass(int index);
	
	void moveClass(int targetIndex, int sourceIndex);
	
	
	// **************** misc **************************************************
	
	/**
	 * Return all directly referenced types, fully qualified.
	 * (Used for constructing Jaxb context)
	 */
	Iterable<String> getReferencedXmlTypeNames();
}
