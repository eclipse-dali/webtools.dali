/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSeeAlso;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping;

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
public interface OxmTypeMapping
		extends JaxbTypeMapping {
	
	// ***** misc *****
	
	/**
	 * Resource model element
	 */
	EAbstractTypeMapping getETypeMapping();
	
	OxmXmlBindings getXmlBindings();
	
	ELJaxbPackage getJaxbPackage();
	
	
	// ***** type name *****
	
	String TYPE_NAME_PROPERTY = "typeName"; //$NON-NLS-1$
	
	
	// ***** java type *****
	
	String JAVA_TYPE_PROPERTY = "javaType"; //$NON-NLS-1$
	
	JavaType getJavaType();
	
	
	// ***** xml transient *****
	
	String DEFAULT_XML_TRANSIENT_PROPERTY = "defaultXmlTransient";  //$NON-NLS-1$
	
	boolean isDefaultXmlTransient();
	
	String SPECIFIED_XML_TRANSIENT_PROPERTY = "specifiedXmlTransient";  //$NON-NLS-1$
	
	Boolean getSpecifiedXmlTransient();
	
	void setSpecifiedXmlTransient(Boolean newValue);
	
	
	// ***** xml root element *****
	
	String DEFAULT_XML_ROOT_ELEMENT_PROPERTY = "defaultXmlRootElement";  //$NON-NLS-1$
	
	XmlRootElement getDefaultXmlRootElement();
	
	String SPECIFIED_XML_ROOT_ELEMENT_PROPERTY = "specifiedXmlRootElement";  //$NON-NLS-1$
	
	OxmXmlRootElement getSpecifiedXmlRootElement();
	
	public OxmXmlRootElement addSpecifiedXmlRootElement();
	
	void removeSpecifiedXmlRootElement();
	
	
	// ***** xml see also *****
	
	String DEFAULT_XML_SEE_ALSO_PROPERTY = "defaultXmlSeeAlso";  //$NON-NLS-1$
	
	XmlSeeAlso getDefaultXmlSeeAlso();
	
	String SPECIFIED_XML_SEE_ALSO_PROPERTY = "specifiedXmlSeeAlso";  //$NON-NLS-1$
	
	OxmXmlSeeAlso getSpecifiedXmlSeeAlso();
	
	public OxmXmlSeeAlso addSpecifiedXmlSeeAlso();
	
	void removeSpecifiedXmlSeeAlso();
}
