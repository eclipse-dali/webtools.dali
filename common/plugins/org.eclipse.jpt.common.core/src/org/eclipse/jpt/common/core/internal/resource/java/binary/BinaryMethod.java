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

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

/**
 * binary method
 */
final class BinaryMethod
		extends BinaryAttribute
		implements JavaResourceMethod {
	
	private boolean constructor;
	
	private final Vector<String> parameterTypeNames = new Vector<String>();
	
	
	BinaryMethod(JavaResourceType parent, IMethod method) {
		this(parent,new MethodAdapter(method));
	}
	
	private BinaryMethod(JavaResourceType parent, MethodAdapter adapter) {
		super(parent, adapter);
		this.constructor = buildConstructor();
		CollectionTools.addAll(this.parameterTypeNames, buildParameterTypeNames(adapter.getMethodBinding()));
	}
	
	
	public Kind getKind() {
		return JavaResourceAnnotatedElement.Kind.METHOD;
	}
	
	public void synchronizeWith(MethodDeclaration methodDeclaration) {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** overrides *****
	
	@Override
	public void update() {
		super.update();
		updateConstructor();
		updateParameterTypeNames();
	}
	
	@Override
	public IMethod getElement() {
		return (IMethod) super.getElement();
	}
	
	
	// ***** method name *****
	
	public String getMethodName() {
		return getElement().getElementName();
	}
	
	
	// ***** constructor *****
	
	public boolean isConstructor() {
		return this.constructor;
	}
	
	private boolean buildConstructor() {
		try {
			return getElement().isConstructor();
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}
	
	protected void updateConstructor() {
		throw new UnsupportedOperationException();
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
	
	private Iterable<String> buildParameterTypeNames(IMethodBinding binding) {
		if (binding == null) {
			return EmptyIterable.instance();
		}
		return new TransformationIterable<ITypeBinding, String>(
				new ArrayIterable<ITypeBinding>(binding.getParameterTypes())) {
			@Override
			protected String transform(ITypeBinding parameterType) {
				return parameterType.getTypeDeclaration().getQualifiedName();
			}
		};
	}
	
	private void updateParameterTypeNames() {
		throw new UnsupportedOperationException();
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
			implements AttributeAdapter {
		
		final IMethod method;
		
		static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];
		
		/* cached, but only during initialization */
		private final IBinding binding;
		
		
		MethodAdapter(IMethod method) {
			super();
			this.method = method;
			this.binding = createBinding(method);
		}
		
		protected IBinding createBinding(IMethod method) {
			return ASTTools.createBinding(method);
		}
		
		public IMethod getElement() {
			return this.method;
		}
		
		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.method.getAnnotations();
		}
		
		public String getAttributeName() {
			return NameTools.convertGetterSetterMethodNameToPropertyName(this.method.getElementName());
		}
		
		/* NB - may return null */
		public IMethodBinding getMethodBinding() {
			// bug 381503 - if the binary method is a constructor,
			// the jdtBinding will be a JavaResourceTypeBinding already
			if (this.binding.getKind() == IBinding.TYPE) {
				return null;
			}
			return (IMethodBinding) binding;
		}
		
		public ITypeBinding getTypeBinding() {
			// bug 381503 - if the binary method is a constructor,
			// the jdtBinding will be a JavaResourceTypeBinding already
			if (this.binding.getKind() == IBinding.TYPE) {
				return (ITypeBinding) binding;
			}
			return ((IMethodBinding) binding).getReturnType();
		}
	}
}
