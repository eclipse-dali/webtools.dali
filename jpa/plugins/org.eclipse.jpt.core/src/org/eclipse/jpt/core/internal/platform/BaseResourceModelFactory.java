/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.IResourceModelFactory;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

public class BaseResourceModelFactory implements IResourceModelFactory
{
	/*
	 * Singleton
	 */
	private static BaseResourceModelFactory INSTANCE;
	
	
	/**
	 * Return the singleton
	 */
	public static BaseResourceModelFactory instance() {
		if (INSTANCE == null) {
			INSTANCE = new BaseResourceModelFactory();
		}
		return INSTANCE;
	}
	
	
	/*
	 * Restrict access
	 */
	private BaseResourceModelFactory() {
		super();
	}
	
	
	public IResourceModel buildResourceModel(IJpaFile jpaFile) {
		IFile file = jpaFile.getFile();
		if (! JavaCore.create(file.getProject()).isOnClasspath(file)) {
			return null;
		}
		IContentType contentType = this.contentType(file);
		if (contentType == null) {
			return null;
		}
		String contentTypeId = contentType.getId();
		return buildResourceModel(jpaFile, contentTypeId);
	}
	
	protected IResourceModel buildResourceModel(IJpaFile jpaFile, String contentTypeId) {
		if (JptCorePlugin.JAVA_CONTENT_TYPE.equals(contentTypeId)) {
			return buildJavaResourceModel(jpaFile);
		}
		else if (JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildPersistenceResourceModel(jpaFile);
		}
		else if (JptCorePlugin.ORM_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildOrmResourceModel(jpaFile);
		}
		
		return null;
	}
	
	protected IResourceModel buildJavaResourceModel(IJpaFile jpaFile) {
		//TODO passing IJpaPlatform in because IJpaFile has no parent yet.
		//I believe this should change once brian's changes to remove emf from the top-level
		//model have been checked in.
		return new JavaResourceModel(jpaFile);
	}
	
	protected IResourceModel buildPersistenceResourceModel(IJpaFile jpaFile) {
		IFile resourceFile = jpaFile.getFile();
		PersistenceResourceModel resource = 
				(PersistenceResourceModel) WorkbenchResourceHelper.getResource(resourceFile, true);
		resource.accessForWrite();
		return resource;
	}
	
	protected IResourceModel buildOrmResourceModel(IJpaFile jpaFile) {
		IFile resourceFile = jpaFile.getFile();
		OrmResourceModel resource = 
				(OrmResourceModel) WorkbenchResourceHelper.getResource(resourceFile, true);
		resource.accessForWrite();
		return resource;
	}
	
	// attempting to get the contentType based on the file contents.
	// have to check the file contents instead of just the file name
	// because for xml we base it on the rootElement name
	private IContentType contentType(IFile file) {
		try {
			return Platform.getContentTypeManager().findContentTypeFor(file.getContents(), file.getName());
		}
		catch (IOException ex) {
			JptCorePlugin.log(ex);
		}
		catch (CoreException ex) {
			JptCorePlugin.log(ex);
		}
		return null;
	}
}
