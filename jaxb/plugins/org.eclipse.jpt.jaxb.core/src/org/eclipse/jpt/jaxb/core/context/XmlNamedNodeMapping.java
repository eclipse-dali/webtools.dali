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

import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.xsd.XsdFeature;

/**
 * Common interface for XmlElementMapping and XmlAttributeMapping
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
public interface XmlNamedNodeMapping
		extends XmlNodeMapping {
	
	// ***** XmlID *****
	
	String XML_ID_PROPERTY = "xmlID"; //$NON-NLS-1$
	
	XmlID getXmlID();
	Predicate<XmlNamedNodeMapping> HAS_XML_ID = new HasXmlID();
	class HasXmlID
		extends PredicateAdapter<XmlNamedNodeMapping>
	{
		@Override
		public boolean evaluate(XmlNamedNodeMapping mapping) {
			return mapping.getXmlID() != null;
		}
	}

	
	XmlID addXmlID();
	
	void removeXmlID();
	
	
	// ***** XmlIDREF *****
	
	String XML_IDREF_PROPERTY = "xmlIDREF"; //$NON-NLS-1$
	
	XmlIDREF getXmlIDREF();
	
	XmlIDREF addXmlIDREF();
	
	void removeXmlIDREF();
	
	
	// ***** XmlAttachmentRef *****
	
	String XML_ATTACHMENT_REF_PROPERTY = "xmlAttachmentRef"; //$NON-NLS-1$
	
	XmlAttachmentRef getXmlAttachmentRef();
	
	XmlAttachmentRef addXmlAttachmentRef();
	
	void removeXmlAttachmentRef();
	
	
	// ***** misc *****
	
	XsdFeature getXsdFeature();

	Predicate<JaxbAttributeMapping> IS_NAMED_NODE_MAPPING = new IsNamedNodeMapping();
	class IsNamedNodeMapping
		extends PredicateAdapter<JaxbAttributeMapping>
	{
		@Override
		public boolean evaluate(JaxbAttributeMapping mapping) {
			return (mapping.getKey() == MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY
					|| mapping.getKey() == MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		}
	}
}
