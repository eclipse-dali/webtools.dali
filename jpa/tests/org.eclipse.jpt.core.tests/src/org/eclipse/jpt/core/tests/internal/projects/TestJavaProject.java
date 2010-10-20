/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.utility.internal.ArrayTools;

/**
 * This builds and holds a "Java" project.
 * Support for adding packages and types.
 * 
 * "Java" projects aren't required to be "faceted" projects, but for JPA
 * testing they are.
 */
@SuppressWarnings("nls")
public class TestJavaProject extends TestFacetedProject {
	private final IJavaProject javaProject;
	private final IPackageFragmentRoot sourceFolder;


	// ********** builders *****************************
	
	public static TestJavaProject buildJavaProject(String baseProjectName, boolean autoBuild)
			throws CoreException {
		return new TestJavaProject(baseProjectName, autoBuild);
	}
	
	
	// ********** constructors/initialization **********

	public TestJavaProject(String projectName) throws CoreException {
		this(projectName, false);
	}

	public TestJavaProject(String projectName, boolean autoBuild) throws CoreException {
		super(projectName, autoBuild);
		this.installFacet("jst.java", "5.0");
		this.javaProject = JavaCore.create(this.getProject());
		this.sourceFolder = this.javaProject.getPackageFragmentRoot(this.getProject().getFolder("src"));
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
			sb.append(source);
		}
	}

}
