/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractOrmXmlRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Used by the persistence unit for the
 * <code>META-INF/orm.xml</code> file when it is not explicitly listed
 * in the <code>persistence.xml</code> file.
 */
public class VirtualOrmXmlRef
	extends AbstractOrmXmlRef
{

	// ********** construction/initialization **********

	public VirtualOrmXmlRef(PersistenceUnit parent, String fileName) {
		super(parent, fileName);
	}


	// ********** MappingFileRef implementation **********

	public XmlMappingFileRef getXmlMappingFileRef() {
		throw new UnsupportedOperationException();
	}

	public boolean isDefault() {
		return true;
	}

	public void setFileName(String fileName) {
		throw new UnsupportedOperationException("Cannot set an implied mapping file ref's 'fileName': " + fileName); //$NON-NLS-1$
	}


	// ********** JpaStructureNode implementation **********

	public boolean containsOffset(int textOffset) {
		return false;
	}

	public TextRange getSelectionTextRange() {
		return null;
	}


	// ********** XmlContextNode implementation **********

	public TextRange getValidationTextRange() {
		return this.getPersistenceUnit().getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file) {
		throw new IllegalStateException("This reference cannot be deleted - it is implied"); //$NON-NLS-1$
	}

	public Iterable<ReplaceEdit> createRenameFolderEdits(IFolder originalFolder, String newName) {
		throw new IllegalStateException("This reference cannot be moved - it is implied"); //$NON-NLS-1$
	}

	@Override
	protected ReplaceEdit createRenameEdit(IFile originalFile, String newName) {
		return this.createReplaceEdit(this.fileName.substring(0, this.fileName.lastIndexOf('/') + 1) + newName);
	}

	@Override
	protected ReplaceEdit createMoveEdit(IFile originalFile, IPath runtimeDestination) {
		return this.createReplaceEdit(runtimeDestination.append(originalFile.getName()).toString());
	}

	protected ReplaceEdit createReplaceEdit(String newMappingFileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringTools.CR);
		sb.append("\t\t<mapping-file>"); //$NON-NLS-1$
		sb.append(newMappingFileName);
		sb.append("</mapping-file>"); //$NON-NLS-1$
		int offset = this.getPersistenceUnit().findInsertLocationForMappingFileRef();
		return new ReplaceEdit(offset, 0, sb.toString());
	}

	public Iterable<ReplaceEdit> createMoveFolderEdits(IFolder originalFolder, IPath runtimeDestination) {
		throw new IllegalStateException("This reference cannot be moved - it is implied"); //$NON-NLS-1$
	}
}
