/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import java.beans.Introspector;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.utility.CommandExecutorProvider;

/**
 * Adapt and extend a jdt method.
 * Attribute based on a Java property, e.g.
 *     private int getFoo() {
 *         return foo;
 *     }
 *     private void setFoo(int foo) {
 *         this.foo = foo;
 *     }
 * 
 * For now we only hold the getter method, since that's where the
 * annotations are put.
 */
public class JDTMethodAttribute
	extends JDTAttribute
	implements MethodAttribute
{

	public JDTMethodAttribute(IMethod getMethod, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		super(getMethod, modifySharedDocumentCommandExecutorProvider);
	}
	
	public JDTMethodAttribute(IMethod getMethod, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider, AnnotationEditFormatter annotationEditFormatter) {
		super(getMethod, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
	}

	@Override
	public IMethod getJdtMember() {
		return (IMethod) super.getJdtMember();
	}
	

	// ********** Member implementation **********

	@Override
	public MethodDeclaration bodyDeclaration(CompilationUnit astRoot) {
		try {
			return ASTNodeSearchUtil.getMethodDeclarationNode(getJdtMember(), astRoot);
		} catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public IMethodBinding binding(CompilationUnit astRoot) {
		return bodyDeclaration(astRoot).resolveBinding();
	}

	// ********** Attribute implementation **********

	@Override
	public boolean isMethod() {
		return true;
	}

	/**
	 * "foo" returned for a method named "getFoo" or "isFoo"
	 */
	@Override
	public String attributeName() {
		String methodName = super.getName();
		int beginIndex = 0;
		if (methodName.startsWith("get")) {
			beginIndex = 3;
		} else if (methodName.startsWith("is")) {
			beginIndex = 2;
		}
		return Introspector.decapitalize(methodName.substring(beginIndex));
	}
	
	@Override
	public ITypeBinding typeBinding(CompilationUnit astRoot) {
		IMethodBinding methodBinding = bodyDeclaration(astRoot).resolveBinding();
		if (methodBinding != null) {
			return methodBinding.getReturnType();
		}
		return null;
	}
}
