/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;
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
			EnumConstantDeclaration enumConstantDeclaration) {
		
		EnumConstant enumConstant = new JDTEnumConstant(
				declaringEnum,
				name,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		SourceEnumConstant jrec = new SourceEnumConstant(parent, enumConstant);
		jrec.initialize(enumConstantDeclaration);
		return jrec;
	}
	
	
	private SourceEnumConstant(JavaResourceEnum parent, EnumConstant enumConstant){
		super(parent, enumConstant);
	}
	
	
	protected void initialize(EnumConstantDeclaration enumConstantDeclaration) {
		super.initialize(enumConstantDeclaration, enumConstantDeclaration.getName());
		this.initialize(enumConstantDeclaration.resolveVariable());
	}
	
	protected void initialize(IVariableBinding binding) {
		super.initialize(binding);
	}

	// ******** JavaResourceAnnotatedElement implementation ********
	
	public Kind getKind() {
		return Kind.ENUM_CONSTANT;
	}
	
	
	// ******** overrides ********

	public void resolveTypes(EnumConstantDeclaration enumConstantDeclaration) {
		//no-op
	}

	public void synchronizeWith(EnumConstantDeclaration enumConstantDeclaration) {
		super.synchronizeWith(enumConstantDeclaration, enumConstantDeclaration.getName());
		this.synchronizeWith(enumConstantDeclaration.resolveVariable());
	}

	protected void synchronizeWith(IVariableBinding binding) {
		super.synchronizeWith(binding);
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
