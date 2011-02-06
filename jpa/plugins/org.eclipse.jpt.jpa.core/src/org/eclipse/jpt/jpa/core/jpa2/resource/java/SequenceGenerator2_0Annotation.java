/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;

/**
 * Corresponds to the JPA 2.0 annotation
 * javax.persistence.SequenceGenerator
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface SequenceGenerator2_0Annotation
	extends SequenceGeneratorAnnotation
{
	// ********** catalog **********
	/**
	 * Corresponds to the 'catalog' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getCatalog();
		String CATALOG_PROPERTY = "catalog"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'catalog' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setCatalog(String catalog);

	/**
	 * Return the {@link TextRange} for the 'catalog' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getCatalogTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'catalog' element.
	 * Return false if the element does not exist.
	 */
	boolean catalogTouches(int pos, CompilationUnit astRoot);


	// ********** schema **********

	/**
	 * Corresponds to the 'schema' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getSchema();
		String SCHEMA_PROPERTY = "schema"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'schema' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setSchema(String schema);

	/**
	 * Return the {@link TextRange} for the 'schema' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getSchemaTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'schema' element.
	 * Return false if the element does not exist.
	 */
	boolean schemaTouches(int pos, CompilationUnit astRoot);

}
