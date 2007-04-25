/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;

public class JpaFileContentRegistry
{	
	private JpaFileContentRegistry() {
		super();
	}
		
	public static IJpaFile getFile(IJpaProject jpaProject, IFile file) {
		//attempting to get the contentType based on the file contents.
		//have to check the file contents instead of just the file name
		//because for xml we base it on the rootElement name
		IContentType contentType = null;
		try {
			contentType = (file == null) ?
				null :
				Platform.getContentTypeManager().findContentTypeFor(file.getContents(), file.getName());
		}
		catch (IOException e) {
			JpaCorePlugin.log(e);
		}
		catch (CoreException e) {
			JpaCorePlugin.log(e);
		}
		if (contentType == null) {
			return null;
		}
		String contentTypeId = contentType.getId();
		IJpaRootContentNode content = buildContent(jpaProject, file, contentTypeId);

		if (content == null) {
			return null;
		}
		JpaFile jpaFile = JpaCoreFactory.eINSTANCE.createJpaFile();
		jpaFile.setFile(file);
		jpaFile.setContentId(contentTypeId);
		jpaFile.setContent(content);
		
		return jpaFile;
	}
	
	private static IJpaRootContentNode buildContent(IJpaProject jpaProject, IFile file, String contentTypeId) {
		for (IJpaFileContentProvider provider : jpaProject.getPlatform().jpaFileContentProviders()) {
			if (provider.contentType().equals(contentTypeId)) {
				return provider.buildRootContent(file);
			}
		}		
		return null;
	}
}
