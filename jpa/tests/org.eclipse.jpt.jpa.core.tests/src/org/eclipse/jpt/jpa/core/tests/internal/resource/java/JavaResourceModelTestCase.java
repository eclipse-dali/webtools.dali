package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.utility.jdt.NullAnnotationEditFormatter;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.common.utility.CommandExecutor;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourcePackageInfoCompilationUnit;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceTypeCompilationUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackageInfoCompilationUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;


@SuppressWarnings("nls")
public class JavaResourceModelTestCase
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
			return StringTools.buildToStringFor(this);
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
	
	protected JavaResourcePackage buildJavaResourcePackage(ICompilationUnit cu) {
		JavaResourcePackageInfoCompilationUnit pkgCu = 
				new SourcePackageInfoCompilationUnit(
						cu,
						this.buildAnnotationProvider(),
						NullAnnotationEditFormatter.instance(),
						CommandExecutor.Default.instance());
		this.javaResourceCompilationUnit = pkgCu;
		return pkgCu.getPackage();
	}

	protected JavaResourcePersistentType buildJavaTypeResource(ICompilationUnit cu) {
		this.javaResourceCompilationUnit = this.buildJavaResourceCompilationUnit(cu);
		this.javaResourceCompilationUnit.resolveTypes();
		return this.hackJavaResourcePersistentType();
	}

	protected JavaResourcePersistentType hackJavaResourcePersistentType() {
		return (JavaResourcePersistentType) ReflectionTools.getFieldValue(this.javaResourceCompilationUnit, "persistentType");
	}

	protected JavaResourceCompilationUnit buildJavaResourceCompilationUnit(ICompilationUnit cu) {
		if (this.javaResourceCompilationUnit != null) {
			throw new IllegalStateException();
		}
		return new SourceTypeCompilationUnit(
			cu,
			this.buildAnnotationProvider(),
			NullAnnotationEditFormatter.instance(),
			CommandExecutor.Default.instance()
		);
	}

	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider(this.annotationDefinitionProvider());
	}

	protected JpaAnnotationDefinitionProvider annotationDefinitionProvider() {
		return GenericJpaAnnotationDefinitionProvider.instance();
	}
}
