/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.internal.GenericJpaProject;
import org.eclipse.jpt.core.internal.SimpleJpaProjectConfig;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.utility.jdt.NullAnnotationEditFormatter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.StringTools;

public class JavaResourceModelTestCase extends AnnotationTestCase
{
	private JavaElementChangeListener javaElementChangeListener;
	protected JavaResourceModel javaResourceModel;
	

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
	
	/**
	 * Forward the Java element change event back to the JPA model manager.
	 */
	private class JavaElementChangeListener implements IElementChangedListener {
		JavaElementChangeListener() {
			super();
		}
		public void elementChanged(ElementChangedEvent event) {
			if (JavaResourceModelTestCase.this.javaResourceModel != null) {
				JavaResourceModelTestCase.this.javaResourceModel.javaElementChanged(event);
			}
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}

	protected IType createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		return this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	protected IType createEnumAndMembers(String enumName, String enumBody) throws Exception {
		return this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	//build up a dummy JpaProject that does not have JpaFiles in it and does not update from java changes
	protected JpaProject buildJpaProject() throws CoreException {
		return new TestJpaProject(this.buildJpaProjectConfig(this.javaProject.getProject()));
	}

	protected class TestJpaProject extends GenericJpaProject {
		protected TestJpaProject(JpaProject.Config config) throws CoreException {
			super(config);
			this.setUpdater(Updater.Null.instance());// ignore all updates, since there is no context model
		}

		@Override
		protected IResourceProxyVisitor buildInitialResourceProxyVisitor() {
			return new IResourceProxyVisitor() {
				public boolean visit(IResourceProxy proxy) throws CoreException {
					return false;  // ignore all the files in the Eclipse project
				}
			};
		}
		
		@Override
		protected JpaRootContextNode buildRootContextNode() {
			return null;  // no root context
		}
	}

	private JpaProject.Config buildJpaProjectConfig(IProject project) {
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setProject(project);
		config.setJpaPlatform(JptCorePlugin.jpaPlatform(project));
		config.setConnectionProfileName(JptCorePlugin.connectionProfileName(project));
		config.setDiscoverAnnotatedClasses(JptCorePlugin.discoverAnnotatedClasses(project));
		return config;
	}

	protected JavaResourcePersistentType buildJavaTypeResource(IType testType) 
		throws CoreException {
		this.javaResourceModel = buildJavaResourceModel(testType);
		this.javaResourceModel.resolveTypes();
		return this.javaResourceModel.resource().getPersistentType();
	}	
	
	protected JavaResourceModel buildJavaResourceModel(IType testType) throws CoreException {
		if (this.javaResourceModel != null) {
			throw new IllegalStateException();
		}
		IFile file = (IFile) testType.getResource();
		JpaProject jpaProject = buildJpaProject();
		return new JavaResourceModel(
			file, 
			jpaProject.jpaPlatform().annotationProvider(),
			MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER,
			NullAnnotationEditFormatter.instance());
	}

}
