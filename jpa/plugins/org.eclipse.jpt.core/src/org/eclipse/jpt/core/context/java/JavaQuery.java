/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * Java named and named native queries
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.0
 * @since 2.0
 */
public interface JavaQuery
	extends Query, JavaJpaContextNode
{
	QueryAnnotation getQueryAnnotation();


	// ********** hints **********

	@SuppressWarnings("unchecked")
	ListIterable<JavaQueryHint> getHints();

	JavaQueryHint addHint();

	JavaQueryHint addHint(int index);


	// ********** validation **********

	TextRange getNameTextRange(CompilationUnit astRoot);
}
