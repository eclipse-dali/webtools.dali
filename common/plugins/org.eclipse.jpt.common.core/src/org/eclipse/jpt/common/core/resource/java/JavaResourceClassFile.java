/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;

/**
 * Java class file
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JavaResourceClassFile
	extends JavaResourceNode
{
	/**
	 * Return the class file's type.
	 */
	JavaResourceAbstractType getType();


	// ********** content types **********

	/**
	 * The content type ID for Java class files.
	 * <p>
	 * [The JDT plug-in does not define a constant for this ID....]
	 */
	String CONTENT_TYPE_ID = JavaCore.PLUGIN_ID + ".javaClass"; //$NON-NLS-1$

	/**
	 * The content type for Java class files.
	 */
	IContentType CONTENT_TYPE = Platform.getContentTypeManager().getContentType(CONTENT_TYPE_ID);
}
