/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * This builds and holds a "Java" project.
 * Support for adding packages and types.
 * 
 * "Java" projects aren't required to be "faceted" projects, but for JPA
 * testing they are.
 */
public class TestJavaProject extends TestFacetedProject {
	private final IJavaProject javaProject;
	private final IPackageFragmentRoot sourceFolder;


	// ********** builders *****************************
	
	public static TestJavaProject buildJavaProject(String baseProjectName, boolean autoBuild)
			throws CoreException {
		return new TestJavaProject(uniqueProjectName(baseProjectName), autoBuild);
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

	protected void addJar(String jarPath) throws JavaModelException {
		this.addClasspathEntry(JavaCore.newLibraryEntry(new Path(jarPath), null, null));
	}

	private void addClasspathEntry(IClasspathEntry entry) throws JavaModelException {
		this.javaProject.setRawClasspath(CollectionTools.add(this.javaProject.getRawClasspath(), entry), null);
	}
	

	// ********** public methods **********

	public IPackageFragment createPackage(String packageName) throws CoreException {
		return this.sourceFolder.createPackageFragment(packageName, false, null);	// false = "no force"
	}

	/**
	 * The source should NOT contain a package declaration;
	 * it will be added here.
	 */
	public IType createType(String packageName, String compilationUnitName, String source) throws CoreException {
		return this.createType(this.createPackage(packageName), compilationUnitName, new SimpleSourceWriter(source));
	}

	/**
	 * The source should NOT contain a package declaration;
	 * it will be added here.
	 */
	public IType createType(String packageName, String compilationUnitName, SourceWriter sourceWriter) throws CoreException {
		return this.createType(this.createPackage(packageName), compilationUnitName, sourceWriter);
	}
	
	/**
	 * The source should NOT contain a package declaration;
	 * it will be added here.
	 */
	public IType createType(IPackageFragment packageFragment, String compilationUnitName, SourceWriter sourceWriter) throws CoreException {
		StringBuffer sb = new StringBuffer(2000);
		sb.append("package ").append(packageFragment.getElementName()).append(";").append(CR);
		sb.append(CR);
		sourceWriter.appendSourceTo(sb);
		String source = sb.toString();
		ICompilationUnit cu = packageFragment.createCompilationUnit(compilationUnitName, source, false, null);	// false = "no force"
		return cu.findPrimaryType();
	}

	public IType findType(String fullyQualifiedName) throws JavaModelException {
		return this.javaProject.findType(fullyQualifiedName);
	}


	// ********** member classes **********

	public interface SourceWriter {
		void appendSourceTo(StringBuffer sb);
	}

	public class SimpleSourceWriter implements SourceWriter {
		private final String source;
		public SimpleSourceWriter(String source) {
			super();
			this.source = source;
		}
		public void appendSourceTo(StringBuffer sb) {
			sb.append(source);
		}
	}

}
