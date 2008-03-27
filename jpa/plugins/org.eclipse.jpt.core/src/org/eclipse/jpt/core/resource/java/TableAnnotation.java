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

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TableAnnotation extends JavaResourceNode
{
	String ANNOTATION_NAME = JPA.TABLE;

	String getName();
	void setName(String name);
		String NAME_PROPERTY = "nameProperty";
		
	String getCatalog();
	void setCatalog(String catalog);
		String CATALOG_PROPERTY = "catalogProperty";

	String getSchema();
	void setSchema(String schema);
		String SCHEMA_PROPERTY = "schemaProperty";
	
	ListIterator<UniqueConstraintAnnotation> uniqueConstraints();
	
	UniqueConstraintAnnotation uniqueConstraintAt(int index);
	
	int indexOfUniqueConstraint(UniqueConstraintAnnotation uniqueConstraint);
	
	int uniqueConstraintsSize();

	UniqueConstraintAnnotation addUniqueConstraint(int index);
	
	void removeUniqueConstraint(int index);
	
	void moveUniqueConstraint(int targetIndex, int sourceIndex);
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraintsList";

	/**
	 * Return the {@link TextRange} for the name element.  If the name element 
	 * does not exist return the {@link TextRange} for the *Table annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the catalog element.  If the catalog element 
	 * does not exist return the {@link TextRange} for the *Table annotation.
	 */
	TextRange getCatalogTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the schema element.  If the schema element 
	 * does not exist return the {@link TextRange} for the *Table annotation.
	 */
	TextRange getSchemaTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified postition touches the name element.
	 * Return false if the name element does not exist.
	 */
	boolean nameTouches(int pos, CompilationUnit astRoot);
	
	/**
	 * Return whether the specified postition touches the schema element.
	 * Return false if the name element does not exist.
	 */
	boolean schemaTouches(int pos, CompilationUnit astRoot);
	
	/**
	 * Return whether the specified postition touches the catalog element.
	 * Return false if the name element does not exist.
	 */
	boolean catalogTouches(int pos, CompilationUnit astRoot);

}
