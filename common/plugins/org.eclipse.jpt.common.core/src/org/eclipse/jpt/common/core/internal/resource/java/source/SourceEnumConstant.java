/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTEnumConstant;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.utility.jdt.Enum;
import org.eclipse.jpt.common.core.utility.jdt.EnumConstant;

/**
 * Java source enum constant
 */
final class SourceEnumConstant
	extends SourceMember<EnumConstant>
	implements JavaResourceEnumConstant
{

	/**
	 * construct enum constant
	 */
	static JavaResourceEnumConstant newInstance(
			JavaResourceEnum parent,
			Enum declaringEnum,
			String name,
			int occurrence,
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			CompilationUnit astRoot) {
		EnumConstant enumConstant = new JDTEnumConstant(
				declaringEnum,
				name,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceEnumConstant jrec = new SourceEnumConstant(parent, enumConstant);
		jrec.initialize(astRoot);
		return jrec;
	}

	private SourceEnumConstant(JavaResourceEnum parent, EnumConstant enumConstant){
		super(parent, enumConstant);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
	}


	// ******** overrides ********

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
	
	
	// ******** JavaResourceEnumConstant implementation ********
	
	public String getName() {
		return this.annotatedElement.getName();
	}
}
