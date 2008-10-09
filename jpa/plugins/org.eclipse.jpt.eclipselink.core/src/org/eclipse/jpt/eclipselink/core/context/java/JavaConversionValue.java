/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.java.ConversionValueAnnotation;

/**
 * Corresponds to a Convert resource model object
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
public interface JavaConversionValue extends ConversionValue, JavaJpaContextNode
{
	
	void initialize(ConversionValueAnnotation resourceConversionValue);

	/**
	 * Update the EclipseLinkJavaConversionValue context model object to match the ConversionValueAnnotation 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(ConversionValueAnnotation resourceConversionValue);
	
	TextRange getDataValueTextRange(CompilationUnit astRoot);

	TextRange getObjectValueTextRange(CompilationUnit astRoot);


}
