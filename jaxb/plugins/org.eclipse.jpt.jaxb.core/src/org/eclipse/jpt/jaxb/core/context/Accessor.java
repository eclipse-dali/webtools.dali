/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;

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
		extends JavaContextNode {

	JavaResourceAttribute getJavaResourceAttribute();

	boolean isFor(JavaResourceField resourceField);

	boolean isFor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter);

	/**
	 * Return the type name of the java resource attribute
	 * This might not return the same thing as getJavaResourceAttribute().getTypeName().
	 */
	String getJavaResourceAttributeTypeName();

	/**
	 * Return whether the java resource attribute type is an array
	 * This might not return the same thing as getJavaResourceAttribute().typeIsArray().
	 */
	boolean isJavaResourceAttributeTypeArray();
	
	/**
	 * Return whether the java resource attribute type is a subtype of the given type
	 * This might not return the same thing as getJavaResourceAttribute().typeIsSubTypeOf(String).
	 */
	boolean isJavaResourceAttributeTypeSubTypeOf(String typeName);


	//**************** static methods *****************

	final class AccessorTools {
		public static String getTypeName(JavaResourceAttribute attribute) {
			if (attribute.typeIsSubTypeOf(COLLECTION_CLASS_NAME)) {
				if (attribute.getTypeTypeArgumentNamesSize() == 1) {
					return attribute.getTypeTypeArgumentName(0);
				}
				return null;
			}
			return attribute.getTypeName();
		}
	
		public static final String COLLECTION_CLASS_NAME = Collection.class.getName();
	}
}
