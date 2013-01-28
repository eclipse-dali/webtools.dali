/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.text.edits.ReplaceEdit;

public interface TypeRefactoringParticipant {
	/**
	 * Create replace edits for renaming any references to
	 * the specified original type to the specified new name.
	 * The specified original type has not yet been renamed; and the specified
	 * new name is a "simple" (unqualified) name.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

	class RenameTypeEditsTransformer
		extends TransformerAdapter<TypeRefactoringParticipant, Iterable<ReplaceEdit>>
	{
		protected final IType originalType;
		protected final String newName;
		public RenameTypeEditsTransformer(IType originalType, String newName) {
			super();
			this.originalType = originalType;
			this.newName = newName;
		}
		@Override
		public Iterable<ReplaceEdit> transform(TypeRefactoringParticipant participant) {
			return participant.createRenameTypeEdits(this.originalType, this.newName);
		}
	}

	/**
	 * Create replace edits for moving any references to
	 * the specified original type to the specified new package.
	 * The specified original type has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	class MoveTypeEditsTransformer
		extends TransformerAdapter<TypeRefactoringParticipant, Iterable<ReplaceEdit>>
	{
		protected final IType originalType;
		protected final IPackageFragment newPackage;
		public MoveTypeEditsTransformer(IType originalType, IPackageFragment newPackage) {
			super();
			this.originalType = originalType;
			this.newPackage = newPackage;
		}
		@Override
		public Iterable<ReplaceEdit> transform(TypeRefactoringParticipant participant) {
			return participant.createMoveTypeEdits(this.originalType, this.newPackage);
		}
	}

	/**
	 * Create replace edits for renaming any references to
	 * the specified original package to the specified new name.
	 * The specified original package has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);

	class RenamePackageEditsTransformer
		extends TransformerAdapter<TypeRefactoringParticipant, Iterable<ReplaceEdit>>
	{
		protected final IPackageFragment originalPackage;
		protected final String newName;
		public RenamePackageEditsTransformer(IPackageFragment originalPackage, String newName) {
			super();
			this.originalPackage = originalPackage;
			this.newName = newName;
		}
		@Override
		public Iterable<ReplaceEdit> transform(TypeRefactoringParticipant participant) {
			return participant.createRenamePackageEdits(this.originalPackage, this.newName);
		}
	}
}
