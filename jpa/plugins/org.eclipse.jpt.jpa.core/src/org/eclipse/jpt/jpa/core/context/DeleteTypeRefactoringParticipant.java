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

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.text.edits.DeleteEdit;

public interface DeleteTypeRefactoringParticipant {
	/**
	 * Create delete edits for deleting any references
	 * to the specified (about to be deleted) type.
	 * Return an empty collection if there are no references to the specified type.
	 */
	Iterable<DeleteEdit> createDeleteTypeEdits(IType type);

	class DeleteTypeEditsTransformer
		extends TransformerAdapter<DeleteTypeRefactoringParticipant, Iterable<DeleteEdit>>
	{
		protected final IType type;
		public DeleteTypeEditsTransformer(IType type) {
			super();
			this.type = type;
		}
		@Override
		public Iterable<DeleteEdit> transform(DeleteTypeRefactoringParticipant participant) {
			return participant.createDeleteTypeEdits(this.type);
		}
	}
}
