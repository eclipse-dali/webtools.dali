/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;


/**
 * A JpaProject corresponds to an IProject and containts of JpaFiles.
 * A JpaFile corresponds to a file resource in the project and contains
 * an IJpaRootContentNode.  Specify an IJpaFileContentProvider for each
 * file contentType to be included in the JpaProject.
 * 
 * See IJpaPlatform
 *
 */
public interface IJpaFileContentProvider
{
	/**
	 * Create the IJpaRootContentNode for the given IFile.
	 * This will be set on the corresponding JpaFile in the JpaProject.
	 * The file passed in will have a contentType that matches
	 * the one returned by the contentType() method.
	 */
	IJpaRootContentNode buildRootContent(IJpaFile jpaFile);
	
	/**
	 * Return the contentType of the file to be included in the JpaProject.
	 * This contentType should correspond to one specified through the 
	 * org.eclipse.core.runtime.contentTypes extension point.
	 * @return
	 */
	String contentType();
}
