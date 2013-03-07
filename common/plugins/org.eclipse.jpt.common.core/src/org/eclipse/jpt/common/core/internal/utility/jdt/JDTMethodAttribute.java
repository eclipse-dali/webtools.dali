/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.Arrays;
import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.JavaType;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.SimpleMethodSignature;

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
	extends JDTMember
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
			CommandContext modifySharedDocumentCommandContext) {
		return newInstance(declaringType, signature, occurrence, compilationUnit, modifySharedDocumentCommandContext, DefaultAnnotationEditFormatter.instance());
	}

	public static JDTMethodAttribute newInstance(
			Type declaringType,
			MethodSignature signature,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		return new JDTMethodAttribute(declaringType, signature, occurrence, compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
	}

	private JDTMethodAttribute(
			Type declaringType,
			MethodSignature methodSignature,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, methodSignature.getName(), occurrence, compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
		this.parameterTypes = methodSignature.getParameterTypes();
	}

	/**
	 * constructor for testing
	 */
	public JDTMethodAttribute(Type declaringType, String name, String[] parameterTypeNames, int occurrence, ICompilationUnit compilationUnit) {
		this(declaringType, new SimpleMethodSignature(name, parameterTypeNames), occurrence, compilationUnit, CommandContext.Default.instance(), DefaultAnnotationEditFormatter.instance());
	}


	// ********** Member/Attribute/MethodAttribute implementation **********

	@Override
	protected Type getDeclaringType() {
		return (Type) super.getDeclaringType();
	}

	public boolean isField() {
		return false;
	}

	@Override
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
		return signature.getName().equals(this.getName())
					&& Arrays.equals(this.parameterTypes, signature.getParameterTypes());
	}

	protected boolean matches(MethodDeclaration methodDeclaration) {
		return this.matches(ASTTools.buildMethodSignature(methodDeclaration));
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

	/**
	 * return "foo" for a method named "getFoo", "isFoo", "setFoo"
	 */
	public String getAttributeName() {
		return NameTools.convertGetterOrSetterMethodNameToPropertyName(this.getName());
	}


	// ********** internal **********

	protected TypeDeclaration getDeclaringTypeDeclaration(CompilationUnit astRoot) {
		// assume the declaring type is not an enum or annotation
		// since they do not have field or method declarations
		return this.getDeclaringType().getBodyDeclaration(astRoot);
	}

	protected MethodDeclaration[] getDeclaringTypeMethodDeclarations(CompilationUnit astRoot) {
		TypeDeclaration typeDeclaration = this.getDeclaringTypeDeclaration(astRoot);
		// the declaration can be null if the resource is out of sync with the file system
		return (typeDeclaration == null) ? EMPTY_METHOD_DECLARATION_ARRAY : typeDeclaration.getMethods();
	}
	protected static final MethodDeclaration[] EMPTY_METHOD_DECLARATION_ARRAY = new MethodDeclaration[0];


}
