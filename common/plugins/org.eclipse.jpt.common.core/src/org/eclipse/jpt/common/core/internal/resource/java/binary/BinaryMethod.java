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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;

/**
 * binary method
 */
final class BinaryMethod
		extends BinaryAttribute
		implements JavaResourceMethod {
	
	private boolean constructor;
	
	private final Vector<String> parameterTypeNames = new Vector<String>();
	
	
	BinaryMethod(JavaResourceType parent, IMethod method) {
		super(parent, new MethodAdapter(method));
	}
	
	public Kind getKind() {
		return JavaResourceAnnotatedElement.Kind.METHOD;
	}
	
	// ***** overrides *****
	
	@Override
	public void update() {
		super.update();
		this.setConstructor(this.buildConstructor());

		this.setParameterTypeNames(this.buildParameterTypeNames());
	}

	public void initialize(MethodDeclaration methodDeclaration) {
		throw new UnsupportedOperationException();
	}

	public void synchronizeWith(MethodDeclaration methodDeclaration) {
		throw new UnsupportedOperationException();
	}

	@Override
	IMethod getMember() {
		return (IMethod) super.getMember();
	}
	
	
	// ***** method name *****
	
	public String getMethodName() {
		return getMember().getElementName();
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
	
	private List<String> buildParameterTypeNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (ILocalVariable parameter : this.getParameters(this.getMember())) {
			names.add(parameter.getElementName());//TODO is this right?
		}
		return names;
	}
	
	private ILocalVariable[] getParameters(IMethod jdtMethod) {
		try {
			return jdtMethod.getParameters();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return null;
		}
	}
	
	private void setParameterTypeNames(List<String> parameterTypeNames) {
		this.synchronizeList(parameterTypeNames, this.parameterTypeNames, PARAMETER_TYPE_NAMES_LIST);
	}
	
	
	// ***** constructor *****
	
	public boolean isConstructor() {
		return this.constructor;
	}
	
	private void setConstructor(boolean isConstructor) {
		boolean old = this.constructor;
		this.constructor = isConstructor;
		this.firePropertyChanged(CONSTRUCTOR_PROPERTY, old, isConstructor);
	}
	
	private boolean buildConstructor() {
		try {
			return this.getMember().isConstructor();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}
	
	
	// ***** misc *****
	
	public boolean isFor(MethodSignature methodSignature, int occurrence) {
		throw new UnsupportedOperationException();
	}
	
	
	// ********** adapters **********

	/**
	 * IMethod adapter
	 */
	static class MethodAdapter
			implements BinaryAttribute.Adapter {
		
		final IMethod method;
		static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];
		
		MethodAdapter(IMethod method) {
			super();
			this.method = method;
		}
		
		public IMethod getElement() {
			return this.method;
		}
		
		public Iterable<ITypeParameter> getTypeParameters() {
			try {
				return new CompositeIterable<ITypeParameter>(
						new ArrayIterable<ITypeParameter>(this.method.getTypeParameters()),
						new ArrayIterable<ITypeParameter>(this.method.getDeclaringType().getTypeParameters()));
			}
			catch (JavaModelException jme) {
				JptCommonCorePlugin.log(jme);
			}
			return EmptyIterable.instance();
		}
		
		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.method.getAnnotations();
		}
		
		public String getAttributeName() {
			return NameTools.convertGetterSetterMethodNameToPropertyName(this.method.getElementName());
		}
		
		public String getTypeSignature() throws JavaModelException {
			return this.method.getReturnType();
		}
	}
}
