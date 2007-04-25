/*******************************************************************************
 * Copyright (c) 2005, 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.internal.codeassist.ISelectionRequestor;
import org.eclipse.jdt.internal.codeassist.SelectionEngine;
import org.eclipse.jdt.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.SearchableEnvironment;

public class JDTTools {

	/**
	 * add a "normal" import, as opposed to a "static" import
	 */
	public static IImportDeclaration addImport(ICompilationUnit compilationUnit, String importElement) {
		return addImport(compilationUnit, importElement, Flags.AccDefault);
	}

	/**
	 * this doesn't work yet... see eclipse bugzilla 143684
	 */
	public static IImportDeclaration addStaticImport(ICompilationUnit compilationUnit, String importElement) {
		return addImport(compilationUnit, importElement, Flags.AccStatic);
	}

	public static IImportDeclaration addImport(ICompilationUnit compilationUnit, String importElement, int flags) {
		try {
			return compilationUnit.createImport(importElement, null, flags, null);  // null = place at end of import list; null = no progress monitor
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Build an AST for the specified member's compilation unit or class file.
	 */
	public static CompilationUnit createASTRoot(IMember member) {
		return (member.isBinary()) ?
			createASTRoot(member.getClassFile())  // the class file must have a source attachment
		:
			createASTRoot(member.getCompilationUnit());
	}
	
	public static CompilationUnit createASTRoot(IClassFile classFile) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(classFile);
		return (CompilationUnit) parser.createAST(null);
		
	}
	
	public static CompilationUnit createASTRoot(ICompilationUnit compilationUnit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(compilationUnit);
		return (CompilationUnit) parser.createAST(null);
	}

	/**
	 * Resolve the specified signature in the scope of the specified jdt type.
	 * Return the fully-qualified type name or return null if it cannot be
	 * resolved unambiguously.
	 */
	public static String resolveSignature(String signature, IType type) {
		String elementSignature = Signature.getElementType(signature);
		if (signatureIsPrimitive(elementSignature)) {
			return Signature.toString(signature);  // no need to resolve primitives
		}
		String elementTypeName = Signature.toString(elementSignature);
		elementTypeName = resolve(elementTypeName, type);
		if (elementTypeName == null) {
			return null;  // unable to resolve type
		}
		int arrayCount = Signature.getArrayCount(signature);
		if (arrayCount == 0) {
			return elementTypeName;
		}
		StringBuffer sb = new StringBuffer(elementTypeName.length() + 2*arrayCount);
		sb.append(elementTypeName);
		for (int i = arrayCount; i-- > 0; ) {
			sb.append('[').append(']');
		}
		return sb.toString();
	}

	/**
	 * Resolve the specified type name in the scope of the specified jdt type.
	 * Return the fully-qualified type name or return null if it cannot be
	 * resolved unambiguously.
	 */
	public static String resolve(String typeName, IType type) {
		try {
			return resolve_(typeName, type);
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String resolve_(String typeName, IType type) throws JavaModelException {
		String[][] resolvedTypes = type.resolveType(typeName);
		// if more than one resolved type is returned, the type name is ambiguous
		if (resolvedTypes == null) {
			return null;
		}
		if (resolvedTypes.length > 1) {
			return null;
		}
		return resolvedTypes[0][0] + "." + resolvedTypes[0][1];
	}

	public static boolean signatureIsPrimitive(String signature) {
		return Signature.getTypeSignatureKind(signature) == Signature.BASE_TYPE_SIGNATURE;
	}

	public static String resolveEnum(ICompilationUnit sourceUnit, Expression enumExpression) {
		return resolveEnum(sourceUnit, enumExpression.getStartPosition(), enumExpression.getStartPosition() + enumExpression.getLength() - 1);
	}

	public static String resolveEnum(ICompilationUnit sourceUnit, int enumSourceStart, int enumSourceEnd) {
		try {
			return resolveEnum_(sourceUnit, enumSourceStart, enumSourceEnd);
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String resolveEnum_(ICompilationUnit sourceUnit, int enumSourceStart, int enumSourceEnd) throws JavaModelException {
		String[][] resolvedEnums = resolveField_((org.eclipse.jdt.internal.core.CompilationUnit) sourceUnit, enumSourceStart, enumSourceEnd);
		// if more than one resolved enum is returned, the enum name is ambiguous
		if (resolvedEnums == null) {
			return null;
		}
		if (resolvedEnums.length > 1) {
			return null;
		}
		return resolvedEnums[0][0] + "." + resolvedEnums[0][1] + "." + resolvedEnums[0][2];
	}

	// code lifted from SourceType.resolveType(String, WorkingCopyOwner)
	private static String[][] resolveField_(org.eclipse.jdt.internal.core.CompilationUnit sourceUnit, int selectionSourceStart, int selectionSourceEnd) throws JavaModelException {
		class TypeResolveRequestor implements ISelectionRequestor {
			String[][] answers = null;
			public void acceptType(char[] packageName, char[] tName, int modifiers, boolean isDeclaration, char[] uniqueKey, int start, int end) {
				// ignore
			}
			public void acceptError(CategorizedProblem error) {
				// ignore
			}
			public void acceptField(char[] declaringTypePackageName, char[] declaringTypeName, char[] fieldName, boolean isDeclaration, char[] uniqueKey, int start, int end) {
				String[] answer = new String[]  {new String(declaringTypePackageName), new String(declaringTypeName), new String(fieldName) };
				if (this.answers == null) {
					this.answers = new String[][]{ answer };
				} else {
					int len = this.answers.length;
					System.arraycopy(this.answers, 0, this.answers = new String[len+1][], 0, len);
					this.answers[len] = answer;
				}
			}
			public void acceptMethod(char[] declaringTypePackageName, char[] declaringTypeName, String enclosingDeclaringTypeSignature, char[] selector, char[][] parameterPackageNames, char[][] parameterTypeNames, String[] parameterSignatures, char[][] typeParameterNames, char[][][] typeParameterBoundNames, boolean isConstructor, boolean isDeclaration, char[] uniqueKey, int start, int end) {
				// ignore
			}
			public void acceptPackage(char[] packageName){
				// ignore
			}
			public void acceptTypeParameter(char[] declaringTypePackageName, char[] declaringTypeName, char[] typeParameterName, boolean isDeclaration, int start, int end) {
				// ignore
			}
			public void acceptMethodTypeParameter(char[] declaringTypePackageName, char[] declaringTypeName, char[] selector, int selectorStart, int selcetorEnd, char[] typeParameterName, boolean isDeclaration, int start, int end) {
				// ignore
			}
	
		}
		TypeResolveRequestor requestor = new TypeResolveRequestor();
		JavaProject project = (JavaProject) sourceUnit.getJavaProject();
		SearchableEnvironment environment = project.newSearchableNameEnvironment(DefaultWorkingCopyOwner.PRIMARY);
	
		SelectionEngine engine = new SelectionEngine(environment, requestor, project.getOptions(true));
			
		engine.select(sourceUnit, selectionSourceStart, selectionSourceEnd);
		return requestor.answers;
	}
}
