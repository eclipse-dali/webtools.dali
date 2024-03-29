/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractOrmXmlRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>mapping-file</code> element
 */
public class GenericOrmXmlRef
	extends AbstractOrmXmlRef
{
	protected final XmlMappingFileRef xmlMappingFileRef;


	public GenericOrmXmlRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		super(parent, xmlMappingFileRef.getFileName());
		this.xmlMappingFileRef = xmlMappingFileRef;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		// set the file name *before* calling super
		this.setFileName_(this.xmlMappingFileRef.getFileName());
		super.synchronizeWithResourceModel(monitor);
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
			this.setMappingFile(null);
		}
	}


	// ********** misc **********

	public XmlMappingFileRef getXmlMappingFileRef() {
		return this.xmlMappingFileRef;
	}

	public boolean isDefault() {
		return false;
	}


	// ********** JpaStructureNode implementation **********

	public TextRange getFullTextRange() {
		return this.xmlMappingFileRef.getFullTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.xmlMappingFileRef.containsOffset(textOffset);
	}

	public TextRange getSelectionTextRange() {
		return this.xmlMappingFileRef.getFileNameTextRange();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getPersistenceUnit().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		return this.xmlMappingFileRef.getFileNameTextRange();
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
		ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
		IPath originalLocation = locator.getRuntimePath(fullPath);
		return this.createMoveEdit(originalLocation, runtimeDestination);
	}

	protected ReplaceEdit createMoveEdit(IPath originalLocation, IPath runtineDestination) {
		return this.xmlMappingFileRef.createMoveEdit(originalLocation, runtineDestination);
	}
}
