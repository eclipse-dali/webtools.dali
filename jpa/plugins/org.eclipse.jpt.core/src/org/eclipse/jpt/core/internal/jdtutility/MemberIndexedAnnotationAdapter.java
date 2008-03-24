/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Adapt a member and an indexed declaration annotation adapter.
 */
public class MemberIndexedAnnotationAdapter
	extends AbstractAnnotationAdapter
	implements IndexedAnnotationAdapter
{
	private final IndexedDeclarationAnnotationAdapter idaa;


	// ********** constructor **********

	public MemberIndexedAnnotationAdapter(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(member, idaa);
		this.idaa = idaa;
	}


	// ********** IndexedAnnotationAdapter implementation **********

	public int index() {
		return this.idaa.getIndex();
	}

	public void moveAnnotation(int newIndex) {
		this.edit(this.buildMoveAnnotationEditor(newIndex));
	}


	// ********** factory methods **********

	protected Member.Editor buildMoveAnnotationEditor(int newIndex) {
		return new MoveAnnotationEditor(this.idaa, newIndex);
	}


	// ********** member classes **********

	protected static class MoveAnnotationEditor implements Member.Editor {
		private final IndexedDeclarationAnnotationAdapter idaa;
		private int index;

		MoveAnnotationEditor(IndexedDeclarationAnnotationAdapter idaa, int index) {
			super();
			this.idaa = idaa;
			this.index = index;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.idaa.moveAnnotation(this.index, declaration);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}

}
