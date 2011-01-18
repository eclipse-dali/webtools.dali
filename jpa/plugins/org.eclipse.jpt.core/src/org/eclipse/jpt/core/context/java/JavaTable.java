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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Java table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface JavaTable
	extends Table, JavaJpaContextNode
{
	BaseTableAnnotation getTableAnnotation();

	TextRange getNameTextRange(CompilationUnit astRoot);
	TextRange getSchemaTextRange(CompilationUnit astRoot);
	TextRange getCatalogTextRange(CompilationUnit astRoot);

	ListIterator<JavaUniqueConstraint> uniqueConstraints();
	JavaUniqueConstraint getUniqueConstraint(int index);
	JavaUniqueConstraint addUniqueConstraint();
	JavaUniqueConstraint addUniqueConstraint(int index);
}
