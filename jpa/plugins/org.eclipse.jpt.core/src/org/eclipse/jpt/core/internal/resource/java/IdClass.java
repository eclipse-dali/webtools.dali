/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;

/**
 * This interface corresponds to the javax.persistence.IdClass annotation
 */
public interface IdClass extends JavaResource
{
	final String ANNOTATION_NAME = JPA.ID_CLASS;

	/**
	 * Corresponds to the value element of the IdClass annotation.
	 * Returns null if the value element does not exist in java.
	 * Returns the portion of the value preceding the .class.
	 * <p>
	 *     &#64;IdClass(value=Employee.class)
	 * </p>
	 * will return "Employee"
	 **/
	String getValue();	
	
	/**
	 * Corresponds to the value element of the IdClass annotation.
	 * Set to null to remove the value element.  This will also remove the IdClass 
	 * annotation itself.
	 */
	void setValue(String value);
		String VALUE_PROPERTY = "valueProperty";
		
	/**
	 * Returns the qualified value name as it is resolved in the AST
	 * <p>
	 *     &#64;IdClass(Employee.class)
	 * </p>
	 * will return "model.Employee" if there is an import for model.Employee
	 * @return
	 */
	String getFullyQualifiedClass();
		String FULLY_QUALIFIED_CLASS_PROPERTY = "fullyQualifiedClassProperty";

	/**
	 * Return the ITextRange for the value element.  If the value element 
	 * does not exist return the ITextRange for the IdClass annotation.
	 */
	ITextRange valueTextRange(CompilationUnit astRoot);

}
