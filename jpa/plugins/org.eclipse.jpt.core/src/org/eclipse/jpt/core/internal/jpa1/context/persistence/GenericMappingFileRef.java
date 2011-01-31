/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>mapping-file</code> element
 */
public class GenericMappingFileRef
	extends AbstractMappingFileRef
{
	protected final XmlMappingFileRef xmlMappingFileRef;


	public GenericMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		super(parent, xmlMappingFileRef.getFileName());
		this.xmlMappingFileRef = xmlMappingFileRef;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		// set the file name *before* calling super
		this.setFileName_(this.xmlMappingFileRef.getFileName());
		super.synchronizeWithResourceModel();
	}


	// ********** file name **********

	public void setFileName(String fileName) {
		this.setFileName_(fileName);
		this.xmlMappingFileRef.setFileName(fileName);
	}

	protected void setFileName_(String xmlFileName) {
		String old = this.fileName;
		this.fileName = xmlFileName;
		if (this.firePropertyChanged(FILE_NAME_PROPERTY, old, xmlFileName)) {
			if (this.mappingFile != null) {
				this.mappingFile.dispose();
				this.setMappingFile(null);
			}
		}
	}


	// ********** misc **********

	public XmlMappingFileRef getXmlMappingFileRef() {
		return this.xmlMappingFileRef;
	}

	public boolean isImplied() {
		return false;
	}

	public boolean containsOffset(int textOffset) {
		return this.xmlMappingFileRef.containsOffset(textOffset);
	}


	// ********** JpaStructureNode implementation **********

	public TextRange getSelectionTextRange() {
		return this.xmlMappingFileRef.getSelectionTextRange();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.xmlMappingFileRef.getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file) {
		return this.isFor(file) ?
				new SingleElementIterable<DeleteEdit>(this.createDeleteEdit()) :
				EmptyIterable.<DeleteEdit>instance();
	}

	protected DeleteEdit createDeleteEdit() {
		return this.xmlMappingFileRef.createDeleteEdit();
	}

	@Override
	protected ReplaceEdit createRenameEdit(IFile originalFile, String newName) {
		return this.xmlMappingFileRef.createRenameEdit(originalFile, newName);
	}

	public Iterable<ReplaceEdit> createRenameFolderEdits(IFolder originalFolder, String newName) {
		return this.isIn(originalFolder) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameFolderEdit(originalFolder, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameFolderEdit(IFolder originalFolder, String newName) {
		return this.xmlMappingFileRef.createRenameFolderEdit(originalFolder, newName);
	}

	@Override
	protected ReplaceEdit createMoveEdit(IFile originalFile, IPath runtineDestination) {
		return this.xmlMappingFileRef.createMoveEdit(originalFile, runtineDestination);
	}

	public Iterable<ReplaceEdit> createMoveFolderEdits(IFolder originalFolder, IPath runtimeDestination) {
		return this.isIn(originalFolder) ?
				new SingleElementIterable<ReplaceEdit>(this.createMoveEdit(originalFolder, runtimeDestination)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createMoveEdit(IFolder originalFolder, IPath runtimeDestination) {
		IProject project = originalFolder.getProject();
		IPath fullPath = originalFolder.getFullPath();
		IPath originalLocation = JptCommonCorePlugin.getResourceLocator(project).getRuntimePath(project, fullPath);
		return this.createMoveEdit(originalLocation, runtimeDestination);
	}

	protected ReplaceEdit createMoveEdit(IPath originalLocation, IPath runtineDestination) {
		return this.xmlMappingFileRef.createMoveEdit(originalLocation, runtineDestination);
	}
}
