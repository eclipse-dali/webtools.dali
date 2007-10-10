/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.IJpaDataSource;
import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaCoreFactory;
import org.eclipse.jpt.core.internal.JpaProject;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaBasic;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEmbeddable;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEmbedded;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEmbeddedId;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEntity;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaId;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaManyToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaManyToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaMappedSuperclass;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaOneToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaOneToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaTransient;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaVersion;
import org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsFactory;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public abstract class BaseJpaFactory implements IJpaFactory
{
	public IJpaProject createJpaProject(IJpaProject.Config config) throws CoreException {
		return new JpaProject(config);
	}

	public IJpaDataSource createDataSource(IJpaProject jpaProject, String connectionProfileName) {
		return JpaCoreFactory.eINSTANCE.createJpaDataSource(jpaProject, connectionProfileName);
	}

	public IJpaFile createJpaFile(IJpaProject jpaProject, IFile file, IJpaFileContentProvider provider) {
		return JpaCoreFactory.eINSTANCE.createJpaFile(jpaProject, file, provider);
	}

	public IJavaEntity createJavaEntity(Type type) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEntity(type);
	}
	
	public IJavaEmbeddable createJavaEmbeddable(Type type) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEmbeddable(type);
	}
	
	public IJavaMappedSuperclass createJavaMappedSuperclass(Type type) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaMappedSuperclass(type);
	}
		
	public IJavaBasic createJavaBasic(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaBasic(attribute);
	}
	
	public IJavaEmbedded createJavaEmbedded(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEmbedded(attribute);
	}
	
	public IJavaEmbeddedId createJavaEmbeddedId(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEmbeddedId(attribute);
	}
	
	public IJavaId createJavaId(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaId(attribute);
	}
	
	public IJavaManyToMany createJavaManyToMany(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaManyToMany(attribute);
	}
	
	public IJavaManyToOne createJavaManyToOne(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaManyToOne(attribute);
	}
	
	public IJavaOneToMany createJavaOneToMany(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaOneToMany(attribute);
	}
	
	public IJavaOneToOne createJavaOneToOne(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaOneToOne(attribute);
	}
	
	public IJavaTransient createJavaTransient(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaTransient(attribute);
	}
	
	public IJavaVersion createJavaVersion(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaVersion(attribute);
	}

}
