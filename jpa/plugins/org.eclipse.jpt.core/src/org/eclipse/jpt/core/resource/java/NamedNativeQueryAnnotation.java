/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
 * Corresponds to the JPA annotation
 * <code>javax.persistence.NamedNativeQuery</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface NamedNativeQueryAnnotation
	extends QueryAnnotation
{
	String ANNOTATION_NAME = JPA.NAMED_NATIVE_QUERY;


	// ********** result class **********

	/**
	 * Corresponds to the 'resultClass' element of the NamedNativeQuery annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getResultClass();
		String RESULT_CLASS_PROPERTY = "resultClass"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'resultClass' element of the NamedNativeQuery annotation.
	 * Set to null to remove the element.
	 */
	void setResultClass(String resultClass);

	/**
	 * Return the {@link TextRange} for the 'resultClass' element. If element
	 * does not exist return the {@link TextRange} for the NamedNativeQuery annotation.
	 */
	TextRange getResultClassTextRange(CompilationUnit astRoot);

	/**
	 * Return the named native query's fully-qualified result class name as
	 * resolved by the AST's bindings.
	 * <pre>
	 *     &#64;NamedNativeQuery(resultClass=Employee.class)
	 * </pre>
	 * will return <code>"model.Employee"</code> if there is an import for
	 * <code>model.Employee</code>.
	 */
	String getFullyQualifiedResultClassName();


	// ********** result set mapping **********

	/**
	 * Corresponds to the 'resultSetMapping' element of the NamedNativeQuery annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getResultSetMapping();
		String RESULT_SET_MAPPING_PROPERTY = "resultSetMapping"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'resultSetMapping' element of the NamedNativeQuery annotation.
	 * Set to null to remove the element.
	 */
	void setResultSetMapping(String resultSetMapping);

	/**
	 * Return the {@link TextRange} for the 'resultSetMapping' element. If element
	 * does not exist return the {@link TextRange} for the NamedNativeQuery annotation.
	 */
	TextRange getResultSetMappingTextRange(CompilationUnit astRoot);

}
