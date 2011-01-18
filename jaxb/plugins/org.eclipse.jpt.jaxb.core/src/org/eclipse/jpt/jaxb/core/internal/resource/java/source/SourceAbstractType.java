/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.HashMap;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.core.utility.jdt.AbstractType;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.SimpleIntReference;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Java source type
 */
abstract class SourceAbstractType<A extends AbstractType>
	extends SourceMember<A>
	implements AbstractJavaResourceType
{
	private String name;

	private String qualifiedName;

	private String packageName;

	private String declaringTypeName;

	private boolean memberType;


	// ********** construction/initialization **********

	protected SourceAbstractType(JavaResourceCompilationUnit javaResourceCompilationUnit, A type) {
		super(javaResourceCompilationUnit, type);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		ITypeBinding binding = this.annotatedElement.getBinding(astRoot);
		this.name = this.buildName(binding);
		this.qualifiedName = this.buildQualifiedName(binding);
		this.packageName = this.buildPackageName(binding);
		this.declaringTypeName = this.buildDeclaringTypeName(binding);
		this.memberType = this.buildMemberType(binding);
	}


	// ********** update **********

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		ITypeBinding binding = this.annotatedElement.getBinding(astRoot);
		this.syncName(this.buildName(binding));
		this.syncQualifiedName(this.buildQualifiedName(binding));
		this.syncPackageName(this.buildPackageName(binding));
		this.syncDeclaringTypeName(this.buildDeclaringTypeName(binding));
		this.syncMemberType(this.buildMemberType(binding));
	}


	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ******** JavaResourceAbstractType implementation ********

	// ***** name
	public String getName() {
		return this.name;
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(ITypeBinding binding) {
		return (binding == null) ? null : binding.getName();
	}

	// ***** qualified name
	public String getQualifiedName() {
		return this.qualifiedName;
	}

	private void syncQualifiedName(String astQualifiedName) {
		String old = this.qualifiedName;
		this.qualifiedName = astQualifiedName;
		this.firePropertyChanged(QUALIFIED_NAME_PROPERTY, old, astQualifiedName);
	}

	private String buildQualifiedName(ITypeBinding binding) {
		return (binding == null) ? null : binding.getQualifiedName();
	}

	// ***** package name
	public String getPackageName() {
		return this.packageName;
	}

	private void syncPackageName(String astPackageName) {
		String old = this.packageName;
		this.packageName = astPackageName;
		this.firePropertyChanged(PACKAGE_NAME_PROPERTY, old, astPackageName);
	}

	private String buildPackageName(ITypeBinding binding) {
		return (binding == null) ? null : binding.getPackage().getName();
	}

	// ***** package
	public boolean isIn(IPackageFragment packageFragment) {
		return StringTools.stringsAreEqual(packageFragment.getElementName(), this.packageName);
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

	// ***** member type
	public boolean isMemberType() {
		return this.memberType;
	}

	private void syncMemberType(boolean memberType) {
		boolean old = this.memberType;
		this.memberType = memberType;
		this.firePropertyChanged(MEMBER_TYPE_PROPERTY, old, memberType);
	}

	private boolean buildMemberType(ITypeBinding binding) {
		return (binding == null) ? false : binding.isMember();
	}

	public boolean isMapped() {
		return ! CollectionTools.isEmpty(getAnnotations());
	}


	// ********** CounterMap **********

	protected static class CounterMap {
		private final HashMap<Object, SimpleIntReference> counters;

		protected CounterMap(int initialCapacity) {
			super();
			this.counters = new HashMap<Object, SimpleIntReference>(initialCapacity);
		}

		/**
		 * Return the incremented count for the specified object.
		 */
		int increment(Object o) {
			SimpleIntReference counter = this.counters.get(o);
			if (counter == null) {
				counter = new SimpleIntReference();
				this.counters.put(o, counter);
			}
			counter.increment();
			return counter.getValue();
		}
	}

}
