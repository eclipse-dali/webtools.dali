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
 * Represents a JAXB attribute (field/property).
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
public interface JaxbPersistentAttribute
		extends JaxbContextNode {
	
	JaxbClassMapping getClassMapping();
	
	
	// ***** inheritance *****
	
	/**
	 * Return true if the source of the attribute is defined in a superclass
	 */
	boolean isInherited();
	
	/**
	 * Return the name of the type where the source of the attribute is declared.
	 * This may not be where the attribute is fully defined, as it may be inherited
	 */
	TypeName getDeclaringTypeName();
	
	
	// ***** name *****
	
	/**
	 * Return the name of the attribute.
	 */
	String getName();
	
	Transformer<JaxbPersistentAttribute, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
			extends TransformerAdapter<JaxbPersistentAttribute, String> {
		@Override
		public String transform(JaxbPersistentAttribute attribute) {
			return attribute.getName();
		}
	}
	
	
	// *****  attribute type information *****
	
	/**
	 * Return the type name of the java attribute, or the item type name of a collection or array.
	 */
	String getJavaResourceAttributeBaseTypeName();
	
	/**
	 * (See JAXB 2.2 Spec, sect. 5.5.2)
	 * Return true if the java attribute type is an extension of java.util.Collection or a single 
	 * dimensional array (except for byte[], which is treated like a non-array)
	 */
	boolean isJavaResourceAttributeCollectionType();
	
	/**
	 * Return whether the java resource attribute type is a subtype of the given type
	 * This might not return the same thing as getJavaResourceAttribute().typeIsSubTypeOf(String).
	 */
	boolean isJavaResourceAttributeTypeSubTypeOf(String typeName);
	
	
	// ********** mapping **********
	
	String MAPPING_PROPERTY = "mapping"; //$NON-NLS-1$
	
	/**
	 * Return the attribute's mapping. This is never <code>null</code>
	 * (although, it may be a <em>null</em> mapping).
	 * Set the mapping via {@link #setMappingKey(String)}.
	 */
	JaxbAttributeMapping getMapping();
	
	Transformer<JaxbPersistentAttribute, JaxbAttributeMapping> MAPPING_TRANSFORMER = new MappingTransformer();
	class MappingTransformer
			extends TransformerAdapter<JaxbPersistentAttribute, JaxbAttributeMapping> {
		@Override
		public JaxbAttributeMapping transform(JaxbPersistentAttribute attribute) {
			return attribute.getMapping();
		}
	}
	
	/**
	 * Return the attribute's mapping key.
	 */
	String getMappingKey();
	
	/**
	 * Set the attribute's mapping.
	 * If the specified key is <code>null</code>, clear the specified mapping,
	 * allowing the attribute's mapping to default (if appropriate).
	 * Return the new mapping (which may be a <em>null</em> mapping).
	 */
	JaxbAttributeMapping setMappingKey(String key);
}
