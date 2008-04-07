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
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IType;
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
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * Adapt and extend a JDT member with simplified annotation handling.
 */
public abstract class JDTMember
	implements Member
{

	private final IMember jdtMember;

	/** this will be null for a top-level type */
	private final Type declaringType;

	private final CommandExecutorProvider modifySharedDocumentCommandExecutorProvider;

	private final AnnotationEditFormatter annotationEditFormatter;

	// ********** constructor **********
	
	JDTMember(IMember jdtMember, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		this(jdtMember, modifySharedDocumentCommandExecutorProvider, DefaultAnnotationEditFormatter.instance());
	}
	
	JDTMember(IMember jdtMember, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider, AnnotationEditFormatter annotationEditFormatter) {
		super();
		this.jdtMember = jdtMember;
		IType jdtDeclaringType = jdtMember.getDeclaringType();
		this.declaringType = (jdtDeclaringType == null) ? null : new JDTType(jdtDeclaringType, modifySharedDocumentCommandExecutorProvider);
		this.modifySharedDocumentCommandExecutorProvider = modifySharedDocumentCommandExecutorProvider;
		this.annotationEditFormatter = annotationEditFormatter;
	}


	// ********** accessors **********

	protected IMember getJdtMember() {
		return this.jdtMember;
	}

	public boolean wraps(IMember member) {
		return this.jdtMember.exists()
				&& this.jdtMember.equals(member);
	}

	/**
	 * this will return null for a top-level type
	 */
	protected Type declaringType() {
		return this.declaringType;
	}


	// ********** miscellaneous **********

	protected ICompilationUnit getCompilationUnit() {
		return this.jdtMember.getCompilationUnit();
	}

	public String getName() {
		return this.jdtMember.getElementName();
	}

	/**
	 * note: this creates a *new* AST
	 */
	public CompilationUnit getAstRoot() {
		return JDTTools.buildASTRoot(this.jdtMember);
	}

	public ModifiedDeclaration getModifiedDeclaration() {
		return this.getModifiedDeclaration(this.getAstRoot());
	}

	public ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot) {
		return new JDTModifiedDeclaration(this.getBodyDeclaration(astRoot));
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName());
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
	 *     - when editing headlessly, a "working copy" must be created
	 *        (at least as far as I can tell  ~kfm)
	 *     - when editing via a plain text editor, make a working copy or else things are screwed
	 *        up the second time you edit through the XmlPersistence XmlProperties View
	 */
	private void edit_(Editor editor) throws JavaModelException, BadLocationException {
		ICompilationUnit compilationUnit = this.getCompilationUnit();
		if ( ! compilationUnit.isWorkingCopy()) {
			compilationUnit.becomeWorkingCopy(null);
		}

		ITextFileBuffer buffer = FileBuffers.getTextFileBufferManager().getTextFileBuffer(compilationUnit.getResource().getFullPath(), LocationKind.NORMALIZE);
		boolean sharedDocument = (buffer != null);  // documents are typically shared when they are already open in an editor
		IDocument doc = sharedDocument ?
				buffer.getDocument()
			:
				new Document(compilationUnit.getBuffer().getContents());

		CompilationUnit astRoot = this.getAstRoot();
		astRoot.recordModifications();

		editor.edit(this.getModifiedDeclaration(astRoot));

		TextEdit edits = astRoot.rewrite(doc, compilationUnit.getJavaProject().getOptions(true));
		if (sharedDocument) {
			this.getModifySharedDocumentCommandExecutor().execute(new ModifySharedDocumentCommand(edits, doc));
		} else {
			this.applyEdits(edits, doc);
		}

		if ( ! sharedDocument) {
			compilationUnit.getBuffer().setContents(doc.get());
			compilationUnit.commitWorkingCopy(true, null);  // true="force"
			compilationUnit.discardWorkingCopy();
		}
	}

	/**
	 * apply the specified edits to the specified document,
	 * reformatting the document if necessary
	 */
	void applyEdits(TextEdit edits, IDocument doc) throws MalformedTreeException, BadLocationException {
		edits.apply(doc, TextEdit.UPDATE_REGIONS);
		this.getAnnotationEditFormatter().format(doc, edits);
	}

	private AnnotationEditFormatter getAnnotationEditFormatter() {
		return this.annotationEditFormatter;
	}

	private CommandExecutor getModifySharedDocumentCommandExecutor() {
		return this.modifySharedDocumentCommandExecutorProvider.getCommandExecutor();
	}


	// ********** modify shared document command class **********

	/**
	 * simple command that calls back to the member to apply the edits
	 * in the same way as if the document were not shared
	 */
	class ModifySharedDocumentCommand implements Command {
		private final TextEdit edits;
		private final IDocument doc;

		ModifySharedDocumentCommand(TextEdit edits, IDocument doc) {
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
