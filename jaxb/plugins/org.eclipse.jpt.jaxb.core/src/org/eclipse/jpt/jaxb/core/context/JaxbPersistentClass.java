/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;

/**
 * Represents a JAXB persistent class.  
 * (A class with either an explicit or implicit @XmlType annotation)
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
public interface JaxbPersistentClass
		extends JaxbPersistentType, XmlAccessTypeHolder, XmlAccessOrderHolder, XmlAdaptable {

	/**
	 * covariant override
	 */
	JavaResourceType getJavaResourceType();

	JaxbPersistentClass getSuperPersistentClass();
		String SUPER_PERSISTENT_CLASS_PROPERTY = "superPersistentClass"; //$NON-NLS-1$

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>including</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterable<JaxbPersistentClass> getInheritanceHierarchy();

	/**
	 * Return the persistent type's "persistence" inheritance hierarchy,
	 * <em>excluding</em> the persistent type itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterable<JaxbPersistentClass> getAncestors();


	/********** attributes **********/

	Iterable<JaxbPersistentAttribute> getAttributes();
	int getAttributesSize();
		String ATTRIBUTES_COLLECTION = "attributes"; //$NON-NLS-1$

}
