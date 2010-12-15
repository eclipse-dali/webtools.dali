/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.jdt.JDTMethodAttribute;
import org.eclipse.jpt.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.core.utility.jdt.Type;
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
		this.parameterTypeNames.addAll(this.buildParameterTypeNames(binding));
	}


	// ******** overrides ********

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		IMethodBinding binding = this.annotatedElement.getBinding(astRoot);

		this.syncParameterTypeNames(this.buildParameterTypeNames(binding));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ******** JavaResourceMethod implementation ********
	
	public boolean isFor(MethodSignature signature, int occurrence) {
		return this.annotatedElement.matches(signature, occurrence);
	}

	public boolean returnTypeIsSubTypeOf(String tn) {
		return this.typeIsSubTypeOf(tn);
	}

	public boolean returnTypeIsVariablePrimitive() {
		return this.typeIsVariablePrimitive();
	}

	public int getReturnTypeModifiers() {
		return this.getModifiers();
	}

	@Override
	protected String getModifiersProperty() {
		return RETURN_TYPE_MODIFIERS_PROPERTY;
	}

	public String getReturnTypeName() {
		return this.getTypeName();
	}

	@Override
	protected String getTypeNameProperty() {
		return RETURN_TYPE_NAME_PROPERTY;
	}

	public boolean returnTypeIsInterface() {
		return this.typeIsInterface();
	}

	@Override
	protected String getTypeIsInterfaceProperty() {
		return RETURN_TYPE_IS_INTERFACE_PROPERTY;
	}

	public boolean returnTypeIsEnum() {
		return this.typeIsEnum();
	}

	@Override
	protected String getTypeIsEnumProperty() {
		return RETURN_TYPE_IS_ENUM_PROPERTY;
	}

	public ListIterable<String> getReturnTypeSuperclassNames() {
		return this.getTypeSuperclassNames();
	}

	@Override
	protected String getTypeSuperclassNamesProperty() {
		return RETURN_TYPE_SUPERCLASS_NAMES_LIST;
	}

	public Iterable<String> getReturnTypeInterfaceNames() {
		return this.getTypeInterfaceNames();
	}

	@Override
	protected String getTypeInterfaceNamesProperty() {
		return RETURN_TYPE_INTERFACE_NAMES_COLLECTION;
	}

	public ListIterable<String> getReturnTypeTypeArgumentNames() {
		return this.getTypeTypeArgumentNames();
	}

	@Override
	protected String getTypeTypeArgumentNamesProperty() {
		return RETURN_TYPE_TYPE_ARGUMENT_NAMES_LIST;
	}

	public int getReturnTypeTypeArgumentNamesSize() {
		return this.getTypeTypeArgumentNamesSize();
	}

	public String getReturnTypeTypeArgumentName(int index) {
		return this.getTypeTypeArgumentName(index);
	}

	// ***** parameter type names
	public ListIterable<String> getParameterTypeNames() {
		return new LiveCloneListIterable<String>(this.parameterTypeNames);
	}

	public int getParametersSize() {
		return this.parameterTypeNames.size();
	}

	private void syncParameterTypeNames(List<String> astParameterTypeNames) {
		this.synchronizeList(astParameterTypeNames, this.parameterTypeNames, getTypeSuperclassNamesProperty());
	}

	private List<String> buildParameterTypeNames(IMethodBinding methodBinding) {
		if (methodBinding == null) {
			return Collections.emptyList();
		}
		ArrayList<String> names = new ArrayList<String>();
		for (ITypeBinding parameterType : methodBinding.getParameterTypes()) {
			names.add(parameterType.getQualifiedName());
		}
		return names;
	}
}
