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
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

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
	
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.METHOD;
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
		return IterableTools.cloneLive(this.parameterTypeNames);
	}
	
	public String getParameterTypeName(int index) {
		return this.parameterTypeNames.get(index);
	}
	
	public int getParametersSize() {
		return this.parameterTypeNames.size();
	}
	
	private Iterable<String> buildParameterTypeNames(IMethodBinding binding) {
		return (binding == null) ?
				EmptyIterable.<String>instance() :
				IterableTools.transform(IterableTools.iterable(binding.getParameterTypes()), TYPE_BINDING_DECLARATION_QUALIFIED_NAME);
	}

	private static final Transformer<ITypeBinding, String> TYPE_BINDING_DECLARATION_QUALIFIED_NAME = new TypeBindingDeclarationQualifiedName();
	/* CU private */ static class TypeBindingDeclarationQualifiedName
		extends TransformerAdapter<ITypeBinding, String>
	{
		@Override
		public String transform(ITypeBinding typeBinding) {
			return typeBinding.getTypeDeclaration().getQualifiedName();
		}
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
			return NameTools.convertGetterOrSetterMethodNameToPropertyName(this.method.getElementName());
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
