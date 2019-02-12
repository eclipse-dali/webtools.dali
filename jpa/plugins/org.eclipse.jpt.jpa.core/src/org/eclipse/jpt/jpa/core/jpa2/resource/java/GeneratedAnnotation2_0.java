/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Corresponds to the Java 6 annotation
 * <code>javax.annotation.Generated</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface GeneratedAnnotation2_0
	extends Annotation
{
	String ANNOTATION_NAME = "javax.annotation.Generated";  // requires JRE 1.6 //$NON-NLS-1$
	String VALUE_ELEMENT_NAME = "value"; //$NON-NLS-1$
	String DATE_ELEMENT_NAME = "date"; //$NON-NLS-1$
	String COMMENTS_ELEMENT_NAME = "comments"; //$NON-NLS-1$

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 * Return <code>null</code> if the element does not exist in the annotation.
	 */
	ListIterable<String> getValues();
		String VALUES_LIST = "values"; //$NON-NLS-1$

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 */
	int getValuesSize();

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 */
	String getValue(int index);

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 */
	void addValue(String value);

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 */
	void addValue(int index, String value);

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 */
	void moveValue(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 */
	void removeValue(String value);

	/**
	 * Corresponds to the <code>value<code> element of the <code>Generated</code>
	 * annotation.
	 */
	void removeValue(int index);

	/**
	 * Corresponds to the <code>date</code> element of the <code>Generated</code>
	 * annotation.
	 * Return <code>null</code> if the element does not exist in the annotation.
	 */
	String getDate();
		String DATE_PROPERTY = "date"; //$NON-NLS-1$

	/**
	 * Corresponds to the <code>date</code> element of the <code>Generated</code>
	 * annotation.
	 * Set to <code>null</code> to remove the element.
	 */
	void setDate(String date);

	/**
	 * Corresponds to the <code>comments</code> element of the <code>Generated</code>
	 * annotation.
	 * Return <code>null</code> if the element does not exist in the annotation.
	 */
	String getComments();
		String COMMENTS_PROPERTY = "comments"; //$NON-NLS-1$

	/**
	 * Corresponds to the <code>comments</code> element of the <code>Generated</code>
	 * annotation.
	 * Set to <code>null</code> to remove the element.
	 */
	void setComments(String comments);

}
