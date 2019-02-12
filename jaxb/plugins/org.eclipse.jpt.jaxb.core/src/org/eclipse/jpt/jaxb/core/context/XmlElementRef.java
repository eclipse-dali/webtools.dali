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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;

/**
 * Represents an @XmlElementRef, whether at top level or nested in @XmlElementRefs
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.1
 */
public interface XmlElementRef
		extends JaxbContextNode {
	
	// ***** annotation *****
	
	XmlElementRefAnnotation getAnnotation();
	
	
	// ***** qname *****
	
	JaxbQName getQName();
	
	
	// ***** required *****
	
	boolean isRequired();
	
	static String SPECIFIED_REQUIRED_PROPERTY = "specifiedRequired"; //$NON-NLS-1$
	
	Boolean getSpecifiedRequired();
	
	void setSpecifiedRequired(Boolean specifiedRequired);
	
	boolean isDefaultRequired();
	
	
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
	Transformer<XmlElementRef, Iterable<String>> REFERENCED_XML_TYPE_NAMES_TRANSFORMER = new ReferencedXmlTypeNamesTransformer();
	class ReferencedXmlTypeNamesTransformer
		extends TransformerAdapter<XmlElementRef, Iterable<String>>
	{
		@Override
		public Iterable<String> transform(XmlElementRef ref) {
			return ref.getReferencedXmlTypeNames();
		}
	}
	
	
	// ***** validation *****
	
	TextRange getTypeTextRange();
}
