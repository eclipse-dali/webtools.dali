/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.tests.internal.JaxbTestCase;

@SuppressWarnings("nls")
public abstract class JaxbContextModelTestCase
		extends JaxbTestCase {
	
	protected static final String BASE_PROJECT_NAME = "JaxbContextModelTestProject";
	
	
	protected JaxbContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJaxbProject(BASE_PROJECT_NAME, autoBuild, buildJaxbFacetInstallConfig());
	}
	
	protected JaxbContextRoot getContextRoot() {
		return this.getJaxbProject().getContextRoot();
	}
	
	protected AnnotatedElement annotatedElement(JavaResourceAnnotatedElement resource) {
		return (AnnotatedElement) ReflectionTools.getFieldValue(resource, "annotatedElement");
	}
	
	protected ICompilationUnit createUnannotatedPackageInfo(String packageName) throws CoreException {
		return createTestPackageInfo(packageName);
	}
	
	protected ICompilationUnit createAnnotatedPersistentClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
		});
	}
	
	protected ICompilationUnit createUnannotatedClassNamed(String typeName) throws Exception {
		return this.createTestType(PACKAGE_NAME, typeName + ".java", typeName, new DefaultAnnotationWriter());
	}
	
	protected ICompilationUnit createAnnotatedPersistentEnum() throws Exception {
		return this.createTestEnum(new DefaultEnumAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendEnumAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
		});
	}
	
	protected ICompilationUnit createUnannotatedEnumNamed(String enumName) throws Exception {
		return this.createTestEnum(PACKAGE_NAME, enumName + ".java", enumName, new DefaultEnumAnnotationWriter());
	}
	
	protected ICompilationUnit createAnnotatedRegistry() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_REGISTRY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRegistry");
			}
		});
	}
}
