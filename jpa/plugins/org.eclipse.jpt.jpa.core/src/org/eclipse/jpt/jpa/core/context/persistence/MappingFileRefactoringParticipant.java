/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

public interface MappingFileRefactoringParticipant {
	/**
	 * Create delete edits for deleting any references to the specified file
	 * Return an empty collection if there are no references to the specified file.
	 */
	Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file);

	class DeleteMappingFileEditsTransformer
		extends TransformerAdapter<MappingFileRefactoringParticipant, Iterable<DeleteEdit>>
	{
		protected final IFile file;
		public DeleteMappingFileEditsTransformer(IFile file) {
			super();
			this.file = file;
		}
		@Override
		public Iterable<DeleteEdit> transform(MappingFileRefactoringParticipant participant) {
			return participant.createDeleteMappingFileEdits(this.file);
		}
	}

	/**
	 * Create replace edits for renaming any references to the specified
	 * original folder to the specified new name.
	 * The specified original folder has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenameFolderEdits(IFolder originalFolder, String newName);

	class RenameFolderEditsTransformer
		extends TransformerAdapter<MappingFileRefactoringParticipant, Iterable<ReplaceEdit>>
	{
		protected final IFolder originalFolder;
		protected final String newName;
		public RenameFolderEditsTransformer(IFolder originalFolder, String newName) {
			super();
			this.originalFolder = originalFolder;
			this.newName = newName;
		}
		@Override
		public Iterable<ReplaceEdit> transform(MappingFileRefactoringParticipant participant) {
			return participant.createRenameFolderEdits(this.originalFolder, this.newName);
		}
	}

	/**
	 * Create replace edits for renaming any references to the specifie original
	 * file to the specified new name.
	 * Return an empty collection if there are not any references.
	 * The specified original file has not yet been renamed; and the specified
	 * new name is a "simple" (unqualified) name.
	 */
	Iterable<ReplaceEdit> createRenameMappingFileEdits(IFile originalFile, String newName);

	class RenameMappingFileEditsTransformer
		extends TransformerAdapter<MappingFileRefactoringParticipant, Iterable<ReplaceEdit>>
	{
		protected final IFile originalFile;
		protected final String newName;
		public RenameMappingFileEditsTransformer(IFile originalFile, String newName) {
			super();
			this.originalFile = originalFile;
			this.newName = newName;
		}
		@Override
		public Iterable<ReplaceEdit> transform(MappingFileRefactoringParticipant participant) {
			return participant.createRenameMappingFileEdits(this.originalFile, this.newName);
		}
	}

	/**
	 * Create replace edits for moving any references to the specified
	 * original file to the specified destination.
	 * Return an empty collection if there are not any references.
	 * The specified original file has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveMappingFileEdits(IFile originalFile, IPath destination);

	class MoveMappingFileEditsTransformer
		extends TransformerAdapter<MappingFileRefactoringParticipant, Iterable<ReplaceEdit>>
	{
		protected final IFile originalFile;
		protected final IPath destination;
		public MoveMappingFileEditsTransformer(IFile originalFile, IPath destination) {
			super();
			this.originalFile = originalFile;
			this.destination = destination;
		}
		@Override
		public Iterable<ReplaceEdit> transform(MappingFileRefactoringParticipant participant) {
			return participant.createMoveMappingFileEdits(this.originalFile, this.destination);
		}
	}

	/**
	 * Create replace edits for moving any references to the specified
	 * original folder to the specified destination.
	 * The specified destination already includes the original folder name.
	 */
	Iterable<ReplaceEdit> createMoveFolderEdits(IFolder originalFolder, IPath destination);

	class MoveFolderEditsTransformer
		extends TransformerAdapter<MappingFileRefactoringParticipant, Iterable<ReplaceEdit>>
	{
		protected final IFolder originalFolder;
		protected final IPath destination;
		public MoveFolderEditsTransformer(IFolder originalFolder, IPath destination) {
			super();
			this.originalFolder = originalFolder;
			this.destination = destination;
		}
		@Override
		public Iterable<ReplaceEdit> transform(MappingFileRefactoringParticipant participant) {
			return participant.createMoveFolderEdits(this.originalFolder, this.destination);
		}
	}
}
