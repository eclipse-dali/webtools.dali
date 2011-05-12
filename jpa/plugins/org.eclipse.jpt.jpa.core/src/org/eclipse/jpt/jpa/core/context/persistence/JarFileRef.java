/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJarFileRef;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Context model corresponding to the
 * XML resource model {@link XmlJarFileRef},
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
	// ********** file name **********

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


	// ********** JAR file **********

	/**
	 * String constant associated with changes to the JAR file.
	 */
	String JAR_FILE_PROPERTY = "jarFile"; //$NON-NLS-1$

	/**
	 * Return the JAR file ref's JAR file corresponding to the file name.
	 */
	JarFile getJarFile();


	// ********** refactoring **********

	/**
	 * Create ReplaceEdits for renaming any references to the originalFolder to the newName.
	 * The originalFolder has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createReplaceFolderEdits(IFolder originalFolder, String newName);


	// ********** misc **********

	XmlJarFileRef getXmlJarFileRef();

	/**
	 * Return whether the text representation of the JAR file ref contains
	 * the specified text offset.
	 */
	boolean containsOffset(int textOffset);
}
