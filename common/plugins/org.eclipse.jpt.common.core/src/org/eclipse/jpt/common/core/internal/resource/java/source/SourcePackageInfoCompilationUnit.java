/*******************************************************************************
 * Copyright (c) 2010, 2013 Red Hat, Inc. and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageInfoCompilationUnit;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;

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
			AnnotationProvider annotationProvider, 
			AnnotationEditFormatter annotationEditFormatter,
			CommandContext modifySharedDocumentCommandContext) {
		super(compilationUnit, annotationProvider, annotationEditFormatter, modifySharedDocumentCommandContext);  // the JPA compilation unit is the root of its sub-tree
		this.package_ = this.buildPackage();
	}


	private JavaResourcePackage buildPackage() {
		this.openCompilationUnit();
		CompilationUnit astRoot = this.buildASTRoot();
		this.closeCompilationUnit();
		return this.buildPackage(astRoot);
	}


	// ********** JavaResourceNode.Root implementation **********

	public Iterable<JavaResourceAbstractType> getTypes() {
		return EmptyIterable.instance();
	}

	public JavaResourceAbstractType getPrimaryType() {
		return null;
	}

	// ********** JptResourceModel implementation **********

	public JptResourceType getResourceType() {
		return ContentTypeTools.getResourceType(PACKAGE_INFO_CONTENT_TYPE);
	}


	// ********** Java changes **********

	@Override
	protected void synchronizeWith(CompilationUnit astRoot) {
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
		return this.buildPackage(this.getPackageDeclaration(astRoot));
	}

	private void syncPackage(CompilationUnit astRoot) {
		PackageDeclaration pd = this.getPackageDeclaration(astRoot);
		if (pd == null) {
			this.syncPackage_(null);
		} else {
			if (this.package_ == null) {
				this.syncPackage_(this.buildPackage(pd));
			} else {
				this.package_.synchronizeWith(pd);
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

	private JavaResourcePackage buildPackage(PackageDeclaration pd) {
		return SourcePackage.newInstance(this, pd);
	}

}
