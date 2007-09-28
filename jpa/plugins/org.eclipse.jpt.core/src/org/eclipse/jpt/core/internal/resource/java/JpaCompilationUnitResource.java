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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

public class JpaCompilationUnitResource
{
	/**
	 * The primary type of the CompilationUnit. Not going to handle
	 * multiple Types defined in a compilation unit.  Entities must have
	 * a public/protected no-arg constructor and there is no way to access
	 * it in a non-public/protected class.
	 */
	private JavaPersistentTypeResource persistentType;

	private final ICompilationUnit compilationUnit;
	
	//TODO move this to a superclass, JpaProject or the parent of JpaCompilationUnit should
	//be in the constructor instead
	private IJpaPlatform jpaPlatform;
	
	public JpaCompilationUnitResource(ICompilationUnit compilationUnit, IJpaPlatform jpaPlatform){
		super();
		this.compilationUnit = compilationUnit;
		this.jpaPlatform = jpaPlatform;
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
		return new JavaPersistentTypeResourceImpl(type, this.jpaPlatform);
	}
	
	private void setPersistentType(JavaPersistentTypeResource persistentType) {
		this.persistentType = persistentType;
		//TODO property change notification, or other notification to the context model
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
	//TODO using platform instead of project that i should be getting from a super class
	private CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return this.jpaPlatform.modifySharedDocumentCommandExecutorProvider();
	}
}
