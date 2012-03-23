/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;

/**
 * A JPA Project contains JPA files for all files in the project that
 * are relevant to the JPA spec.
 * <p>
 * To retrieve the JPA file corresponding to an Eclipse file:
 * <pre>
 * IFile file = (IFile) ResourcesPlugin.getWorkspace().getRoot().findMember("Foo.java");
 * JpaFile jpaFile = (JpaFile) file.getAdapter(JpaFile.class);
 * </pre>
 * This is a non-blocking call; and as a result it will return <code>null</code>
 * if the JPA file or its JPA project is currently under construction. Use a
 * {@link Reference JPA file reference} to retrieve a JPA file in a blocking
 * fashion that will return a JPA file once it and its JPA project have been
 * constructed.
 * <p>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 * 
 * @see Reference
 * @see org.eclipse.jpt.jpa.core.internal.FileAdapterFactory
 */
public interface JpaFile
	extends JpaNode
{
	/**
	 * Return the JPA file's Eclipse file.
	 */
	IFile getFile();
	
	/**
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

	/**
	 * Return the JPA file's root structure nodes.
	 */
	Iterable<JpaStructureNode> getRootStructureNodes();
		String ROOT_STRUCTURE_NODES_COLLECTION = "rootStructureNodes"; //$NON-NLS-1$

	/**
	 * Return the count of the JPA file's root context model objects.
	 */
	int getRootStructureNodesSize();

	/**
	 * Add a root structure node.
	 * There is the potential for multiple root structure nodes 
	 * for a particular key. For example, a Java type can be listed
	 * both as a {@code <class>} in the <code>persistence.xml</code> file
	 * and as an {@code <entity>} in
	 * an <code>orm.xml</code> file. In this case, the Jave type in
	 * the <code>orm.xml</code> file must set
	 * the root structure node <em>after</em> the Java type in the
	 * <code>persistence.xml</code> file.
	 * Last one in during project <em>update</em> wins.
	 */
	void addRootStructureNode(Object key, JpaStructureNode rootStructureNode);

	/**
	 * Remove the root structure node for the specified key if its current value
	 * is the same as the specified node.
	 * 
	 * @see #addRootStructureNode(Object, JpaStructureNode)
	 */
	void removeRootStructureNode(Object key, JpaStructureNode rootStructureNode);

	/**
	 * Return the structure node best corresponding to the location in the file.
	 */
	JpaStructureNode getStructureNode(int textOffset);


	// ********** reference **********

	/**
	 * Standard adapter for retrieving a {@link JpaFile JPA file}:
	 * <pre>
	 * IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Foo Project");
	 * IFile file = project.getFile("plugin.xml");
	 * JpaFile.Reference jpaFileRef = (JpaFile.Reference) file.getAdapter(JpaFile.Reference.class);
	 * JpaFile jpaFile = jpaFileRef.getValue();
	 * </pre>
	 * See <code>org.eclipse.jpt.jpa.core/plugin.xml</code>.
	 * @see org.eclipse.jpt.jpa.core.internal.FileAdapterFactory
	 */
	interface Reference {
		/**
		 * Return the JPA file corresponding to the reference's Eclipse file,
		 * or <code>null</code> if unable to associate the specified file with a
		 * JPA file. This method can be long-running.
		 */
		JpaFile getValue() throws InterruptedException;
	}
}
