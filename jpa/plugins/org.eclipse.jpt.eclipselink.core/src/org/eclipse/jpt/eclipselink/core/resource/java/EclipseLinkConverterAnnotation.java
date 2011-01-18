/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
 * Corresponds to the EclipseLink annotation
 * org.eclipse.persistence.annotations.Converter
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.1
 */
public interface EclipseLinkConverterAnnotation
	extends EclipseLinkNamedConverterAnnotation
{
	String ANNOTATION_NAME = EclipseLink.CONVERTER;
		
	/**
	 * Corresponds to the 'converterClass' element of the Converter annotation.
	 * Returns null if the element does not exist in java.
	 */
	String getConverterClass();
		String CONVERTER_CLASS_PROPERTY = "converterClass"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'converterClass' element of the Converter annotation.
	 * Set to null to remove the element.
	 */
	void setConverterClass(String value);

	/**
	 * Return the fully-qualified converter class name as resolved by the
	 * AST's bindings.
	 * <pre>
	 *     &#64;Converter(GenderConverter.class)
	 * </pre>
	 * will return <code>"model.GenderConverter"</code> if there is an import for
	 * <code>model.GenderConverter</code>.
	 */
	String getFullyQualifiedConverterClassName();
		
	/**
	 * Return the {@link TextRange} for the 'converterClass' element. If the element 
	 * does not exist return the {@link TextRange} for the Converter annotation.
	 */
	TextRange getConverterClassTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the converter class implements the specified interface.
	 * @see org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter#ECLIPSELINK_CONVERTER_CLASS_NAME
	 */
	boolean converterClassImplementsInterface(String interfaceName, CompilationUnit astRoot);

}
