/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;

/**
 * Represents an @XmlElement, whether at top level or nested in @XmlElements
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
public interface XmlElement
		extends JaxbContextNode {
	
	// ***** annotation *****
	
	XmlElementAnnotation getAnnotation(boolean createIfNull);
	
	
	// ***** qname *****
	
	JaxbQName getQName();
	
	
	// ***** nillable *****
	
	boolean isNillable();
	
	String SPECIFIED_NILLABLE_PROPERTY = "specifiedNillable"; //$NON-NLS-1$
	
	Boolean getSpecifiedNillable();
	
	void setSpecifiedNillable(Boolean specifiedNillable);
	
	String DEFAULT_NILLABLE_PROPERTY = "defaultNillable"; //$NON-NLS-1$
	
	boolean isDefaultNillable();
	
	
	// ***** required *****
	
	boolean isRequired();
	
	static String SPECIFIED_REQUIRED_PROPERTY = "specifiedRequired"; //$NON-NLS-1$
	
	Boolean getSpecifiedRequired();
	
	void setSpecifiedRequired(Boolean specifiedRequired);
	
	boolean isDefaultRequired();
	
	
	// ***** default value *****
	
	String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$
	
	String getDefaultValue();
	
	void setDefaultValue(String defaultValue);
	
	
	// ***** type *****
	
	String getType();
	
	String getFullyQualifiedType();
	
	String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$
	
	String getSpecifiedType();
	
	void setSpecifiedType(String type);
	
	String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
	
	String getDefaultType();
	
	
	// ***** misc *****
	
	/**
	 * Return all directly referenced xml types, fully qualified.
	 * (Used for constructing Jaxb context)
	 */
	Iterable<String> getReferencedXmlTypeNames();
	Transformer<XmlElement, Iterable<String>> REFERENCED_XML_TYPE_NAMES_TRANSFORMER = new ReferencedXmlTypeNamesTransformer();
	class ReferencedXmlTypeNamesTransformer
		extends TransformerAdapter<XmlElement, Iterable<String>>
	{
		@Override
		public Iterable<String> transform(XmlElement xmlElement) {
			return xmlElement.getReferencedXmlTypeNames();
		}
	}
	
	/**
	 * Return the schema element declaration referenced, if it can be resolved.
	 */
	XsdElementDeclaration getXsdElement();
	
	
	// ***** validation *****
	
	TextRange getTypeTextRange();
}
