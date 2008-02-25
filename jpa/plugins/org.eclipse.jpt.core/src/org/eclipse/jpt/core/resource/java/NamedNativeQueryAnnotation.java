/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;


/**
 * Corresponds to the javax.persistence.NamedNativeQuery annotation
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
		String RESULT_CLASS_PROPERTY = "resultClassProperty";
	
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
		String RESULT_SET_MAPPING_PROPERTY = "resultSetMappingProperty";
	
	String getFullyQualifiedResultClass();
	String FULLY_QUALIFIED_RESULT_CLASS_PROPERTY = "fullyQualifiedResultClassProperty";
	
	/**
	 * Return the ITextRange for the resultClass element. If resultClass element
	 * does not exist return the ITextRange for the NamedNativeQuery annotation.
	 */
	TextRange resultClassTextRange(CompilationUnit astRoot);

	
	/**
	 * Return the ITextRange for the resultSetMapping element. If resultSetMapping element
	 * does not exist return the ITextRange for the NamedNativeQuery annotation.
	 */
	TextRange resultSetMappingTextRange(CompilationUnit astRoot);

}
