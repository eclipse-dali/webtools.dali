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
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.text.edits.DeleteEdit;

/**
 * Used by the persistence unit for the
 * <code>META-INF/orm.xml</code> file when it is not explicitly listed
 * in the <code>persistence.xml</code> file.
 */
public class ImpliedMappingFileRef
	extends AbstractMappingFileRef
{

	// ********** construction/initialization **********

	public ImpliedMappingFileRef(PersistenceUnit parent, String resourceFileName) {
		super(parent, resourceFileName);
	}


	// ********** MappingFileRef implementation **********

	public boolean isImplied() {
		return true;
	}

	public void setFileName(String fileName) {
		throw new UnsupportedOperationException("Cannot set an implied mapping file ref's 'fileName': " + fileName); //$NON-NLS-1$
	}

	public boolean containsOffset(int textOffset) {
		return false;
	}

	public void update(XmlMappingFileRef mappingFileRef) {
		if (mappingFileRef != null) {
			throw new IllegalArgumentException("an implied mapping file ref's xml mapping file ref must be null: " + mappingFileRef); //$NON-NLS-1$
		}
		this.update();
	}


	// ********** JpaStructureNode implementation **********

	public TextRange getSelectionTextRange() {
		return null;
	}


	// ********** XmlContextNode implementation **********

	public TextRange getValidationTextRange() {
		return this.getPersistenceUnit().getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file) {
		throw new IllegalStateException("Cannot delete this reference since it is implied"); //$NON-NLS-1$
	}
}
