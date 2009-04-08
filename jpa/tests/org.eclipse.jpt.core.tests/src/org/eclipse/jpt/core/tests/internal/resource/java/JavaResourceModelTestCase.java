/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.resource.java.source.SourceCompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.NullAnnotationEditFormatter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.StringTools;

@SuppressWarnings("nls")
public class JavaResourceModelTestCase extends AnnotationTestCase
{
	public static final String JAVAX_PERSISTENCE_PACKAGE_NAME = "javax.persistence"; //$NON-NLS-1$

	private JavaElementChangeListener javaElementChangeListener;
	protected JavaResourceCompilationUnit javaResourceCompilationUnit;
	

	public JavaResourceModelTestCase(String name) {
		super(name);
	}
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.javaProject.addJar(org.eclipse.jpt.core.tests.internal.projects.TestJpaProject.jpaJarName());
		this.javaElementChangeListener = new JavaElementChangeListener();
		JavaCore.addElementChangedListener(this.javaElementChangeListener);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		JavaCore.removeElementChangedListener(this.javaElementChangeListener);
		this.javaElementChangeListener = null;
	}
	
	/**
	 * Forward the Java element change event back to the JPA model manager.
	 */
	private class JavaElementChangeListener implements IElementChangedListener {
		JavaElementChangeListener() {
			super();
		}
		public void elementChanged(ElementChangedEvent event) {
			JavaResourceModelTestCase.this.javaElementChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}

	void javaElementChanged(ElementChangedEvent event) {
		if (this.javaResourceCompilationUnit == null) {
			return;
		}
		this.synchWithJavaDelta(event.getDelta());
	}

	/**
	 * NB: this is copied from GenericJpaProject, so it might need to be
	 * kept in synch with that code if it changes... yech...
	 */
	protected void synchWithJavaDelta(IJavaElementDelta delta) {
		switch (delta.getElement().getElementType()) {
			case IJavaElement.JAVA_MODEL :
			case IJavaElement.JAVA_PROJECT :
			case IJavaElement.PACKAGE_FRAGMENT_ROOT :
			case IJavaElement.PACKAGE_FRAGMENT :
				this.synchWithJavaDeltaChildren(delta);
				break;
			case IJavaElement.COMPILATION_UNIT :
				this.javaCompilationUnitChanged(delta);
				break;
			default :
				break; // ignore the elements inside a compilation unit
		}
	}

	protected void synchWithJavaDeltaChildren(IJavaElementDelta delta) {
		for (IJavaElementDelta child : delta.getAffectedChildren()) {
			this.synchWithJavaDelta(child); // recurse
		}
	}

	protected void javaCompilationUnitChanged(IJavaElementDelta delta) {
		if (this.deltaIsRelevant(delta)) {
			this.javaResourceCompilationUnit.update();
		}
	}

	protected boolean deltaIsRelevant(IJavaElementDelta delta) {
		if (BitTools.onlyFlagIsSet(delta.getFlags(), IJavaElementDelta.F_PRIMARY_WORKING_COPY)) {
			return false;
		}
		return delta.getKind() == IJavaElementDelta.CHANGED;
	}

	protected ICompilationUnit createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		return createAnnotationAndMembers(JAVAX_PERSISTENCE_PACKAGE_NAME, annotationName, annotationBody);
	}
	
	protected ICompilationUnit createAnnotationAndMembers(String packageName, String annotationName, String annotationBody) throws Exception {
		return this.javaProject.createCompilationUnit(packageName, annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	protected ICompilationUnit createEnumAndMembers(String enumName, String enumBody) throws Exception {
		return createEnumAndMembers(JAVAX_PERSISTENCE_PACKAGE_NAME, enumName, enumBody);
	}
	
	protected ICompilationUnit createEnumAndMembers(String packageName, String enumName, String enumBody) throws Exception {
		return this.javaProject.createCompilationUnit(packageName, enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	protected JavaResourcePersistentType buildJavaTypeResource(ICompilationUnit cu) {
		this.javaResourceCompilationUnit = this.buildJavaResourceCompilationUnit(cu);
		this.javaResourceCompilationUnit.resolveTypes();
		return this.hackJavaResourcePersistentType();
	}

	protected JavaResourcePersistentType hackJavaResourcePersistentType() {
		return (JavaResourcePersistentType) ClassTools.fieldValue(this.javaResourceCompilationUnit, "persistentType");
	}

	protected JavaResourceCompilationUnit buildJavaResourceCompilationUnit(ICompilationUnit cu) {
		if (this.javaResourceCompilationUnit != null) {
			throw new IllegalStateException();
		}
		return new SourceCompilationUnit(
			cu,
			this.buildAnnotationProvider(),
			NullAnnotationEditFormatter.instance(),
			CommandExecutor.Default.instance()
		);
	}

	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider.instance());
	}

}
