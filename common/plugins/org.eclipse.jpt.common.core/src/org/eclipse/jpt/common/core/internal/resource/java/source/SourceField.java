/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
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
			FieldDeclaration fieldDeclaration,
			VariableDeclarationFragment variableDeclaration) {
		
		FieldAttribute field = new JDTFieldAttribute(
				declaringType,
				name,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		SourceField sf = new SourceField(parent, field);
		sf.initialize(fieldDeclaration, variableDeclaration);
		return sf;
	}
	
	
	private SourceField(JavaResourceType parent, FieldAttribute field){
		super(parent, field);
	}
	
	
	// call initialize(FieldDeclaration, VariableDeclarationFragment) now for performance
	// trying to minimize API changes, this should be removed from the interface
	// TODO other members of this hierarchy should have similar initialize methods
	@Override
	public void initialize(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	protected void initialize(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration) {
		super.initialize(fieldDeclaration);
		initialize(variableDeclaration.resolveBinding());
	}
	
	// call synchronizeWith(FieldDeclaration, VariableDeclarationFragment) now for performance
	// trying to minimize API changes, this should be removed from the interface
	// TODO other members of this hierarchy should have similar initialize methods
	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	public void synchronizeWith(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration) {
		super.synchronizeWith(fieldDeclaration);
		synchronizeWith(variableDeclaration.resolveBinding());
	}
	
	@Override
	protected ITypeBinding getJdtTypeBinding(IBinding binding) {
		return ((IVariableBinding) binding).getType();
	}
	
	
	// ******** JavaResourceAnnotatedElement implementation ********
	
	public Kind getKind() {
		return Kind.FIELD;
	}
}
