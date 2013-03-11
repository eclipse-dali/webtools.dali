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

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotation
 * <code>javax.persistence.StoredProcedureParameter</code>
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
public interface StoredProcedureParameterAnnotation2_1
	extends NestableAnnotation
{
	String ANNOTATION_NAME = JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER;

	// ********* name **********
	/**
	 * Corresponds to the 'name' element of the StoredProcedureParameter annotation.
	 * Return null if the element does not exist in the annotation
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the StoredProcedureParameter annotation.
	 * Setting to null will remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element 
	 * does not exist return the {@link TextRange} for the StoredProcedureParameter annotation.
	 */
	TextRange getNameTextRange();

	// *********** mode *************

	/**
	 * Corresponds to the 'mode' element of the StoredProcedureParameter annotation.
	 * Return null if the element does not exist in the annotation
	 */
	ParameterMode_2_1 getMode();
		String MODE_PROPERTY = "mode"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'mode' element of the StoredProcedureParameter annotation.
	 * Setting to null will remove the element.
	 */
	void setMode(ParameterMode_2_1 mode);

	/**
	 * Return the {@link TextRange} for the 'mode' element. If the element 
	 * does not exist return the {@link TextRange} for the StoredProcedureParameter annotation.
	 */
	TextRange getModeTextRange();

	// ********** type ***********

	/**
	 * Corresponds to the 'type' element of the StoredProcedureParameter annotation.
	 * Return null if the element does not exist in the annotation
	 */
	String getTypeName();
		String TYPE_PROPERTY = "type"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'type' element of the StoredProcedureParameter annotation.
	 * Setting to null will remove the element.
	 */
	void setTypeName(String type);

	/**
	 * Return the {@link TextRange} for the 'type' element. If the element 
	 * does not exist return the {@link TextRange} for the StoredProcedureParameter annotation.
	 */
	TextRange getTypeTextRange();

	/**
	 * Return the named native query's fully-qualified type name as
	 * resolved by the AST's bindings.
	 * <pre>
	 *     &#64;NamedStoredProcedureQuery(type=Employee.class)
	 * </pre>
	 * will return <code>"model.Employee"</code> if there is an import for
	 * <code>model.Employee</code>.
	 */
	String getFullyQualifiedTypeName();
}
