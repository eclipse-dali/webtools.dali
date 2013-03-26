/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * binary type
 */
abstract class BinaryAbstractType
	extends BinaryMember
	implements JavaResourceAbstractType
{
	private JavaResourceTypeBinding typeBinding;
	
	private String declaringTypeName;
	
	
	// ********** construction/initialization **********
	
	protected BinaryAbstractType(JavaResourceModel parent, TypeAdapter adapter) {
		super(parent, adapter);
		this.typeBinding = buildTypeBinding(adapter.getTypeBinding());
		this.declaringTypeName = buildDeclaringTypeName();
	}
	
	
	// ********** overrides **********
	
	@Override
	protected IType getElement() {
		return (IType) super.getElement();
	}
	
	@Override
	public void update() {
		super.update();
		
		updateTypeBinding();
		updateDeclaringTypeName();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.typeBinding.getSimpleName());
	}
	
	
	// ********** JavaResourceAbstractType implementation **********
	
	// ***** name
	public String getName() {
		return this.typeBinding.getSimpleName();
	}
	
	// ***** type binding
	public JavaResourceTypeBinding getTypeBinding() {
		return this.typeBinding;
	}
	
	protected JavaResourceTypeBinding buildTypeBinding(ITypeBinding jdtTypeBinding) {
		return new JavaResourceTypeBinding(jdtTypeBinding);
	}
	
	protected void updateTypeBinding() {
		throw new UnsupportedOperationException();
	}
	
	// ***** package
	public boolean isIn(IPackageFragment packageFragment) {
		return ObjectTools.equals(packageFragment.getElementName(), this.typeBinding.getPackageName());
	}
	
	// ***** source folder
	public boolean isIn(IPackageFragmentRoot sourceFolder) {
		return getSourceFolder().equals(sourceFolder);
	}
	
	private IPackageFragmentRoot getSourceFolder() {
		return (IPackageFragmentRoot) getElement().getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
	}
	
	// ***** declaring type name
	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}
	
	private String buildDeclaringTypeName() {
		IType declaringType = getElement().getDeclaringType();
		return (declaringType == null) ? null : declaringType.getFullyQualifiedName('.');  // no parameters are included here
	}
	
	protected void updateDeclaringTypeName() {
		throw new UnsupportedOperationException();
	}
	
	
	// ********** misc **********
	
	public Iterable<JavaResourceType> getTypes() {
		throw new UnsupportedOperationException();
	}
	
	public Iterable<JavaResourceEnum> getEnums() {
		throw new UnsupportedOperationException();
	}
	
	public Iterable<JavaResourceType> getAllTypes() {
		throw new UnsupportedOperationException();
	}
	
	public Iterable<JavaResourceEnum> getAllEnums() {
		throw new UnsupportedOperationException();
	}
	
	
	// ********** IType adapter **********
	
	static class TypeAdapter
		implements MemberAdapter
	{
		private final IType type;
		/* cached, but only during initialization */
		private final ITypeBinding typeBinding;
		
		TypeAdapter(IType type) {
			super();
			this.type = type;
			this.typeBinding = this.buildTypeBinding();
		}
		
		
		protected ITypeBinding buildTypeBinding() {
			return (ITypeBinding) ASTTools.createBinding(this.type);
		}
		
		public IType getElement() {
			return this.type;
		}
		
		public ITypeBinding getTypeBinding() {
			return this.typeBinding;
		}
		
		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.type.getAnnotations();
		}
	}
}
