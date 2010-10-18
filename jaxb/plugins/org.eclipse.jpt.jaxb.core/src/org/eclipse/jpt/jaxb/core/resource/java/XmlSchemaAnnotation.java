/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlSchema
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface XmlSchemaAnnotation
		extends Annotation {
	
	String ANNOTATION_NAME = JAXB.XML_SCHEMA;
	
	/**
	 * Corresponds to the 'attributeFormDefault' element of the XmlSchema annotation.
	 * Return null if the element does not exist in Java.
	 */
	XmlNsForm getAttributeFormDefault();
		String ATTRIBUTE_FORM_DEFAULT_PROPERTY = "attributeFormDefault"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'attributeFormDefault' element of the XmlSchema annotation.
	 * Set to null to remove the element.
	 */
	void setAttributeFormDefault(XmlNsForm attributeFormDefault);
	
	/**
	 * Return the {@link TextRange} for the 'attributeFormDefault' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlSchema annotation.
	 */
	TextRange getAttributeFormDefaultTextRange(CompilationUnit astRoot);
	
	/**
	 * Corresponds to the 'elementFormDefault' element of the XmlSchema annotation.
	 * Return null if the element does not exist in Java.
	 */
	XmlNsForm getElementFormDefault();
		String ELEMENT_FORM_DEFAULT_PROPERTY = "elementFormDefault"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'elementFormDefault' element of the XmlSchema annotation.
	 * Set to null to remove the element.
	 */
	void setElementFormDefault(XmlNsForm elementFormDefault);
	
	/**
	 * Return the {@link TextRange} for the 'elementFormDefault' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlSchema annotation.
	 */
	TextRange getElementFormDefaultTextRange(CompilationUnit astRoot);
	
	/**
	 * Corresponds to the 'location' element of the XmlSchema annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getLocation();
		String LOCATION_PROPERTY = "location"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'location' element of the XmlSchema annotation.
	 * Set to null to remove the element.
	 */
	void setLocation(String location);
	
	/**
	 * Return the {@link TextRange} for the 'location' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlSchema annotation.
	 */
	TextRange getLocationTextRange(CompilationUnit astRoot);
	
	/**
	 * Corresponds to the 'namespace' element of the XmlSchema annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getNamespace();
		String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'namespace' element of the XmlSchema annotation.
	 * Set to null to remove the element.
	 */
	void setNamespace(String namespace);

	/**
	 * Return the {@link TextRange} for the 'namespace' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlSchema annotation.
	 */
	TextRange getNamespaceTextRange(CompilationUnit astRoot);

	/**
	 * Corresponds to the 'xmlns' element of the XmlSchema annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<XmlNsAnnotation> getXmlns();
		String XMLNS_LIST = "xmlns"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'xmlns' element of the XmlSchema annotation.
	 */
	int getXmlnsSize();
	
	/**
	 * Corresponds to the 'xmlns' element of the XmlSchema annotation.
	 */
	void addXmlns(XmlNsAnnotation xmlns);
	
	/**
	 * Corresponds to the 'xmlns' element of the XmlSchema annotation.
	 */
	void addXmlns(int index, XmlNsAnnotation xmlns);
	
	/**
	 * Corresponds to the 'xmlns' element of the XmlSchema annotation.
	 */
	void moveXmlns(int targetIndex, int sourceIndex);
	
	/**
	 * Corresponds to the 'xmlns' element of the XmlSchema annotation.
	 */
	void removeXmlns(XmlNsAnnotation xmlns);
	
	/**
	 * Corresponds to the 'xmlns' element of the XmlSchema annotation.
	 */
	void removeXmlns(int index);
}
