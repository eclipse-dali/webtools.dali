/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.ContentTypeReference;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

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
	extends JaxbNode, ContentTypeReference
{
	/**
	 * Return the JAXB file's Eclipse file.
	 */
	IFile getFile();
	
	/**
	 * Return the resource model corresponding to the JPA file; typically a JPA
	 * compilation unit, a JPA XML resource, or a JPA package fragment root (JAR).
	 */
	JptResourceModel getResourceModel();
	Transformer<JaxbFile, JptResourceModel> RESOURCE_MODEL_TRANSFORMER = new ResourceModelTransformer();
	class ResourceModelTransformer
		extends TransformerAdapter<JaxbFile, JptResourceModel>
	{
		@Override
		public JptResourceModel transform(JaxbFile jaxbFile) {
			return jaxbFile.getResourceModel();
		}
	}

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
