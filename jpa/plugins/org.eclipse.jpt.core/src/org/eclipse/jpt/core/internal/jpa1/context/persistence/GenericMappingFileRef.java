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
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.utility.TextRange;
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
	protected XmlMappingFileRef xmlMappingFileRef;


	// ********** construction/initialization **********

	public GenericMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		super(parent, xmlMappingFileRef.getFileName());
		this.xmlMappingFileRef = xmlMappingFileRef;
	}


	// ********** file name **********

	public void setFileName(String fileName) {
		String old = this.fileName;
		this.fileName = fileName;
		this.xmlMappingFileRef.setFileName(fileName);
		this.firePropertyChanged(FILE_NAME_PROPERTY, old, fileName);
	}

	protected void setFileName_(String newFileName) {
		String old = this.fileName;
		this.fileName = newFileName;
		this.firePropertyChanged(FILE_NAME_PROPERTY, old, newFileName);
	}


	// ********** MappingFileRef implementation **********

	public void update(XmlMappingFileRef mappingFileRef) {
		this.xmlMappingFileRef = mappingFileRef;
		this.setFileName_(mappingFileRef.getFileName());
		this.update();
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


	// ********** XmlContextNode implementation **********

	public TextRange getValidationTextRange() {
		return this.xmlMappingFileRef.getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file) {
		if (this.isFor(file)) {
			return new SingleElementIterable<DeleteEdit>(this.createDeleteEdit());
		}
		return EmptyIterable.instance();
	}

	protected DeleteEdit createDeleteEdit() {
		return this.xmlMappingFileRef.createDeleteEdit();
	}

	@Override
	protected ReplaceEdit createReplaceEdit(IFile originalFile, String newName) {
		return this.xmlMappingFileRef.createReplaceEdit(originalFile, newName);
	}

	public Iterable<ReplaceEdit> createReplaceFolderEdits(IFolder originalFolder, String newName) {
		if (this.isIn(originalFolder)) {
			return new SingleElementIterable<ReplaceEdit>(this.createReplaceFolderEdit(originalFolder, newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createReplaceFolderEdit(IFolder originalFolder, String newName) {
		return this.xmlMappingFileRef.createReplaceFolderEdit(originalFolder, newName);
	}

}