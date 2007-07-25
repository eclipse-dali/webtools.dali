/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

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
public class MethodAttribute extends Attribute {

	public MethodAttribute(IMethod getMethod, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		super(getMethod, modifySharedDocumentCommandExecutorProvider);
	}

	@Override
	public IMethod getJdtMember() {
		return (IMethod) super.getJdtMember();
	}

	@Override
	public MethodDeclaration bodyDeclaration() {
		return (MethodDeclaration) super.bodyDeclaration();
	}


	// ********** Member implementation **********

	@Override
	public MethodDeclaration bodyDeclaration(CompilationUnit astRoot) {
		String methodName = this.getName();
		for (MethodDeclaration methodDeclaration : this.declaringTypeDeclaration(astRoot).getMethods()) {
			if (methodDeclaration.getName().getFullyQualifiedName().equals(methodName)
					&& (methodDeclaration.parameters().size() == 0)) {
				return methodDeclaration;
			}
		}
		return null;
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
	public String typeSignature() {
		try {
			return this.getJdtMember().getReturnType();
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

}
