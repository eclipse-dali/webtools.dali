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

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.utility.jdt.AbstractType;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Java source type
 */
abstract class SourceAbstractType<A extends AbstractType>
		extends SourceMember<A>
		implements JavaResourceAbstractType {
	
	private JavaResourceTypeBinding typeBinding; 
	
	private String declaringTypeName;
	
	
	// ********** construction/initialization **********
	
	protected SourceAbstractType(JavaResourceCompilationUnit javaResourceCompilationUnit, A type) {
		super(javaResourceCompilationUnit, type);
	}

	protected void initialize(ITypeBinding binding) {
		super.initialize(binding);
		this.typeBinding = buildTypeBinding(binding);
		this.declaringTypeName = this.buildDeclaringTypeName(binding);
	}

	protected void initialize(AbstractTypeDeclaration typeDeclaration) {
		super.initialize(typeDeclaration, typeDeclaration.getName());
		this.initialize(typeDeclaration.resolveBinding()); 
	}


	// ********** synchronize **********

	protected void synchronizeWith(ITypeBinding binding) {
		super.synchronizeWith(binding);
		this.syncTypeBinding(binding);
		this.syncDeclaringTypeName(this.buildDeclaringTypeName(binding));
	}

	public void synchronizeWith(AbstractTypeDeclaration typeDeclaration) {
		super.synchronizeWith(typeDeclaration, typeDeclaration.getName());
		this.synchronizeWith(typeDeclaration.resolveBinding()); 
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getName());
	}


	// ******** JavaResourceAbstractType implementation ********
	
	// ***** name
	public String getName() {
		return this.typeBinding.getSimpleName();
	}
	
	// ***** type binding
	public TypeBinding getTypeBinding() {
		return this.typeBinding;
	}
	
	private JavaResourceTypeBinding buildTypeBinding(ITypeBinding jdtTypeBinding) {
		return new JavaResourceTypeBinding(jdtTypeBinding);
	}
	
	private void syncTypeBinding(ITypeBinding jdtTypeBinding) {
		if (this.typeBinding.isEquivalentTo(jdtTypeBinding)) {
			return;
		}
		TypeBinding old = this.typeBinding;
		this.typeBinding = buildTypeBinding(jdtTypeBinding);
		firePropertyChanged(TYPE_BINDING_PROPERTY, old, this.typeBinding);
	}
	
	
	// ***** package
	public boolean isIn(IPackageFragment packageFragment) {
		return StringTools.stringsAreEqual(packageFragment.getElementName(), this.typeBinding.getPackageName());
	}

	// ***** source folder
	public boolean isIn(IPackageFragmentRoot sourceFolder) {
		return getSourceFolder().equals(sourceFolder);
	}

	private IPackageFragmentRoot getSourceFolder() {
		return (IPackageFragmentRoot) this.getJavaResourceCompilationUnit().getCompilationUnit().getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
	}

	// ***** declaring type name
	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}

	private void syncDeclaringTypeName(String astDeclaringTypeName) {
		String old = this.declaringTypeName;
		this.declaringTypeName = astDeclaringTypeName;
		this.firePropertyChanged(DECLARING_TYPE_NAME_PROPERTY, old, astDeclaringTypeName);
	}

	private String buildDeclaringTypeName(ITypeBinding binding) {
		if (binding == null) {
			return null;
		}
		ITypeBinding declaringClass = binding.getDeclaringClass();
		return (declaringClass == null) ? null : declaringClass.getTypeDeclaration().getQualifiedName();
	}
}
