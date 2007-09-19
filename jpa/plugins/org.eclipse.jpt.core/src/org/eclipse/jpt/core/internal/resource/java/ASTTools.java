/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

public class ASTTools
{
	
	public static boolean containsAnyAnnotation(String[] annotationNames, IBinding binding) {
		for (String annotationName : annotationNames) {
			if (containsAnnotation(annotationName, binding)) {
				return true;
			}
		}
		return false;
	}
	public static boolean containsAnnotation(String annotationName, IBinding binding) {
		for (IAnnotationBinding annotationBinding : binding.getAnnotations()) {
			if (annotationBinding.getName().equals(annotationName)) {
				return true;
			}
		}
		return false;
	}

	
	public static ITypeBinding typeBinding(String typeName, CompilationUnit astRoot) {
		for (AbstractTypeDeclaration typeDeclaration : types(astRoot)) {
			if (typeDeclaration.getNodeType() == ASTNode.TYPE_DECLARATION) { // no enum or annotation declarations
				ITypeBinding typeBinding = typeDeclaration.resolveBinding();
				if (typeBinding.getQualifiedName().equals(typeName)) {
					return typeBinding;
				}
			}
		}
		//TODO throw exception, or just return null??
		return null;
	}
	

	// ********** miscellaneous **********

	@SuppressWarnings("unchecked")
	protected static List<AbstractTypeDeclaration> types(CompilationUnit astRoot) {
		return astRoot.types();
	}


}
