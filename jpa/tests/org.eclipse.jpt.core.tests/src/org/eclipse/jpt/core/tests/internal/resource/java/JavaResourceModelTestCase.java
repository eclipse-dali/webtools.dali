/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.SimpleJpaProjectConfig;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.NullAnnotationEditFormatter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;

public class JavaResourceModelTestCase extends AnnotationTestCase
{
	public JavaResourceModelTestCase(String name) {
		super(name);
	}

	protected IType createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		return this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	protected IType createEnumAndMembers(String enumName, String enumBody) throws Exception {
		return this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	//build up a dummy JpaProject that does not have JpaFiles in it and does not update from java changes
	protected IJpaProject buildJpaProject() throws CoreException {
		return new JpaProject(buildJpaProjectConfig(this.javaProject.getProject())) {
			@Override
			protected IResourceProxyVisitor buildInitialResourceProxyVisitor() {
				return new IResourceProxyVisitor() {
					public boolean visit(IResourceProxy proxy) throws CoreException {
						return false;
					}
				};
			}
			@Override
			public void javaElementChanged(ElementChangedEvent event) {
				//do nothing
			}
			
			@Override
			public void update() {
				//do nothing
			}
		};
	}
	
	private IJpaProject.Config buildJpaProjectConfig(IProject project) {
		
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setProject(project);
		config.setJpaPlatform(JptCorePlugin.jpaPlatform(project));
		config.setConnectionProfileName(JptCorePlugin.connectionProfileName(project));
		config.setDiscoverAnnotatedClasses(JptCorePlugin.discoverAnnotatedClasses(project));
		return config;
	}

	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) throws CoreException {
		Type type = new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER, NullAnnotationEditFormatter.instance());
		JavaPersistentTypeResource typeResource = new JavaPersistentTypeResourceImpl(buildJpaProject(), type);
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
	}

}
