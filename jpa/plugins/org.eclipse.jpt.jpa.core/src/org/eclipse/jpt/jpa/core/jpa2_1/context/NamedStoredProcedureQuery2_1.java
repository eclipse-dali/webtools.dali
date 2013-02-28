/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Query;

/**
 * named stored procedure query
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
public interface NamedStoredProcedureQuery2_1
	extends Query
{

	// ********** procedure name **********

	String getProcedureName();
		String PROCEDURE_NAME_PROPERTY = "procedureName"; //$NON-NLS-1$

	void setProcedureName(String procedureName);


	// ********** parameters **********

	/**
	 * Return the query's parameters.
	 */
	ListIterable<? extends StoredProcedureParameter2_1> getParameters();
		String PARAMETERS_LIST = "parameters"; //$NON-NLS-1$

	/**
	 * Return the number of parameters in the query.
	 */
	int getParametersSize();

	/**
	 * Add a parameter to the query and return the object representing it.
	 */
	StoredProcedureParameter2_1 addParameter();

	/**
	 * Add a parameter to the index and return the object representing it.
	 */
	StoredProcedureParameter2_1 addParameter(int index);

	/**
	 * Remove the parameter from the query.
	 */
	void removeParameter(StoredProcedureParameter2_1 parameter);

	/**
	 * Remove the parameter at the index from the query.
	 */
	void removeParameter(int index);

	/**
	 * Move the hint from the source index to the target index.
	 */
	void moveParameter(int targetIndex, int sourceIndex);

	/**
	 * Return the parameter at the index.
	 */
	StoredProcedureParameter2_1 getParameter(int index);

	// ********** result classes **********

	/**
	 * Return the query's result classes.
	 */
	ListIterable<String> getResultClasses();
		String RESULT_CLASSES_LIST = "resultClasses"; //$NON-NLS-1$

	/**
	 * Return the number of result classes in the query.
	 */
	int getResultClassesSize();

	/**
	 * Return the result class at the index.
	 */
	String getResultClass(int index);

	/**
	 * Add the result class to the query.
	 */
	void addResultClass(String resultClass);

	/**
	 * Add the result class to  the index.
	 */
	void addResultClass(int index, String resultClass);

	/**
	 * Remove the result class from the query's list of result classes.
	 */
	void removeResultClass(String resultClass);

	/**
	 * Remove the result class at the index.
	 */
	void removeResultClass(int index);		
	
	/**
	 * Move the result class from the source index to the target index.
	 */
	void moveResultClass(int targetIndex, int sourceIndex);


	// ********** result set mappings **********

	/**
	 * Return the query's result set mappings.
	 */
	ListIterable<String> getResultSetMappings();
		String RESULT_SET_MAPPINGS_LIST = "resultSetMappings"; //$NON-NLS-1$

	/**
	 * Return the number of result set mappings in the query.
	 */
	int getResultSetMappingsSize();

	/**
	 * Return the result class at the index.
	 */
	String getResultSetMapping(int index);

	/**
	 * Add the result set mapping to the query.
	 */
	void addResultSetMapping(String resultClass);

	/**
	 * Add the result set mapping to the index.
	 */
	void addResultSetMapping(int index, String resultClass);

	/**
	 * Remove the result set mapping from the query's list of result set mappings.
	 */
	void removeResultSetMapping(String resultClass);

	/**
	 * Remove the result set mapping at the index.
	 */
	void removeResultSetMapping(int index);

	/**
	 * Move the result set mapping from the source index to the target index.
	 */
	void moveResultSetMapping(int targetIndex, int sourceIndex);
}
