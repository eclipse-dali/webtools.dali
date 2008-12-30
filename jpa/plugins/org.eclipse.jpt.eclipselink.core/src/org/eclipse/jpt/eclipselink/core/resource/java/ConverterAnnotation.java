/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Java resource model interface that corresponds to the Eclipselink
 * annotation org.eclipse.persistence.annotations.Converter
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface ConverterAnnotation extends NamedConverterAnnotation
{
	String ANNOTATION_NAME = EclipseLinkJPA.CONVERTER;
		
	/**
	 * Corresponds to the converterClass element of the CustomConverter annotation.
	 * Returns null if the converterClass element does not exist in java.
	 */
	String getConverterClass();
	
	/**
	 * Corresponds to the converterClass element of the CustomConverter annotation.
	 * Set to null to remove the converterClass element.
	 */
	void setConverterClass(String value);
		String CONVERTER_CLASS_PROPERTY = "converterClass"; //$NON-NLS-1$
		
	/**
	 * Return whether the converterClass implements the eclipselink Converter interface
	 * @see org.eclipse.jpt.eclipselink.core.context.CustomConverter#ECLIPSELINK_CONVERTER_CLASS_NAME
	 */
	boolean converterClassImplementsInterface(String interfaceName, CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the converterClass element.  If the converterClass element 
	 * does not exist return the {@link TextRange} for the CustomConverter annotation.
	 */
	TextRange getConverterClassTextRange(CompilationUnit astRoot);
}
