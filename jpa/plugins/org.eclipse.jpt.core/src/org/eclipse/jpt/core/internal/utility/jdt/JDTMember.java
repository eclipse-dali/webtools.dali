/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * Adapt and extend a JDT member with simplified annotation handling.
 */
public abstract class JDTMember
	implements Member
{
	/** this will be null for the primary type */
	private final Type declaringType;

	/** the member's name (duh) */
	private final String name;

	/**
	 * members can occur more than once in non-compiling source;
	 * count starts at 1; the primary type will have occurrence 1
	 */
	private final int occurrence;

	/**
	 * the compilation unit (file) containing the member;
	 * used for building an AST when we modify the member
	 */
	private final ICompilationUnit compilationUnit;

	/**
	 * this allows clients to provide a way to modify the compilation unit
	 * (file) when it is open in an editor and should be modified on the UI
	 * thread
	 */
	private final CommandExecutor modifySharedDocumentCommandExecutor;

	/** this will format the member's annotations a bit */
	private final AnnotationEditFormatter annotationEditFormatter;


	// ********** constructors **********
	
	protected JDTMember(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	protected JDTMember(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super();
		this.declaringType = declaringType;
		this.name = name;
		this.occurrence = occurrence;
		this.compilationUnit = compilationUnit;
		this.modifySharedDocumentCommandExecutor = modifySharedDocumentCommandExecutor;
		this.annotationEditFormatter = annotationEditFormatter;
	}


	// ********** Member implementation **********

	public ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot) {
		return new JDTModifiedDeclaration(this.getBodyDeclaration(astRoot));
	}

	public ModifiedDeclaration getModifiedDeclaration() {
		return this.getModifiedDeclaration(this.buildASTRoot());
	}

	public boolean matches(String memberName, int occur) {
		return memberName.equals(this.name) && (occur == this.occurrence);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.name);
	}


	// ********** internal **********

	protected String getName_() {
		return this.name;
	}

	protected int getOccurrence() {
		return this.occurrence;
	}

	/**
	 * this will return null for a top-level type
	 */
	protected Type getDeclaringType() {
		return this.declaringType;
	}


	// ********** editing **********

	/**
	 * Edit the member with the specified editor.
	 * The editor will be invoked once the member's compilation unit
	 * is in an editable state.
	 */
	public void edit(Editor editor) {
		try {
			this.edit_(editor);
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		} catch (BadLocationException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * NB: Be careful changing this method.
	 * Things to look out for:
	 *     - when editing via the JavaEditor there is no need to create a working copy
	 *     - when editing without an editor or via a simple text editor, a "working copy" must be created.
	 *        (at least as far as I can tell  ~kfm)
	 *     - sharedDocument is only ever false in tests (headless mode).  In the UI, even if the file
	 *        is not open in an editor, sharedDocument is still true (buffer is not null)
	 *     - if a working copy is created, then we must discard it
	 */
	protected void edit_(Editor editor) throws JavaModelException, BadLocationException {
		boolean createWorkingCopy = ! this.compilationUnit.isWorkingCopy();
		if (createWorkingCopy) {
			this.compilationUnit.becomeWorkingCopy(null);
		}

		ITextFileBuffer buffer = FileBuffers.getTextFileBufferManager().getTextFileBuffer(this.compilationUnit.getResource().getFullPath(), LocationKind.NORMALIZE);
		boolean sharedDocument = (buffer != null);  // documents are typically shared when they are already open in an editor
		IDocument doc = sharedDocument ?
				buffer.getDocument()
			:
				new Document(this.compilationUnit.getBuffer().getContents());

		try {
			CompilationUnit astRoot = this.buildASTRoot();
			astRoot.recordModifications();
	
			editor.edit(this.getModifiedDeclaration(astRoot));
	
			TextEdit edits = astRoot.rewrite(doc, this.compilationUnit.getJavaProject().getOptions(true));
			if (sharedDocument) {
				this.modifySharedDocumentCommandExecutor.execute(new ModifySharedDocumentCommand(edits, doc));
			} else {
				this.applyEdits(edits, doc);
			}
		}
		finally {
			if (createWorkingCopy) {
				//discardWorkingCopy must be called every time becomeWorkingCopy is called.
				this.compilationUnit.getBuffer().setContents(doc.get());
				this.compilationUnit.commitWorkingCopy(true, null);  // true="force"
				this.compilationUnit.discardWorkingCopy();
			}
		}
	}

	/**
	 * apply the specified edits to the specified document,
	 * reformatting the document if necessary
	 */
	protected void applyEdits(TextEdit edits, IDocument doc) throws MalformedTreeException, BadLocationException {
		edits.apply(doc, TextEdit.UPDATE_REGIONS);
		this.annotationEditFormatter.format(doc, edits);
	}

	protected CompilationUnit buildASTRoot() {
		return JDTTools.buildASTRoot(this.compilationUnit);
	}


	// ********** modify shared document command class **********

	/**
	 * simple command that calls back to the member to apply the edits
	 * in the same way as if the document were not shared
	 */
	protected class ModifySharedDocumentCommand implements Command {
		private final TextEdit edits;
		private final IDocument doc;

		protected ModifySharedDocumentCommand(TextEdit edits, IDocument doc) {
			super();
			this.edits = edits;
			this.doc = doc;
		}

		public void execute() {
			try {
				JDTMember.this.applyEdits(this.edits, this.doc);
			} catch (MalformedTreeException ex) {
				throw new RuntimeException(ex);
			} catch (BadLocationException ex) {
				throw new RuntimeException(ex);
			}
		}

	}

}
