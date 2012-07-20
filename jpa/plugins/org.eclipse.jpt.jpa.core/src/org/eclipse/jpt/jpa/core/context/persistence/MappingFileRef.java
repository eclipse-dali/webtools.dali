/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.AbstractTransformer;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Context model corresponding to the
 * <code>mapping-file</code> element
 * in the <code>persistence.xml</code> file.
 * <p>
 * <strong>NB:</strong>
 * While this context model corresponds to an XML resource model, the mapping
 * file it references does <em>not</em> necessarily correspond to an XML
 * resource model.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface MappingFileRef
	extends XmlContextNode, JpaStructureNode, PersistentTypeContainer
{
	/**
	 * Covariant override.
	 */
	PersistenceUnit getParent();


	// ********** file name **********

	/**
	 * String constant associated with changes to the file name.
	 */
	String FILE_NAME_PROPERTY = "fileName"; //$NON-NLS-1$

	/**
	 * Return the file name of the mapping file ref.
	 */
	String getFileName();

	/**
	 * Set the file name of the mapping file ref.
	 */
	void setFileName(String fileName);


	// ********** mapping file (orm.xml) **********

	/**
	 * String constant associated with changes to the mapping file.
	 */
	String MAPPING_FILE_PROPERTY = "mappingFile"; //$NON-NLS-1$

	/**
	 * Return mapping file corresponding to the mapping file ref's file name.
	 * This can be <code>null</code> if the file name is invalid or if the
	 * file's content is unsupported.
	 */
	MappingFile getMappingFile();

	Transformer<MappingFileRef, MappingFile> MAPPING_FILE_TRANSFORMER = new MappingFileTransformer();

	class MappingFileTransformer
		extends AbstractTransformer<MappingFileRef, MappingFile>
	{
		@Override
		protected MappingFile transform_(MappingFileRef ref) {
			return ref.getMappingFile();
		}
	}


	// ********** persistence unit metadata **********

	/**
	 * Return the mapping file's persistence unit metadata.
	 */
	MappingFilePersistenceUnitMetadata getPersistenceUnitMetadata();

	/**
	 * Return whether the mapping file's persistence unit metadata exist.
	 */
	boolean persistenceUnitMetadataExists();


	// ********** misc **********

	/**
	 * Return the mapping file ref's corresponding XML mapping file ref.
	 */
	XmlMappingFileRef getXmlMappingFileRef();

	/**
	 * Return whether the mapping file ref is a
	 * <code>persistence.xml</code> default (as opposed to
	 * explicitly specified).
	 */
	boolean isDefault();

	/**
	 * Return whether the specified text offset is within
	 * the text representation of the mapping file.
	 */
	boolean containsOffset(int textOffset);

	/**
	 * @see MappingFile#getMappingFileQueries()
	 */
	Iterable<Query> getMappingFileQueries();

	/**
	 * @see MappingFile#getMappingFileGenerators()
	 */
	Iterable<Generator> getMappingFileGenerators();


	// ********** refactoring **********

	/**
	 * Create delete edits for deleting any references
	 * to the specified (about to be deleted) type.
	 * Return an empty collection if there are no references to the specified type.
	 */
	Iterable<DeleteEdit> createDeleteTypeEdits(IType type);

	/**
	 * Create replace edits for renaming any references to
	 * the specified original type to the specified new name.
	 * The specified original type has not yet been renamed; and the specified
	 * new name is a "simple" (unqualified) name.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

	/**
	 * Create replace edits for moving any references to
	 * the specified original type to the specified new package.
	 * The specified original type has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	/**
	 * Create replace edits for renaming any references to
	 * the specified original package to the specified new name.
	 * The specified original package has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);

	/**
	 * If the ref is for the specified file, create a text 
	 * delete edit for deleting the mapping file element and any text that precedes it
	 * in the <code>persistence.xml</code> file.
	 * Otherwise return an empty collection.
	 */
	Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file);

	/**
	 * Create replace edits for renaming any references to the specified
	 * original folder to the specified new name.
	 * The specified original folder has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenameFolderEdits(IFolder originalFolder, String newName);

	/**
	 * If the ref is for the specified file, create a text 
	 * replace edit for renaming the mapping file element to the new name.
	 * Otherwise return an empty collection.
	 */
	Iterable<ReplaceEdit> createRenameMappingFileEdits(IFile originalFile, String newName);

	/**
	 * If the ref is for the specified file, create a text replace edit for
	 * moving the specified original file to the specified destination.
	 * Otherwise return an empty collection.
	 * The specified original file has not been moved yet.
	 */
	Iterable<ReplaceEdit> createMoveMappingFileEdits(IFile originalFile, IPath destination);

	/**
	 * Create replace edits for moving any references to the specified
	 * original folder to the specified destination.
	 * The specified destination already includes the original folder name.
	 */
	Iterable<ReplaceEdit> createMoveFolderEdits(IFolder originalFolder, IPath destination);
}
