/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.Collection;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;

/**
 * Represents a JAXB accessor (field/property).
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
public interface Accessor
		extends JaxbContextNode {

	JavaResourceAttribute getJavaResourceAttribute();

	boolean isFor(JavaResourceField resourceField);

	boolean isFor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter);

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


	//**************** static methods *****************

	final class AccessorTools {
		
		public static final String BYTE_ARRAY_CLASS_NAME = "byte[]";
		public static final String COLLECTION_CLASS_NAME = Collection.class.getName();
		public static final String OBJECT_CLASS_NAME = Object.class.getName();
		
		public static String getBaseTypeName(JavaResourceAttribute attribute) {
			TypeBinding typeBinding = attribute.getTypeBinding();
			if (typeBinding.isArray()) {
				if (BYTE_ARRAY_CLASS_NAME.equals(typeBinding.getQualifiedName())) {
					return BYTE_ARRAY_CLASS_NAME;
				}
				else if (typeBinding.getArrayDimensionality() == 1) {
					return typeBinding.getArrayComponentTypeName();
				}
			}
			else if (typeBinding.isSubTypeOf(COLLECTION_CLASS_NAME)) {
				if (typeBinding.getTypeArgumentNamesSize() == 1) {
					return typeBinding.getTypeArgumentName(0);
				}
				return OBJECT_CLASS_NAME;
			}
			return typeBinding.getQualifiedName();
		}
		
		/**
		 * @see JaxbPersistentAttribute#isJavaResourceAttributeCollectionType()
		 */
		public static boolean isCollectionType(JavaResourceAttribute attribute) {
			TypeBinding typeBinding = attribute.getTypeBinding();
			if (typeBinding.isArray()) {
				if (typeBinding.getArrayDimensionality() == 1
						&& ! BYTE_ARRAY_CLASS_NAME.equals(typeBinding.getQualifiedName())) {
					return true;
				}
				return false;
			}
			else if (typeBinding.isSubTypeOf(COLLECTION_CLASS_NAME)) {
				return true;
			}
			
			return false;
		}
	}
}
