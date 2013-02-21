/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;

/**
 * Corresponds to the JPA annotation
 * <code>javax.persistence.NamedStoredProcedureQuery</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface NamedStoredProcedureQuery2_1Annotation
	extends QueryAnnotation
{
	String ANNOTATION_NAME = JPA2_1.NAMED_STORED_PROCEDURE_QUERY;


	// ********** procedure name **********

	/**
	 * Corresponds to the 'procedureName' element of the NamedStoredProcedureQuery annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getProcedureName();
		String PROCEDURE_NAME_PROPERTY = "procedureName"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'procedureName' element of the NamedStoredProcedureQuery annotation.
	 * Set to null to remove the element.
	 */
	void setProcedureName(String procedureName);

	/**
	 * Return the {@link TextRange} for the 'procedureName' element. If element
	 * does not exist return the {@link TextRange} for the NamedStoredProcedureQuery annotation.
	 */
	TextRange getProcedureNameTextRange();


	// ********** parameters **********

	/**
	 * Corresponds to the 'parameters' element of the NamedStoredProcedureQuery annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<StoredProcedureParameter2_1Annotation> getParameters();
		String PARAMETERS_LIST = "parameters"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'parameters' element of the NamedStoredProcedureQuery annotation.
	 */
	int getParametersSize();

	/**
	 * Corresponds to the 'parameters' element of the NamedStoredProcedureQuery annotation.
	 */
	StoredProcedureParameter2_1Annotation parameterAt(int index);

	/**
	 * Corresponds to the 'parameters' element of the NamedStoredProcedureQuery annotation.
	 */
	StoredProcedureParameter2_1Annotation addParameter(int index);

	/**
	 * Corresponds to the 'parameters' element of the NamedStoredProcedureQuery annotation.
	 */
	void moveParameter(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'parameters' element of the NamedStoredProcedureQuery annotation.
	 */
	void removeParameter(int index);


	// ********** result classes **********

	/**
	 * Corresponds to the 'resultClasses' element of the NamedStoredProcedureQuery annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<String> getResultClasses();
		String RESULT_CLASSES_LIST = "resultClasses"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'resultClasses' element of the NamedStoredProcedureQuery annotation.
	 */
	int getResultClassesSize();

	/**
	 * Corresponds to the 'resultClasses' element of the NamedStoredProcedureQuery annotation.
	 */
	String resultClassAt(int index);

	/**
	 * Corresponds to the 'resultClasses' element of the NamedStoredProcedureQuery annotation.
	 */
	void addResultClass(String resultClass);

	/**
	 * Corresponds to the 'resultClasses' element of the NamedStoredProcedureQuery annotation.
	 */
	void moveResultClass(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'resultClasses' element of the NamedStoredProcedureQuery annotation.
	 */
	void removeResultClass(String resultClass);

	/**
	 * Corresponds to the 'resultClasses' element of the NamedStoredProcedureQuery annotation.
	 */
	void removeResultClass(int index);

	
	// ********** result set mappings **********

	/**
	 * Corresponds to the 'resultSetMappings' element of the NamedStoredProcedureQuery annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<String> getResultSetMappings();
		String RESULT_SET_MAPPINGS_LIST = "resultSetMappings"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'resultSetMappings' element of the NamedStoredProcedureQuery annotation.
	 */
	int getResultSetMappingsSize();

	/**
	 * Corresponds to the 'resultSetMappings' element of the NamedStoredProcedureQuery annotation.
	 */
	String resultSetMappingAt(int index);

	/**
	 * Corresponds to the 'resultSetMappings' element of the NamedStoredProcedureQuery annotation.
	 */
	void addResultSetMapping(String resultSetMapping);

	/**
	 * Corresponds to the 'resultSetMappings' element of the NamedStoredProcedureQuery annotation.
	 */
	void moveResultSetMapping(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'resultSetMappings' element of the NamedStoredProcedureQuery annotation.
	 */
	void removeResultSetMapping(String resultSetMapping);

	/**
	 * Corresponds to the 'resultSetMappings' element of the NamedStoredProcedureQuery annotation.
	 */
	void removeResultSetMapping(int index);

}
