/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.JavaType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.SimpleMethodSignature;

/**
 * Adapt and extend a JDT method.
 * Attribute based on a Java property, e.g.
 *     private int getFoo() {
 *         return foo;
 *     }
 *     private void setFoo(int foo) {
 *         this.foo = foo;
 *     }
 */
public class JDTMethodAttribute
	extends JDTAttribute
	implements MethodAttribute
{
	/** we need the parameter types to build the method signature */
	private final JavaType[] parameterTypes;


	// ********** constructors **********

	public static JDTMethodAttribute newInstance(
			Type declaringType,
			MethodSignature signature,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		return newInstance(declaringType, signature, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	public static JDTMethodAttribute newInstance(
			Type declaringType,
			MethodSignature signature,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		return new JDTMethodAttribute(declaringType, signature, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	public JDTMethodAttribute(
			Type declaringType,
			MethodSignature methodSignature,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(declaringType, methodSignature, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	public JDTMethodAttribute(
			Type declaringType,
			MethodSignature methodSignature,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, methodSignature.getName(), occurrence, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
		this.parameterTypes = methodSignature.getParameterTypes();
	}

	/**
	 * constructor for testing
	 */
	public JDTMethodAttribute(Type declaringType, String name, String[] parameterTypeNames, int occurrence, ICompilationUnit compilationUnit) {
		this(declaringType, new SimpleMethodSignature(name, parameterTypeNames), occurrence, compilationUnit, CommandExecutor.Default.instance(), DefaultAnnotationEditFormatter.instance());
	}


	// ********** Member/Attribute/MethodAttribute implementation **********

	public IMethodBinding getBinding(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).resolveBinding();
	}

	public MethodDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		int count = 0;
		for (MethodDeclaration methodDeclaration : this.getDeclaringTypeMethodDeclarations(astRoot)) {
			if (this.matches(methodDeclaration)) {
				count++;
				if (count == this.getOccurrence()) {
					return methodDeclaration;
				}
			}
		}
		// return null if the method is no longer in the source code;
		// this can happen when the context model has not yet
		// been synchronized with the resource model but is still
		// asking for an ASTNode (e.g. during a selection event)
		return null;
	}

	public boolean matches(MethodSignature signature, int occurrence) {
		return this.matches(signature) && (occurrence == this.getOccurrence());
	}

	protected boolean matches(MethodSignature signature) {
		return signature.getName().equals(this.getName_())
					&& Arrays.equals(this.parameterTypes, signature.getParameterTypes());
	}

	protected boolean matches(MethodDeclaration methodDeclaration) {
		return this.matches(JDTTools.buildMethodSignature(methodDeclaration));
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected static List<SingleVariableDeclaration> parameters(MethodDeclaration methodDeclaration) {
		return methodDeclaration.parameters();
	}

	@Override
	public boolean matches(String memberName, int occurrence) {
		throw new UnsupportedOperationException("Use #matches(MethodSignature, int)."); //$NON-NLS-1$
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return new ASTNodeTextRange(this.getBodyDeclaration(astRoot).getName());
	}

	/**
	 * return "foo" for a method named "getFoo" or "isFoo"
	 */
	public String getAttributeName() {
		return NameTools.convertGetterMethodNameToPropertyName(this.getName_());
	}

	public ITypeBinding getTypeBinding(CompilationUnit astRoot) {
		IMethodBinding methodBinding = getBodyDeclaration(astRoot).resolveBinding();
		return (methodBinding == null) ? null : methodBinding.getReturnType();
	}

	public boolean isPersistable(CompilationUnit astRoot) {
		IMethodBinding binding = this.getBinding(astRoot);
		return (binding == null) ? false : JPTTools.methodIsPersistablePropertyGetter(new JPTToolsAdapter(binding));
	}


	// ********** internal **********

	protected MethodDeclaration[] getDeclaringTypeMethodDeclarations(CompilationUnit astRoot) {
		return this.getDeclaringTypeDeclaration(astRoot).getMethods();
	}


	// ********** JPTTools adapter **********

	/**
	 * JPTTools needs an adapter so it can work with either an IMethod
	 * or an IMethodBinding etc.
	 */
	protected static class JPTToolsAdapter implements JPTTools.MethodAdapter {
		private final IMethodBinding methodBinding;

		protected JPTToolsAdapter(IMethodBinding methodBinding) {
			super();
			if (methodBinding == null) {
				throw new NullPointerException();
			}
			this.methodBinding = methodBinding;
		}

		public String getName() {
			return this.methodBinding.getName();
		}

		public int getModifiers() {
			return this.methodBinding.getModifiers();
		}

		public String getReturnTypeName() {
			ITypeBinding returnType = this.methodBinding.getReturnType();
			return (returnType == null) ? null : returnType.getTypeDeclaration().getQualifiedName();
		}

		public boolean isConstructor() {
			return this.methodBinding.isConstructor();
		}

		public int getParametersLength() {
			return this.methodBinding.getParameterTypes().length;
		}

		public JPTTools.MethodAdapter getSibling(String name) {
			ITypeBinding typeBinding = this.methodBinding.getDeclaringClass();
			if (typeBinding == null) {
				return null;
			}
			for (IMethodBinding sibling : typeBinding.getDeclaredMethods()) {
				if ((sibling.getParameterTypes().length == 0)
						&& sibling.getName().equals(name)) {
					return new JPTToolsAdapter(sibling);
				}
			}
			return null;
		}

		public JPTTools.MethodAdapter getSibling(String name, String parameterTypeName) {
			ITypeBinding typeBinding = this.methodBinding.getDeclaringClass();
			if (typeBinding == null) {
				return null;
			}
			for (IMethodBinding sibling : typeBinding.getDeclaredMethods()) {
				ITypeBinding[] parmTypes = sibling.getParameterTypes();
				if ((parmTypes.length == 1)
						&& parmTypes[0].getTypeDeclaration().getQualifiedName().equals(parameterTypeName)
						&& sibling.getName().equals(name)) {
					return new JPTToolsAdapter(sibling);
				}
			}
			return null;
		}

	}

}
