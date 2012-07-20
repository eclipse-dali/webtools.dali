/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;

/**
 * binary persistent type
 */
abstract class BinaryAbstractType
		extends BinaryMember
		implements JavaResourceAbstractType {
	
	private JavaResourceTypeBinding typeBinding;
	
	private String declaringTypeName;
	
	
	// ********** construction/initialization **********
	
	protected BinaryAbstractType(JavaResourceNode parent, IType type) {
		super(parent, new TypeAdapter(type));
		this.typeBinding = buildTypeBinding(createJdtTypeBinding(type));
		this.declaringTypeName = this.buildDeclaringTypeName(type);
	}


	// ********** overrides **********

	@Override
	protected void update(IMember member) {
		super.update(member);
		
		// TODO - update type binding?
		
		this.setDeclaringTypeName(this.buildDeclaringTypeName((IType) member));
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
	
	protected ITypeBinding createJdtTypeBinding(IType jdtType) {
		IBinding jdtBinding = ASTTools.createBinding(jdtType);
		return (ITypeBinding) jdtBinding;
	}
	
	protected JavaResourceTypeBinding buildTypeBinding(ITypeBinding jdtTypeBinding) {
		return new JavaResourceTypeBinding(jdtTypeBinding);
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
		return (IPackageFragmentRoot) this.getMember().getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
	}
	
	// ***** declaring type name
	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}
	
	private void setDeclaringTypeName(String declaringTypeName) {
		String old = this.declaringTypeName;
		this.declaringTypeName = declaringTypeName;
		this.firePropertyChanged(DECLARING_TYPE_NAME_PROPERTY, old, declaringTypeName);
	}
	
	private String buildDeclaringTypeName(IType type) {
		IType declaringType = type.getDeclaringType();
		return (declaringType == null) ? null : declaringType.getFullyQualifiedName('.');  // no parameters are included here
	}
	
	
	// ********** misc **********

	@Override
	public IType getMember() {
		return (IType) super.getMember();
	}

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
			implements Adapter {
		
		private final IType type;
		
		TypeAdapter(IType type) {
			super();
			this.type = type;
		}
		
		public IType getElement() {
			return this.type;
		}
		
		public Iterable<ITypeParameter> getTypeParameters() {
			try {
				return new ArrayIterable<ITypeParameter>(this.type.getTypeParameters());
			}
			catch (JavaModelException jme) {
				JptCommonCorePlugin.instance().logError(jme);
			}
			return EmptyIterable.instance();
		}
		
		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.type.getAnnotations();
		}
	}
}
