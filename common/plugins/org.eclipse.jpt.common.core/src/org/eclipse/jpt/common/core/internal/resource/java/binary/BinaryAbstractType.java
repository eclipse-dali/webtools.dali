/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * binary persistent type
 */
abstract class BinaryAbstractType
	extends BinaryMember
	implements JavaResourceAbstractType
{
	private String name;

	private String qualifiedName;

	private String packageName;

	private String declaringTypeName;

	private boolean memberType;


	// ********** construction/initialization **********

	protected BinaryAbstractType(JavaResourceNode parent, IType type) {
		super(parent, new TypeAdapter(type));
		this.name = this.buildName();
		this.qualifiedName = this.buildQualifiedName();
		this.packageName = this.buildPackageName();
		this.declaringTypeName = this.buildDeclaringTypeName();
		this.memberType = this.buildMemberType();
	}


	// ********** overrides **********

	@Override
	public void update() {
		super.update();
		this.setName(this.buildName());
		this.setQualifiedName(this.buildQualifiedName());
		this.setPackageName(this.buildPackageName());
		this.setDeclaringTypeName(this.buildDeclaringTypeName());
		this.setMemberType(this.buildMemberType());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

	
	// ********** JavaResourceAbstractType implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	private void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return this.getMember().getElementName();
	}

	// ***** qualified name
	public String getQualifiedName() {
		return this.qualifiedName;
	}

	private void setQualifiedName(String qualifiedName) {
		String old = this.qualifiedName;
		this.qualifiedName = qualifiedName;
		this.firePropertyChanged(QUALIFIED_NAME_PROPERTY, old, qualifiedName);
	}

	private String buildQualifiedName() {
		return this.getMember().getFullyQualifiedName('.');  // no parameters are included here
	}

	// ***** package
	public String getPackageName() {
		return this.packageName;
	}

	private void setPackageName(String packageName) {
		String old = this.packageName;
		this.packageName = packageName;
		this.firePropertyChanged(PACKAGE_NAME_PROPERTY, old, packageName);
	}

	private String buildPackageName() {
		return this.getMember().getPackageFragment().getElementName();
	}

	public boolean isIn(IPackageFragment packageFragment) {
		return StringTools.stringsAreEqual(packageFragment.getElementName(), this.packageName);
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

	private String buildDeclaringTypeName() {
		IType declaringType = this.getMember().getDeclaringType();
		return (declaringType == null) ? null : declaringType.getFullyQualifiedName('.');  // no parameters are included here
	}


	// ***** member
	public boolean isMemberType() {
		return this.memberType;
	}

	private void setMemberType(boolean memberType) {
		boolean old = this.memberType;
		this.memberType = memberType;
		this.firePropertyChanged(MEMBER_TYPE_PROPERTY, old, memberType);
	}

	private boolean buildMemberType() {
		try {
			return this.getMember().isMember();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
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

	static class TypeAdapter implements Adapter {
		private final IType type;

		TypeAdapter(IType type) {
			super();
			this.type = type;
		}

		public IType getElement() {
			return this.type;
		}

		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.type.getAnnotations();
		}

	}
}
