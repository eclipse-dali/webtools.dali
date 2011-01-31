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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageInfoCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * @author Dmitry Geraskov
 * Source package-info.java
 *
 */
public final class SourcePackageInfoCompilationUnit
	extends SourceCompilationUnit
	implements JavaResourcePackageInfoCompilationUnit {

	private JavaResourcePackage package_;	

	public SourcePackageInfoCompilationUnit(
			ICompilationUnit compilationUnit,
			JpaAnnotationProvider annotationProvider, 
			AnnotationEditFormatter annotationEditFormatter,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		super(compilationUnit, annotationProvider, annotationEditFormatter, modifySharedDocumentCommandExecutor);  // the JPA compilation unit is the root of its sub-tree
		this.package_ = this.buildPackage();
	}


	private JavaResourcePackage buildPackage() {
		this.openCompilationUnit();
		CompilationUnit astRoot = this.buildASTRoot();
		this.closeCompilationUnit();
		return this.buildPackage(astRoot);
	}


	// ********** JavaResourceNode.Root implementation **********

	public Iterator<JavaResourcePersistentType> persistentTypes() {
		return EmptyIterator.instance();
	}


	// ********** JptResourceModel implementation **********

	public JptResourceType getResourceType() {
		return JptCommonCorePlugin.JAVA_SOURCE_PACKAGE_INFO_RESOURCE_TYPE;
	}


	// ********** Java changes **********

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncPackage(astRoot);
	}


	// ********** JavaResourceCompilationUnit implementation **********

	public void resolveTypes() {
		//no-op
	}

	// ********** package-info **********

	public JavaResourcePackage getPackage() {
		return this.package_;
	}

	private JavaResourcePackage buildPackage(CompilationUnit astRoot) {
		return this.buildPackage(astRoot, this.getPackageDeclaration(astRoot));
	}

	private void syncPackage(CompilationUnit astRoot) {
		PackageDeclaration pd = this.getPackageDeclaration(astRoot);
		if (pd == null) {
			this.syncPackage_(null);
		} else {
			if (this.package_ == null) {
				this.syncPackage_(this.buildPackage(astRoot, pd));
			} else {
				this.package_.synchronizeWith(astRoot);
			}
		}
	}

	private PackageDeclaration getPackageDeclaration(CompilationUnit astRoot) {
		return astRoot.getPackage();
	}

	private void syncPackage_(JavaResourcePackage astPackage) {
		JavaResourcePackage old = this.package_;
		this.package_ = astPackage;
		this.firePropertyChanged(PACKAGE, old, astPackage);
	}

	private JavaResourcePackage buildPackage(CompilationUnit astRoot, PackageDeclaration packageDeclaration) {
		return SourcePackage.newInstance(this, packageDeclaration, astRoot);
	}

}
