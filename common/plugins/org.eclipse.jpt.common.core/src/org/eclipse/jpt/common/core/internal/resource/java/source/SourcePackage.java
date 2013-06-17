/*******************************************************************************
  * Copyright (c) 2010, 2012 Red Hat, Inc.
  * Distributed under license by Red Hat, Inc. All rights reserved.
  * This program is made available under the terms of the
  * Eclipse Public License v1.0 which accompanies this distribution,
  * and is available at http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributors:
  *     Red Hat, Inc. - initial API and implementation
  *     Oracle
  ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTPackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @author Dmitry Geraskov
 * Source package-info.java
 *
 */
public final class SourcePackage
	extends SourceAnnotatedElement<AnnotatedPackage>
	implements JavaResourcePackage
{

	private String name;

	/**
	 * construct package info
	 */
	public static JavaResourcePackage newInstance(
			JavaResourceCompilationUnit parent,
			PackageDeclaration declaringPackage) {
		AnnotatedPackage pack = new JDTPackage(
				declaringPackage,
				parent.getCompilationUnit(), 
				parent.getModifySharedDocumentCommandContext(),
				parent.getAnnotationEditFormatter());
		SourcePackage jrpp = new SourcePackage(parent, pack);
		jrpp.initialize(declaringPackage);
		return jrpp;
	}	

	private SourcePackage(
			JavaResourceCompilationUnit parent,
			AnnotatedPackage pack){
		super(parent, pack);
	}


	protected void initialize(PackageDeclaration packageDeclaration) {
		super.initialize(packageDeclaration, packageDeclaration.getName());
		this.initialize(packageDeclaration.resolveBinding());
	}

	protected void initialize(IPackageBinding packageBinding) {
		this.name = this.buildName(packageBinding);
	}

	// ******** JavaResourceAnnotatedElement implementation ********
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.PACKAGE;
	}


	// ********** JavaResourcePackageInfo implementation **********
	
	// ***** name
	public String getName() {
		return this.name;
	}

	private void syncName(String astName) {
		if (ObjectTools.notEquals(astName, this.name)){
			String old = this.name;
			this.name = astName;
			this.firePropertyChanged(NAME_PROPERTY, old, astName);
		}		
	}

	private String buildName(IPackageBinding binding) {
		return (binding == null) ? null : binding.getName();
	}


	// ********** Java changes **********

	public void synchronizeWith(PackageDeclaration packageDeclaration) {
		super.synchronizeWith(packageDeclaration, packageDeclaration.getName());
		this.synchronizeWith(packageDeclaration.resolveBinding());
	}

	protected void synchronizeWith(IPackageBinding packageBinding) {
		this.syncName(this.buildName(packageBinding));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
