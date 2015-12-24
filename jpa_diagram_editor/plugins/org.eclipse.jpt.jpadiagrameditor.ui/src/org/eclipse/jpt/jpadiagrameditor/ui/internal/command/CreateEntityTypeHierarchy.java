/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/

package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditCopier;

public class CreateEntityTypeHierarchy implements Command {

	private PersistentType subclass;
	private PersistentType superclass;
	private boolean shouldCreate;

	public CreateEntityTypeHierarchy(PersistentType superclass, PersistentType subclass, boolean shouldCreate) {
		super();
		this.superclass = superclass;
		this.subclass = subclass;
		this.shouldCreate = shouldCreate;

	}

	public void execute() {
		buildHierarchy(superclass, subclass, shouldCreate);
		JavaResourceType jrt = subclass.getJavaResourceType();
		jrt.getJavaResourceCompilationUnit().synchronizeWithJavaSource();
	}

	private void buildHierarchy(PersistentType superclass,
			PersistentType subclass, boolean build) {

		try {
			ICompilationUnit subCU = JPAEditorUtil.getCompilationUnit(subclass);

			final Document document = new Document(subCU.getBuffer()
					.getContents());
			MultiTextEdit edit = new MultiTextEdit();
			String str = document.get();			
			
			if (build) {
				int offset = str.indexOf(subclass.getSimpleName())
						+ subclass.getSimpleName().length();
				edit.addChild(new InsertEdit(offset, " extends " //$NON-NLS-1$
						+ superclass.getSimpleName()));
			} else {
				int length = ("extends " + superclass.getSimpleName() + " ") //$NON-NLS-1$ //$NON-NLS-2$
						.length();
				int offset = str.indexOf("extends"); //$NON-NLS-1$
				edit.addChild(new DeleteEdit(offset, length));
			}

			TextEditCopier copier = new TextEditCopier(edit);
			TextEdit copy = copier.perform();
			subCU.applyTextEdit(copy, new NullProgressMonitor());

		} catch (CoreException exception) {
			JPADiagramEditorPlugin.logError(exception);
		} catch (MalformedTreeException exception) {
			JPADiagramEditorPlugin.logError(exception);
		}
	}

}
