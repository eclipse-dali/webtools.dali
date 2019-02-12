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

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;


/**
 * Represents a JAXB attribute mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.0
 */
public interface JaxbAttributeMapping
		extends JaxbContextNode {
	
	String getKey();
	
	JaxbPersistentAttribute getPersistentAttribute();
	
	JaxbClassMapping getClassMapping();
	
	
	// ***** misc *****
	
	/**
	 * Return the (fully qualified) type of the attribute
	 * (or the item type if in a collection/array)
	 */
	String getBoundTypeName();
	
	/**
	 * Return the (fully qualified) type used to map the bound type. 
	 * (Usually the same as the value type, except when an XmlJavaTypeAdapter is used.)
	 */
	String getValueTypeName();
	
	/**
	 * Return the (fully qualified) type of the data actually written to xml.
	 * (Usually the same as the value type, except when an XmlIDREF is used.)
	 */
	String getDataTypeName();
	
	/**
	 * Return the schema type associated with the value type.  May be null.
	 */
	XsdTypeDefinition getDataTypeXsdTypeDefinition();
	
	/**
	 * Return all directly referenced types, fully qualified.
	 * (Used for constructing Jaxb context)
	 */
	Iterable<String> getReferencedXmlTypeNames();
	Transformer<JaxbAttributeMapping, Iterable<String>> REFERENCED_XML_TYPE_NAMES_TRANSFORMER = new ReferencedXmlTypeNamesTransformer();
	class ReferencedXmlTypeNamesTransformer
		extends TransformerAdapter<JaxbAttributeMapping, Iterable<String>>
	{
		@Override
		public Iterable<String> transform(JaxbAttributeMapping mapping) {
			return mapping.getReferencedXmlTypeNames();
		}
	}
	
	/**
	 * Return whether the attribute is mapped to a particle
	 * (e.g. XmlElement, XmlElements, XmlElementRef, XmlElementRefs)
	 */
	boolean isParticleMapping();
	
	/**
	 * Return whether the attribute is specifically excluded from being mapped
	 * (e.g. XmlTransient)
	 */
	boolean isTransient();
}
