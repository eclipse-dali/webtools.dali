/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlSeeAlso
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
public interface XmlSeeAlsoAnnotation
		extends Annotation {
	
	String ANNOTATION_NAME = JAXB.XML_SEE_ALSO;
	
	/**
	 * Change notification constant associated with the value element
	 */
	String CLASSES_LIST = "classes"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the XmlSeeAlso annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;XmlSeeAlso({Foo.class, Bar.class})
	 * </pre>
	 * will return and iterable containing: "Foo", "Bar"
	 */
	ListIterable<String> getClasses();
	
	/**
	 * Corresponds to the 'value' element of the XmlSeeAlso annotation.
	 */
	int getClassesSize();
	
	/**
	 * Corresponds to the 'value' element of the XmlSeeAlso annotation.
	 */
	void addClass(String clazz);
	
	/**
	 * Corresponds to the 'value' element of the XmlSeeAlso annotation.
	 */
	void addClass(int index, String clazz);
	
	/**
	 * Corresponds to the 'value' element of the XmlSeeAlso annotation.
	 */
	void moveClass(int targetIndex, int sourceIndex);
	
	/**
	 * Corresponds to the 'value' element of the XmlSeeAlso annotation.
	 */
	void removeClass(int index);
	
	/**
	 * Change notification constant associated with the fully qualified classes.
	 * Changes should occur whenever changes occur to the "classes" list, but 
	 * may also occur with changes to the classes resolution state (import changes, e.g.)
	 */
	String FULLY_QUALIFIED_CLASSES_LIST = "fullyQualifiedClasses"; //$NON-NLS-1$
	
	/**
	 * Return the value elements resolved to fully qualified class names
	 */
	ListIterable<String> getFullyQualifiedClasses();
}
