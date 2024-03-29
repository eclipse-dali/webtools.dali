/*******************************************************************************
 * Copyright (c) 2005, 2021 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jst.common.project.facet.core.internal.FacetCorePlugin;

/**
 * This builds and holds a "Java" project.
 * Support for adding packages and types.
 * <p>
 * "Java" projects aren't required to be "faceted" projects, but for JPA
 * testing they are.
 */
@SuppressWarnings("nls")
public class JavaProjectTestHarness
	extends FacetedProjectTestHarness
{
	private final IJavaProject javaProject;
	private final IPackageFragmentRoot sourceFolder;


	public JavaProjectTestHarness(String projectName) throws CoreException {
		this(projectName, false);
	}

	public JavaProjectTestHarness(String projectName, boolean autoBuild) throws CoreException {
		super(projectName, autoBuild);
		this.installFacet("jst.java", "5.0");
		this.javaProject = JavaCore.create(this.getProject());
		this.sourceFolder = this.javaProject.getPackageFragmentRoot(this.getProject().getFolder(FacetCorePlugin.DEFAULT_SOURCE_FOLDER));
	}

	public void addJar(String jarPath) throws JavaModelException {
		this.addClasspathEntry(JavaCore.newLibraryEntry(new Path(jarPath), null, null));
	}

	private void addClasspathEntry(IClasspathEntry entry) throws JavaModelException {
		this.javaProject.setRawClasspath(ArrayTools.add(this.javaProject.getRawClasspath(), entry), null);
	}
	

	// ********** public methods **********

	public IJavaProject getJavaProject() {
		return this.javaProject;
	}

	public IPackageFragment createPackage(String packageName) throws CoreException {
		return this.sourceFolder.createPackageFragment(packageName, false, null);	// false = "no force"
	}

	/**
	 * The source should NOT contain a package declaration;
	 * it will be added here.
	 */
	public ICompilationUnit createCompilationUnit(String packageName, String compilationUnitName, String source) throws CoreException {
		return this.createCompilationUnit(this.createPackage(packageName), compilationUnitName, new SimpleSourceWriter(source));
	}

	/**
	 * The source should NOT contain a package declaration;
	 * it will be added here.
	 */
	public ICompilationUnit createCompilationUnit(String packageName, String compilationUnitName, SourceWriter sourceWriter) throws CoreException {
		return this.createCompilationUnit(this.createPackage(packageName), compilationUnitName, sourceWriter);
	}

	/**
	 * The source should NOT contain a package declaration;
	 * it will be added here.
	 */
	public ICompilationUnit createCompilationUnit(IPackageFragment packageFragment, String compilationUnitName, SourceWriter sourceWriter) throws CoreException {
		StringBuilder sb = new StringBuilder(2000);
		sourceWriter.appendSourceTo(sb);
		String source = sb.toString();
		return packageFragment.createCompilationUnit(compilationUnitName, source, false, null);	// false = "no force"
	}


	// ********** member classes **********

	public interface SourceWriter {
		void appendSourceTo(StringBuilder sb);
	}

	public class SimpleSourceWriter implements SourceWriter {
		private final String source;
		public SimpleSourceWriter(String source) {
			super();
			this.source = source;
		}
		public void appendSourceTo(StringBuilder sb) {
			sb.append(this.source);
		}
	}

}
