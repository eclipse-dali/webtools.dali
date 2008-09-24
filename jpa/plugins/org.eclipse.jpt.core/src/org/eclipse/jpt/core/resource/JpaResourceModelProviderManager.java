/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.resource;

import java.io.IOException;
import java.util.HashMap;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.internal.resource.JpaResourceModelProviderRegistry;
import org.eclipse.jpt.core.utility.PlatformUtilities;

public class JpaResourceModelProviderManager
{
	// key: file content type, value: model provider factory
	private static HashMap<String, JpaResourceModelProviderFactory> factories;
	
	
	/**
	 * Returns a model provider for the given file, based on its content type, 
	 * if there is one registered for that content type, or one of its base
	 * content types if no model provider is registered for that content type.
	 * 
	 * @param file - The file the model represents 
	 * @return the model provider for the file
	 * @throws CoreException if an error occurs while retrieving the file contents
	 * @throws IOException if an error occurs while reading the file contents 
	 */
	public static JpaResourceModelProvider getModelProvider(IFile file) 
			throws CoreException, IOException {
		IProject project = file.getProject();
		IPath path = file.getProjectRelativePath();
		IContentType contentType = PlatformUtilities.contentType(file);
		if (contentType == null) {
			return null;
		}
		JpaResourceModelProvider modelProvider = null;
		while (modelProvider == null && contentType != null) {
			modelProvider = getModelProvider(project, path, contentType.getId());
			contentType = contentType.getBaseType();
		}
		return modelProvider;
	}
	
	/**
	 * Returns a model provider for the given file path of the given content type
	 * in the given project.
	 * @param project - The project in which the file path exists
	 * @param filePath - The path of the file for which the model provider should 
	 * 	be created
	 * @param contentType - The content type for which to create a 
	 * @return the model provider for the file
	 */
	public static JpaResourceModelProvider getModelProvider(
			IProject project, IPath filePath, String fileContentType) {
		JpaResourceModelProviderFactory factory = getFactory(fileContentType);
		if (factory != null) {
			return factory.create(project, filePath);
		}
		return null;
	}
	
	private static JpaResourceModelProviderFactory getFactory(String fileContentType) {
		if (factories == null) {
			factories = new HashMap<String, JpaResourceModelProviderFactory>();
		}
		if (factories.containsKey(fileContentType)) {
			return factories.get(fileContentType);
		}
		JpaResourceModelProviderFactory factory = 
			JpaResourceModelProviderRegistry.instance().getResourceModelProviderFactory(fileContentType);
		if (factory != null) {
			factories.put(fileContentType, factory);
		}
		return factory;
	}
}
