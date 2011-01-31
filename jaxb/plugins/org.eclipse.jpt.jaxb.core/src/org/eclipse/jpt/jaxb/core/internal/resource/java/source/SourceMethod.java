/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTMethodAttribute;
import org.eclipse.jpt.common.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * Java source method
 */
final class SourceMethod
	extends SourceAttribute<MethodAttribute>
	implements JavaResourceMethod
{
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
			CompilationUnit astRoot) {
		MethodAttribute method = JDTMethodAttribute.newInstance(
				declaringType,
				signature,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceMethod jrm = new SourceMethod(parent, method);
		jrm.initialize(astRoot);
		return jrm;
	}

	private SourceMethod(JavaResourceType parent, MethodAttribute method){
		super(parent, method);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		IMethodBinding binding = this.annotatedElement.getBinding(astRoot);
		this.constructor = this.buildConstructor(binding);
		this.parameterTypeNames.addAll(this.buildParameterTypeNames(binding));
	}


	// ******** overrides ********

	@Override
	protected JavaResourceType getParent() {
		return (JavaResourceType) super.getParent();
	}

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		IMethodBinding binding = this.annotatedElement.getBinding(astRoot);
		this.syncConstructor(this.buildConstructor(binding));
		this.syncParameterTypeNames(this.buildParameterTypeNames(binding));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getMethodName());
	}


	// ******** JavaResourceMethod implementation ********

	public String getMethodName() {
		return this.annotatedElement.getName();
	}

	// ***** constructor
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
	
	public boolean isFor(MethodSignature signature, int occurrence) {
		return this.annotatedElement.matches(signature, occurrence);
	}

	// ***** parameter type names
	public ListIterable<String> getParameterTypeNames() {
		return new LiveCloneListIterable<String>(this.parameterTypeNames);
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
}
