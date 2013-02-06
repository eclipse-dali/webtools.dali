/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.resource.java;

import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * JPA 2.1 Java-related stuff (annotations etc.)
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
@SuppressWarnings("nls")
public interface JPA2_1 
{
	// JPA package
	String PACKAGE = JPA.PACKAGE;
	String PACKAGE_ = JPA.PACKAGE_;


	// ********** API **********

	// JPA 2.1 annotations
	String CONVERTER = PACKAGE_ + "Converter";
		String CONVERTER__AUTO_APPLY = "autoApply";
		
	String NAMED_STORED_PROCEDURE_QUERIES = PACKAGE_ + "NamedStoredProcedureQueries";
		String NAMED_STORED_PROCEDURE_QUERIES__VALUE = "value";
	String NAMED_STORED_PROCEDURE_QUERY =PACKAGE_ + "NamedStoredProcedureQuery";
		String NAMED_STORED_PROCEDURE_QUERY__NAME = "name";
		String NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME= "procedureName";
		String NAMED_STORED_PROCEDURE_QUERY__PARAMETERS = "parameters";
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES = "resultClasses";
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS= "resultSetMappings";
		String NAMED_STORED_PROCEDURE_QUERY__HINTS = "hints";
	String NAMED_STORED_PROCEDURE_PARAMETER = PACKAGE_ + "StoredProcedureParameter";
		String NAMED_STORED_PROCEDURE_PARAMETER__NAME = "name";
		String NAMED_STORED_PROCEDURE_PARAMETER__MODE = "mode";
		String NAMED_STORED_PROCEDURE_PARAMETER__TYPE = "type";
		
	// JPA 2.1 enums
	String PARAMETER_MODE = PACKAGE_ + "ParameterMode";
		String PARAMETER_MODE_ = PARAMETER_MODE + '.';
		String PARAMETER_MODE__IN = PARAMETER_MODE_ + "IN";
		String PARAMETER_MODE__INOUT = PARAMETER_MODE_ + "INOUT";
		String PARAMETER_MODE__OUT = PARAMETER_MODE_ + "OUT";
		String PARAMETER_MODE__REF_CURSOR= PARAMETER_MODE_ + "REF_CURSOR";

}
