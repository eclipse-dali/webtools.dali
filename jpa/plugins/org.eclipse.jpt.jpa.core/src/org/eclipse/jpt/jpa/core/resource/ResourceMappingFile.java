/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

/**
 * Placeholder interface.
 */
public interface ResourceMappingFile {
	/**
	 * The content of a resource mapping file.
	 */
	interface Root {
		/**
		 * The base content type for all mapping files.
		 */
		IContentType CONTENT_TYPE = JptJpaCorePlugin.instance().getContentType("mappingFile"); //$NON-NLS-1$
	}
}
