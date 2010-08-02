/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Context model corresponding to the 
 * XML resource model {@link XmlJarRef},
 * which corresponds to the <code>jar-file</code>
 * element in the <code>persistence.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JarFileRef
	extends XmlContextNode, JpaStructureNode, PersistentTypeContainer
{
	// **************** file name **********************************************
	
	/**
	 * String constant associated with changes to the file name.
	 */
	String FILE_NAME_PROPERTY = "fileName"; //$NON-NLS-1$
	
	/**
	 * Return the file name of the jar file ref.
	 */
	String getFileName();
	
	/**
	 * Set the file name of the jar file ref.
	 */
	void setFileName(String fileName);
	
	
	// **************** JAR file ***********************************************
	
	/**
	 * String constant associated with changes to the JAR file.
	 */
	String JAR_FILE_PROPERTY = "jarFile"; //$NON-NLS-1$
	
	/**
	 * Return the JAR file ref's JAR file corresponding to the file name.
	 */
	JarFile getJarFile();
	
	
	// **************** updating ***********************************************
	
	/**
	 * Update the context JAR file ref to match the specified XML JAR file ref.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update(XmlJarFileRef xmlJarFileRef);


	// **************** refactoring *********************************************

	/**
	 * Create ReplaceEdits for renaming any references to the originalFolder to the newName.
	 * The originalFolder has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createReplaceFolderEdits(IFolder originalFolder, String newName);


	// **************** queries ************************************************

	/**
	 * Return the persistent type, as listed in the JAR file,
	 * with the specified name. Return null if it does not exists.
	 */
	PersistentType getPersistentType(String typeName);
	
	/**
	 * Return whether the text representation of the JAR file ref contains
	 * the specified text offset.
	 */
	boolean containsOffset(int textOffset);
}
