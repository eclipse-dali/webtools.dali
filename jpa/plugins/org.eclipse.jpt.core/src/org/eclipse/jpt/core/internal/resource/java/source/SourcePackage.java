/*******************************************************************************
  * Copyright (c) 2010 Red Hat, Inc.
  * Distributed under license by Red Hat, Inc. All rights reserved.
  * This program is made available under the terms of the
  * Eclipse Public License v1.0 which accompanies this distribution,
  * and is available at http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributor:
  *     Red Hat, Inc. - initial API and implementation
  ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.Iterator;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jpt.core.internal.utility.jdt.JDTPackage;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.core.utility.jdt.Package;

/**
 * @author Dmitry Geraskov
 * Source package-info.java
 *
 */
public final class SourcePackage
	extends SourceAnnotatedElement<Package>
	implements JavaResourcePackage {
	
	private String name;
	
	/**
	 * construct package info
	 */
	public static JavaResourcePackage newInstance(
			JavaResourceCompilationUnit parent,
			PackageDeclaration declaringPackage,
			CompilationUnit astRoot) {
		Package pack = new JDTPackage(
				declaringPackage,
				parent.getCompilationUnit(), 
				parent.getModifySharedDocumentCommandExecutor(),
				parent.getAnnotationEditFormatter());
		JavaResourcePackage jrpp = new SourcePackage(parent, pack);
		jrpp.initialize(astRoot);
		return jrpp;
	}	
	
	private SourcePackage(
			JavaResourceCompilationUnit parent,
			Package pack){
		super(parent, pack);
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.name = this.buildName(astRoot);
	}


	// ********** JavaResourcePackageInfo implementation **********
	
	// ***** name
	public String getName() {
		return this.name;
	}

	private void syncName(String astName) {
		if (valuesAreDifferent(astName, this.name)){
			String old = this.name;
			this.name = astName;
			this.firePropertyChanged(NAME_PROPERTY, old, astName);
		}		
	}

	private String buildName(CompilationUnit astRoot) {
		IPackageBinding binding = this.annotatedElement.getBinding(astRoot);
		return (binding == null) ? null : binding.getName();
	}

	
	// ********** Java changes **********

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncName(this.buildName(astRoot));
	}
	
	// ********** SourceAnnotatedElement implementation **********
	
	@Override
	Iterator<String> validAnnotationNames() {
		return this.getAnnotationProvider().packageAnnotationNames();
	}
	
	
	@Override
	Annotation buildAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildPackageAnnotation(this, this.annotatedElement, annotationName);
	}

	@Override
	Annotation buildNullAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullPackageAnnotation(this, annotationName);
	}


	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}
