/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTFieldAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.FieldAttribute;
import org.eclipse.jpt.common.core.utility.jdt.Type;

/**
 * Java source field
 */
final class SourceField
	extends SourceAttribute<FieldAttribute>
	implements JavaResourceField
{

	/**
	 * construct field attribute
	 */
	static JavaResourceField newInstance(
			JavaResourceType parent,
			Type declaringType,
			String name,
			int occurrence,
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			CompilationUnit astRoot) {
		
		FieldAttribute field = new JDTFieldAttribute(
				declaringType,
				name,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceField jrpa = new SourceField(parent, field);
		jrpa.initialize(astRoot);
		return jrpa;
	}
	
	
	private SourceField(JavaResourceType parent, FieldAttribute field){
		super(parent, field);
	}

	// ******** JavaResourceAnnotatedElement implementation ********
	
	public Kind getKind() {
		return Kind.FIELD;
	}
}
