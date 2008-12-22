/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;


/**
 * Corresponds to the javax.persistence.NamedNativeQuery annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface NamedNativeQueryAnnotation extends QueryAnnotation
{
	String ANNOTATION_NAME = JPA.NAMED_NATIVE_QUERY;

	/**
	 * Corresponds to the resultClass element of the NamedNativeQuery annotation.
	 * Returns null if the resultClass element does not exist in java.
	 */
	String getResultClass();
	
	/**
	 * Corresponds to the resultClass element of the NamedNativeQuery annotation.
	 * Set to null to remove the resultClass element.
	 */
	void setResultClass(String resultClass);
		String RESULT_CLASS_PROPERTY = "resultClass"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the resultSetMapping element of the NamedNativeQuery annotation.
	 * Returns null if the resultSetMapping element does not exist in java.
	 */
	String getResultSetMapping();

	/**
	 * Corresponds to the resultSetMapping element of the NamedNativeQuery annotation.
	 * Set to null to remove the resultSetMapping element.
	 */
	void setResultSetMapping(String resultSetMapping);
		String RESULT_SET_MAPPING_PROPERTY = "resultSetMapping"; //$NON-NLS-1$
	
	String getFullyQualifiedResultClass();
	String FULLY_QUALIFIED_RESULT_CLASS_PROPERTY = "fullyQualifiedResultClass"; //$NON-NLS-1$
	
	/**
	 * Return the {@link TextRange} for the resultClass element. If resultClass element
	 * does not exist return the {@link TextRange} for the NamedNativeQuery annotation.
	 */
	TextRange getResultClassTextRange(CompilationUnit astRoot);

	
	/**
	 * Return the {@link TextRange} for the resultSetMapping element. If resultSetMapping element
	 * does not exist return the {@link TextRange} for the NamedNativeQuery annotation.
	 */
	TextRange getResultSetMappingTextRange(CompilationUnit astRoot);

}
