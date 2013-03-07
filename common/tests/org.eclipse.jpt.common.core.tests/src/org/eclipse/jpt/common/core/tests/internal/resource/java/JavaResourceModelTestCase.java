/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.resource.java;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.GenericAnnotationProvider;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourcePackageInfoCompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceTypeCompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.NullAnnotationEditFormatter;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageInfoCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

@SuppressWarnings("nls")
public abstract class JavaResourceModelTestCase
		extends AnnotationTestCase {
	
	private JavaElementChangeListener javaElementChangeListener;
	protected JavaResourceCompilationUnit javaResourceCompilationUnit;
	
	
	public JavaResourceModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.javaElementChangeListener = new JavaElementChangeListener();
		JavaCore.addElementChangedListener(this.javaElementChangeListener);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		JavaCore.removeElementChangedListener(this.javaElementChangeListener);
		this.javaElementChangeListener = null;
	}
	
	private class JavaElementChangeListener
			implements IElementChangedListener {
		
		JavaElementChangeListener() {
			super();
		}
		
		public void elementChanged(ElementChangedEvent event) {
			JavaResourceModelTestCase.this.javaElementChanged(event);
		}
		
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
	
	void javaElementChanged(ElementChangedEvent event) {
		if (this.javaResourceCompilationUnit == null) {
			return;
		}
		this.syncWithJavaDelta(event.getDelta());
	}
	
	/**
	 * NB: this is copied from GenericJpaProject, so it might need to be
	 * kept in synch with that code if it changes... yech...
	 */
	protected void syncWithJavaDelta(IJavaElementDelta delta) {
		switch (delta.getElement().getElementType()) {
			case IJavaElement.JAVA_MODEL :
			case IJavaElement.JAVA_PROJECT :
			case IJavaElement.PACKAGE_FRAGMENT_ROOT :
			case IJavaElement.PACKAGE_FRAGMENT :
				this.syncWithJavaDeltaChildren(delta);
				break;
			case IJavaElement.COMPILATION_UNIT :
				this.javaCompilationUnitChanged(delta);
				break;
			default :
				break; // ignore the elements inside a compilation unit
		}
	}

	protected void syncWithJavaDeltaChildren(IJavaElementDelta delta) {
		for (IJavaElementDelta child : delta.getAffectedChildren()) {
			this.syncWithJavaDelta(child); // recurse
		}
	}

	protected void javaCompilationUnitChanged(IJavaElementDelta delta) {
		if (this.deltaIsRelevant(delta)) {
			this.javaResourceCompilationUnit.synchronizeWithJavaSource();
		}
	}

	protected boolean deltaIsRelevant(IJavaElementDelta delta) {
		if (BitTools.onlyFlagIsSet(delta.getFlags(), IJavaElementDelta.F_PRIMARY_WORKING_COPY)) {
			return false;
		}
		return delta.getKind() == IJavaElementDelta.CHANGED;
	}
	
	protected ICompilationUnit createAnnotationAndMembers(String packageName, String annotationName, String annotationBody) throws Exception {
		return this.javaProject.createCompilationUnit(packageName, annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	protected ICompilationUnit createEnumAndMembers(String packageName, String enumName, String enumBody) throws Exception {
		return this.javaProject.createCompilationUnit(packageName, enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}
	
	protected JavaResourcePackage buildJavaResourcePackage(ICompilationUnit cu) throws JavaModelException {
		JavaResourcePackageInfoCompilationUnit pkgCu = 
				new SourcePackageInfoCompilationUnit(
						cu,
						this.buildAndVerifyAnnotationProvider(),
						NullAnnotationEditFormatter.instance(),
						CommandContext.Default.instance());
		this.javaResourceCompilationUnit = pkgCu;
		return pkgCu.getPackage();
	}

	protected JavaResourceType buildJavaResourceType(ICompilationUnit cu) throws JavaModelException {
		return (JavaResourceType) this.buildJavaResourceType_(cu);
	}

	protected JavaResourceEnum buildJavaResourceEnum(ICompilationUnit cu) throws JavaModelException {
		return (JavaResourceEnum) this.buildJavaResourceType_(cu);
	}

	private JavaResourceAbstractType buildJavaResourceType_(ICompilationUnit cu) throws JavaModelException {
		this.javaResourceCompilationUnit = this.buildJavaResourceCompilationUnit(cu);
		return this.hackJavaResourceType();
	}

	protected JavaResourceField getField(JavaResourceType type, int index) {
		return IterableTools.get(type.getFields(), index);
	}

	protected JavaResourceMethod getMethod(JavaResourceType type, int index) {
		return IterableTools.get(type.getMethods(), index);
	}

	protected JavaResourceEnumConstant getEnumConstant(JavaResourceEnum resourceEnum, int index) {
		return IterableTools.get(resourceEnum.getEnumConstants(), index);
	}

	protected JavaResourceAbstractType hackJavaResourceType() {
		return (JavaResourceAbstractType) ObjectTools.get(this.javaResourceCompilationUnit, "primaryType");
	}

	protected JavaResourceCompilationUnit buildJavaResourceCompilationUnit(ICompilationUnit cu) throws JavaModelException  {
		if (this.javaResourceCompilationUnit != null) {
			throw new IllegalStateException();
		}
		return new SourceTypeCompilationUnit(
				cu,
				this.buildAndVerifyAnnotationProvider(),
				NullAnnotationEditFormatter.instance(),
				CommandContext.Default.instance()
		);
	}

	protected AnnotationProvider buildAndVerifyAnnotationProvider() throws JavaModelException {
		AnnotationProvider annotationProvider = this.buildAnnotationProvider();
		this.verifyAnnotationClassesExist(annotationProvider);
		return annotationProvider;
	}

	protected AnnotationProvider buildAnnotationProvider() {
		return new GenericAnnotationProvider(this.annotationDefinitions(), this.nestableAnnotationDefinitions());
	}
	
	protected abstract AnnotationDefinition[] annotationDefinitions();
	
	protected abstract NestableAnnotationDefinition[] nestableAnnotationDefinitions();


	private void verifyAnnotationClassesExist(AnnotationProvider annotationProvider) throws JavaModelException  {
		for (String annotationName : this.getAllAnnotationNames(annotationProvider)) {
			if (getJavaProject().getJavaProject().findType(annotationName) == null) {
				//if running the tests with jre 1.5, the javax.annotation.Generated class will not be found
				if (!annotationName.equals("javax.annotation.Generated")) {
					fail(errorMissingAnnotationClass(annotationName));			
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Iterable<String> getAllAnnotationNames(AnnotationProvider annotationProvider) {
		return IterableTools.concatenate(
			annotationProvider.getAnnotationNames(),
			annotationProvider.getContainerAnnotationNames(),
			annotationProvider.getNestableAnnotationNames());
	}

	/*********** private **********/
	private static String errorMissingAnnotationClass(String annotationName) {
		return "Annotation class " + annotationName + " is not on the classpath. Check the Java system property org.eclipse.jpt.jpa.jar";		
	}
}
