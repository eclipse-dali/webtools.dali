/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;

/**
 * {@link IContentType} utility methods
 */
public class ContentTypeTools {


	// ********** resource type **********

	/**
	 * Return the resource type corresponding to the specified content type.
	 */
	public static JptResourceType getResourceType(IContentType contentType) {
		JptWorkspace jptWorkspace = getJptWorkspace();
		return (jptWorkspace == null) ? null : jptWorkspace.getResourceTypeManager().getResourceType(contentType);
	}

	/**
	 * Return the resource type corresponding to the specified content type
	 * and version.
	 */
	public static JptResourceType getResourceType(IContentType contentType, String version) {
		JptWorkspace jptWorkspace = getJptWorkspace();
		return (jptWorkspace == null) ? null : jptWorkspace.getResourceTypeManager().getResourceType(contentType, version);
	}

	private static JptWorkspace getJptWorkspace() {
		return PlatformTools.getAdapter(PathTools.getWorkspace(), JptWorkspace.class);
	}


	// ********** file content type **********

	/**
	 * Return the specified file's content type,
	 * using the Eclipse platform's content type manager.
	 */
	public static IContentType contentType(IFile file) {
		String fileName = file.getName();
		InputStream fileContents = null;
		try {
			fileContents = file.getContents();
		} catch (CoreException ex) {
			// seems like we can ignore any exception that might occur here;
			// e.g. we get a FNFE if the workspace is out of sync with the O/S file system
			// JptCorePlugin.log(ex);
	
			// look for content type based on the file name only(?)
			return findContentTypeFor(fileName);
		}
	
		IContentType contentType = null;
		try {
			contentType = findContentTypeFor(fileContents, fileName);
		} catch (IOException ex) {
			JptCommonCorePlugin.instance().logError(ex);
		} finally {
			try {
				fileContents.close();
			} catch (IOException ex) {
				JptCommonCorePlugin.instance().logError(ex);
			}
		}
		return contentType;
	}

	private static IContentType findContentTypeFor(InputStream fileContents, String fileName) throws IOException {
		return getContentTypeManager().findContentTypeFor(fileContents, fileName);
	}

	private static IContentType findContentTypeFor(String fileName) {
		return getContentTypeManager().findContentTypeFor(fileName);
	}

	private static IContentTypeManager getContentTypeManager() {
		return Platform.getContentTypeManager();
	}


	// ********** disabled constructor **********

	private ContentTypeTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
