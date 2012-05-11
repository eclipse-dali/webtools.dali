/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.0
 */
public interface JavaTableGenerator
	extends JavaGenerator, TableGenerator
{
	void initialize(TableGeneratorAnnotation resourceGenerator);
	
	/**
	 * Update the JavaTableGenerator context model object to match the TableGeneratorAnnotation 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(TableGeneratorAnnotation resourceGenerator);
	
	
	//****************** covariant overrides *******************

	@SuppressWarnings("unchecked")
	ListIterator<JavaUniqueConstraint> uniqueConstraints();
	
	JavaUniqueConstraint addUniqueConstraint(int index);

}