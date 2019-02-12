/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
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

import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;

/**
 * Represents mapping metadata on a JavaType (specified or implied).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public interface JaxbTypeMapping 
		extends JaxbContextNode {
	
	/**
	 * Return the kind of type represented.
	 * {@link JaxbTypeMapping}s of {@link TypeKind} CLASS may safely be cast to {@link JaxbClassMapping}
	 * {@link JaxbTypeMapping}s of {@link TypeKind} ENUM may safely be cast to {@link JaxbEnumMapping}
	 */
	TypeKind getTypeKind();
	
	/**
	 * Return the type's name object
	 */
	TypeName getTypeName();
	
	JaxbPackage getJaxbPackage();
	
	
	// ***** XmlTransient *****
	
	String XML_TRANSIENT_PROPERTY = "xmlTransient";  //$NON-NLS-1$
	
	boolean isXmlTransient();
	
	
	// ***** XmlType.name and XmlType.namespace *****
	
	JaxbQName getQName();
	
	
	// ***** XmlRootElement *****
	
	String XML_ROOT_ELEMENT_PROPERTY = "xmlRootElement";  //$NON-NLS-1$
	
	XmlRootElement getXmlRootElement();
	
	
	// ***** XmlSeeAlso *****
	
	String XML_SEE_ALSO_PROPERTY = "xmlSeeAlso";  //$NON-NLS-1$
	
	XmlSeeAlso getXmlSeeAlso();
	
	
	// ***** misc *****
	
	/**
	 * Return all directly referenced types, fully qualified.
	 * (Used for constructing Jaxb context)
	 */
	Iterable<String> getReferencedXmlTypeNames();
	
	XsdTypeDefinition getXsdTypeDefinition();
	
	/**
	 * Return true if this class or a subclass (if it can have subclasses) has a root element defined
	 */
	boolean hasRootElementInHierarchy();
}
