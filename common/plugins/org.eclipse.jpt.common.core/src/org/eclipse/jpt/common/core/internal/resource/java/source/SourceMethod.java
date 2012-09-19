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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTMethodAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;

/**
 * Java source method
 */
final class SourceMethod
		extends SourceAttribute<MethodAttribute>
		implements JavaResourceMethod {
	
	boolean constructor;
	
	private final Vector<String> parameterTypeNames = new Vector<String>();
	
	
	/**
	 * construct method
	 */
	static JavaResourceMethod newInstance(
			JavaResourceType parent,
			Type declaringType,
			MethodSignature signature,
			int occurrence,
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			MethodDeclaration methodDeclaration) {
		
		MethodAttribute method = JDTMethodAttribute.newInstance(
				declaringType,
				signature,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceMethod jrm = new SourceMethod(parent, method);
		jrm.initialize(methodDeclaration);
		return jrm;
	}
	
	
	private SourceMethod(JavaResourceType parent, MethodAttribute method){
		super(parent, method);
	}

	//call initialize(MethodDeclaration) now for performance
	//trying to minimize API changes, this should be removed from the interface
	//TODO other members of this hierarchy should have similar initialize methods
	@Override
	public void initialize(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void initialize(IBinding binding) {
		super.initialize(binding);
		this.constructor = this.buildConstructor((IMethodBinding) binding);
		this.parameterTypeNames.addAll(this.buildParameterTypeNames((IMethodBinding) binding));
	}

	public void initialize(MethodDeclaration methodDeclaration) {
		super.initialize(methodDeclaration);
		IMethodBinding binding = methodDeclaration.resolveBinding();
		this.initialize(binding == null ? null : binding.getReturnType());
		this.initialize(binding);
		
	}
	
	
	// ******** JavaResourceAnnotatedElement implementation ********
	
	public Kind getKind() {
		return Kind.METHOD;
	}
	
	
	// ******** overrides ********
	
	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
	}

	//call synchronizeWith(MethodDeclaration) now for performance
	//trying to minimize API changes, this should be removed from the interface
	//TODO other members of this hierarchy should have similar synchronizeWith methods
	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void synchronizeWith(MethodDeclaration methodDeclaration) {
		super.synchronizeWith(methodDeclaration);
		IMethodBinding binding = methodDeclaration.resolveBinding();
		this.synchronizeWith(binding == null ? null : binding.getReturnType());
		this.synchronizeWith(binding);
	}

	@Override
	public void synchronizeWith(IBinding binding) {
		super.synchronizeWith(binding);
		this.syncConstructor(this.buildConstructor((IMethodBinding) binding));
		this.syncParameterTypeNames(this.buildParameterTypeNames((IMethodBinding) binding));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getMethodName());
	}
	
	
	// ***** method name *****
	
	public String getMethodName() {
		return this.annotatedElement.getName();
	}
	
	
	// ***** parameter type names *****
	
	public ListIterable<String> getParameterTypeNames() {
		return new LiveCloneListIterable<String>(this.parameterTypeNames);
	}
	
	public String getParameterTypeName(int index) {
		return this.parameterTypeNames.get(index);
	}
	
	public int getParametersSize() {
		return this.parameterTypeNames.size();
	}
	
	private void syncParameterTypeNames(List<String> astParameterTypeNames) {
		this.synchronizeList(astParameterTypeNames, this.parameterTypeNames, PARAMETER_TYPE_NAMES_LIST);
	}
	
	private List<String> buildParameterTypeNames(IMethodBinding methodBinding) {
		if (methodBinding == null) {
			return Collections.emptyList();
		}
		ArrayList<String> names = new ArrayList<String>();
		for (ITypeBinding parameterType : methodBinding.getParameterTypes()) {
			if (parameterType.isTypeVariable()) {
				// e.g. "E extends Number" has an erasure of "Number"
				parameterType = parameterType.getErasure();
			}
			String ptName = parameterType.getTypeDeclaration().getQualifiedName();
			names.add(ptName);
		}
		return names;
	}
	
	
	// ***** constructor *****
	
	public boolean isConstructor() {
		return this.constructor;
	}
	
	private void syncConstructor(boolean astConstructor) {
		boolean old = this.constructor;
		this.constructor = astConstructor;
		this.firePropertyChanged(CONSTRUCTOR_PROPERTY, old, astConstructor);
	}
	
	private boolean buildConstructor(IMethodBinding methodBinding) {
		return methodBinding == null ? false : methodBinding.isConstructor();
	}
	
	
	// ***** misc *****
	
	public boolean isFor(MethodSignature signature, int occurrence) {
		return this.annotatedElement.matches(signature, occurrence);
	}
}
