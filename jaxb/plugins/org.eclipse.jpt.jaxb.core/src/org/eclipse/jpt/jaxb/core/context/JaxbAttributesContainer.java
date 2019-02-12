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

/**
 * Holds the attributes represented by a particular JavaResourceType and XmlAccessType.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface JaxbAttributesContainer<A extends JaxbPersistentAttribute>
		extends JaxbContextNode {
	
	// ***** attributes *****
	
	Iterable<A> getAttributes();
	
	int getAttributesSize();
	
	Transformer<JaxbAttributesContainer, Iterable<? extends JaxbPersistentAttribute>> ATTRIBUTES_TRANSFORMER = new AttributesTransformer();
	class AttributesTransformer
			extends TransformerAdapter<JaxbAttributesContainer, Iterable<? extends JaxbPersistentAttribute>> {
		@Override
		public Iterable<? extends JaxbPersistentAttribute> transform(JaxbAttributesContainer container) {
			return container.getAttributes();
		}
	}
	
	
	interface Context {
		
		/**
		 * Return the access type of the owner, to be used in determining which attributes to build
		 */
		XmlAccessType getAccessType();

		/**
		 * called after an attribute was added to the container
		 */
		void attributeAdded(JaxbPersistentAttribute attribute);
		
		/**
		 * called after an attribute was removed from the container
		 */
		void attributeRemoved(JaxbPersistentAttribute attribute);
	}
}
