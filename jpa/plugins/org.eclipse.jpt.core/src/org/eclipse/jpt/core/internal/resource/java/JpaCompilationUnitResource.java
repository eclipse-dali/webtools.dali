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

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaNodeModel;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

public class JpaCompilationUnitResource extends JpaNodeModel implements JavaResource
{
	/**
	 * The primary type of the CompilationUnit. Not going to handle
	 * multiple Types defined in a compilation unit.  Entities must have
	 * a public/protected no-arg constructor and there is no way to access
	 * it in a non-public/protected class.
	 */
	private JavaPersistentTypeResource persistentType;

	private final ICompilationUnit compilationUnit;
	
	public JpaCompilationUnitResource(JavaResourceModel parent, IFile file) {
		super(parent);
		this.compilationUnit = compilationUnitFrom(file);
		updateFromJava(astRoot());
	}
	
	private ICompilationUnit compilationUnitFrom(IFile file) {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		try {
			compilationUnit.open(null);
		}
		catch (JavaModelException jme) {
			// do nothing - we just won't have a primary type in this case
		}
		return compilationUnit;
	}

	public ICompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}
	
	/**
	 * The persistentType resource for the compilation unit's primary type.
	 * Will be null if the primary type is null.
	 */
	public JavaPersistentTypeResource getPersistentType() {	
		return this.persistentType;
		//TODO should i only be returning this if it returns true to isPersistable?
		//that's how we handle nestedTypes on JavaPersistentTypeResource

//		if (this.persistentType.isPersistable()) {
//		return this.persistentType;
//	}
//	return null;
	}
	
	private JavaPersistentTypeResource createJavaPersistentType(IType iType) {
		Type type = new Type(iType, this.modifySharedDocumentCommandExecutorProvider());
		return new JavaPersistentTypeResourceImpl(this, type);
	}
	
	private void setPersistentType(JavaPersistentTypeResource persistentType) {
		this.persistentType = persistentType;
		//TODO property change notification, or other notification to the context model
	}
	
	private CompilationUnit astRoot() {
		return JDTTools.buildASTRoot(this.compilationUnit);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		IType iType = this.compilationUnit.findPrimaryType();
		if (iType == null) {
			setPersistentType(null);
		}
		else {
			JavaPersistentTypeResource persistentType = createJavaPersistentType(iType);
			setPersistentType(persistentType);
			persistentType.updateFromJava(astRoot);
		}
	}
	
	/**
	 * delegate to the type's project (there is one provider per project)
	 */
	private CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return jpaProject().modifySharedDocumentCommandExecutorProvider();
	}
	
	public ITextRange textRange(CompilationUnit astRoot) {
		return null;//this.selectionTextRange();
	}

//	/**
//	 * Return null for selection textRange.  Entire java file will appear selected when
//	 * switching files otherwise
//	 */
//	public ITextRange selectionTextRange() {
//		return null;
//	}

}
