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
		extends JaxbPersistentType, JaxbClass, XmlAdaptable {


	/********** attributes **********/

	Iterable<JaxbPersistentAttribute> getAttributes();
	int getAttributesSize();
		String ATTRIBUTES_COLLECTION = "attributes"; //$NON-NLS-1$


	/********** inherited attributes **********/

	/**
	 * Inherited attributes come from any superclasses that are mapped as @XmlTransient.
	 * @see JaxbClass#getSuperClass()
	 * @see JaxbClass#getInheritanceHierarchy()
	 */
	Iterable<JaxbPersistentAttribute> getInheritedAttributes();
	int getInheritedAttributesSize();
		String INHERITED_ATTRIBUTES_COLLECTION = "inheritedAttributes"; //$NON-NLS-1$

	
	/**
	 * Return true if the given attribute is one of the inherited attributes.
	 */
	boolean isInherited(JaxbPersistentAttribute attribute);

	/**
	 * Only ask this of inherited persistent attributes. Returns the simple
	 * type name of the attribute's resource type.
	 * 
	 * @see JaxbPersistentAttribute#isInherited()
	 */
	String getJavaResourceAttributeOwningTypeName(JaxbPersistentAttribute attribute);

	/**
	 * Return true if 1 or more attributes include the @XmlId annotation
	 */
	boolean containsXmlId();


}
