/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;

/**
 * A JAXB Project contains JAXB files for all files in the project that
 * are relevant to the JAXB spec.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbFile
	extends JaxbNode
{
	/**
	 * Return the JAXB file's Eclipse file.
	 */
	IFile getFile();
	
	/**JAXB
	 * Return the JPA file's content type.
	 */
	IContentType getContentType();
	
	/**
	 * Return the resource model corresponding to the JPA file; typically a JPA
	 * compilation unit, a JPA XML resource, or a JPA package fragment root (JAR).
	 */
	JptResourceModel getResourceModel();
	
	/**
	 * Convenience method. Return the resource model corresponding to the JPA
	 * file if the file's content is a "kind-of" the specified content type;
	 * otherwise, return null. This is useful when a client has looked up the
	 * JPA file via a file name [and assumed content type].
	 * @see #getResourceModel()
	 */
	JptResourceModel getResourceModel(IContentType contentType);
	
	
	// ********** root structure nodes **********

//	/**
//	 * Return the JPA file's root structure nodes.
//	 */
//	Iterator<JpaStructureNode> rootStructureNodes();
//		String ROOT_STRUCTURE_NODES_COLLECTION = "rootStructureNodes"; //$NON-NLS-1$
//
//	/**
//	 * Return the count of the JPA file's root context model objects.
//	 */
//	int rootStructureNodesSize();
//
//	/**
//	 * Add a root structure node.
//	 * There is the potential for multiple root structure nodes 
//	 * for a particular key. For example, a Java file that is listed
//	 * both as a <class> in the persistence.xml and as an <entity> in
//	 * an orm.xml file. In this case the orm.xml file needs to set
//	 * the root structure node after the Java class reference.
//	 * Last one in during project "update" wins.
//	 */
//	void addRootStructureNode(Object key, JpaStructureNode rootStructureNode);
//
//	/**
//	 * @see #addRootStructureNode(Object, JpaStructureNode)
//	 */
//	void removeRootStructureNode(Object key);
//
//	/**
//	 * Return the structure node best corresponding to the location in the file.
//	 */
//	JpaStructureNode getStructureNode(int textOffset);

}
