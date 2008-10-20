/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;

/**
 * A collection of utilities for dealing with eclipse platform API 
 */
public class PlatformUtilities
{
	/**
	 * Retrieves the content type for the IFile referenced.
	 * (Makes sure to close the input stream)
	 */
	public static IContentType getContentType(IFile file) {
		InputStream inputStream = null;
		try {
			inputStream = file.getContents();
		} catch (CoreException ex) {
			JptCorePlugin.log(ex);
			return null;
		}

		IContentType contentType = null;
		try {
			contentType = Platform.getContentTypeManager().findContentTypeFor(inputStream, file.getName());
		} catch (IOException ex) {
			JptCorePlugin.log(ex);
		} finally {
			try {
				inputStream.close();
			} catch (IOException ex) {
				JptCorePlugin.log(ex);
			}
		}
		return contentType;
	}

	private PlatformUtilities() {
		super();
		throw new UnsupportedOperationException();
	}

}
