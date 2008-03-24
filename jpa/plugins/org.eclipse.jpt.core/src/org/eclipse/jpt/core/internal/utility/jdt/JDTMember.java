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
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jpt.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
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

	public IMember getJdtMember() {
		return this.jdtMember;
	}

	public boolean wraps(IMember member) {
		return this.jdtMember.exists()
				&& this.jdtMember.equals(member);
	}

	/**
	 * this will return null for a top-level type
	 */
	public Type getDeclaringType() {
		return this.declaringType;
	}


	// ********** miscellaneous **********

	public ICompilationUnit compilationUnit() {
		return this.jdtMember.getCompilationUnit();
	}

	public String getName() {
		return this.jdtMember.getElementName();
	}

	public Type topLevelDeclaringType() {
		return this.declaringType.topLevelDeclaringType();
	}

	/**
	 * note: this creates a *new* AST
	 */
	public CompilationUnit astRoot() {
		return JDTTools.buildASTRoot(this.jdtMember);
	}

	/**
	 * note: this creates a *new* AST
	 */
	private CompilationUnit lightweightASTRoot() {
		return JDTTools.buildLightweightASTRoot(this.jdtMember);
	}

	public ModifiedDeclaration modifiedDeclaration() {
		return this.modifiedDeclaration(this.astRoot());
	}

	public ModifiedDeclaration modifiedDeclaration(CompilationUnit astRoot) {
		return new JDTModifiedDeclaration(this.bodyDeclaration(astRoot));
	}

	public TextRange textRange() {
		return this.textRange(this.lightweightASTRoot());
	}

	public TextRange textRange(CompilationUnit astRoot) {
		return this.textRange(this.bodyDeclaration(astRoot));
	}
	
	TextRange textRange(ASTNode astNode) {
		return (astNode == null) ? null : new ASTNodeTextRange(astNode);
	}

	public TextRange nameTextRange() {
		return this.nameTextRange(this.lightweightASTRoot());
	}
	
	public TextRange nameTextRange(CompilationUnit astRoot) {
		ISourceRange sourceRange = this.nameSourceRange();
		return
			new SimpleTextRange(
				sourceRange.getOffset(), 
				sourceRange.getLength(),
				astRoot.getLineNumber(sourceRange.getOffset())
			);
	}
	
	private ISourceRange nameSourceRange() {
		try {
			return this.jdtMember.getNameRange();
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName());
	}


	// ********** abstract methods **********

	/**
	 * Return the member's body declaration.
	 */
	public abstract BodyDeclaration bodyDeclaration(CompilationUnit astRoot);

	public abstract IBinding binding(CompilationUnit astRoot);
	

	// ********** annotations **********

	public Annotation annotation(DeclarationAnnotationAdapter adapter, CompilationUnit astRoot) {
		return adapter.annotation(this.modifiedDeclaration(astRoot));
	}

	public Annotation annotation(DeclarationAnnotationAdapter adapter) {
		return this.annotation(adapter, this.astRoot());
	}

	public boolean containsAnnotation(DeclarationAnnotationAdapter adapter, CompilationUnit astRoot) {
		return this.annotation(adapter, astRoot) != null;
	}

	public boolean containsAnnotation(DeclarationAnnotationAdapter adapter) {
		return this.containsAnnotation(adapter, this.astRoot());
	}

	/**
	 * Return whether the member contains any one of the specified annotations.
	 */
	public boolean containsAnyAnnotation(DeclarationAnnotationAdapter[] adapters) {
		return this.containsAnyAnnotation(adapters, this.astRoot());
	}

	/**
	 * Return whether the member contains any one of the specified annotations.
	 */
	public boolean containsAnyAnnotation(DeclarationAnnotationAdapter[] adapters, CompilationUnit astRoot) {
		for (DeclarationAnnotationAdapter adapter : adapters) {
			if (this.containsAnnotation(adapter, astRoot)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the text range corresponding to the specified annotation.
	 * If the annotation is missing, return null.
	 */
	public TextRange annotationTextRange(DeclarationAnnotationAdapter adapter, CompilationUnit astRoot) {
		return this.textRange(this.annotation(adapter, astRoot));
	}

	/**
	 * Return the text range corresponding to the specified annotation.
	 * If the annotation is missing, return null.
	 */
	public TextRange annotationTextRange(DeclarationAnnotationAdapter adapter) {
		return this.annotationTextRange(adapter, this.astRoot());
	}

	/**
	 * Return the AST node corresponding to the specified annotation.
	 * If the annotation is missing, return its parent node.
	 */
	public ASTNode annotationASTNode(DeclarationAnnotationAdapter adapter, CompilationUnit astRoot) {
		return adapter.astNode(this.modifiedDeclaration(astRoot));
	}

	/**
	 * Return the AST node corresponding to the specified annotation.
	 * If the annotation is missing, return its parent node.
	 */
	public ASTNode annotationASTNode(DeclarationAnnotationAdapter adapter) {
		return this.annotationASTNode(adapter, this.astRoot());
	}

	/**
	 * Add the specified marker annotation to the member.
	 */
	public void newMarkerAnnotation(final DeclarationAnnotationAdapter adapter) {
		this.edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				adapter.newMarkerAnnotation(declaration);
			}
		});		
	}

	/**
	 * Add the specified single member annotation to the member.
	 */
	public void newSingleMemberAnnotation(final DeclarationAnnotationAdapter adapter) {
		this.edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				adapter.newSingleMemberAnnotation(declaration);
			}
		});		
	}

	/**
	 * Add the specified normal annotation to the member.
	 */
	public void newNormalAnnotation(final DeclarationAnnotationAdapter adapter) {
		this.edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				adapter.newNormalAnnotation(declaration);
			}
		});		
	}

	/**
	 * Remove the specified annotation from the member.
	 */
	public void removeAnnotation(final DeclarationAnnotationAdapter adapter) {
		this.edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				adapter.removeAnnotation(declaration);
			}
		});		
	}


	// ********** annotation elements **********

	public <T> T annotationElementValue(DeclarationAnnotationElementAdapter<T> adapter, CompilationUnit astRoot) {
		return adapter.getValue(this.modifiedDeclaration(astRoot));
	}

	public <T> T annotationElementValue(DeclarationAnnotationElementAdapter<T> adapter) {
		return this.annotationElementValue(adapter, this.astRoot());
	}

	public Expression annotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return adapter.expression(this.modifiedDeclaration(astRoot));
	}

	public Expression annotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter) {
		return this.annotationElementExpression(adapter, this.astRoot());
	}

	public boolean containsAnnotationElement(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return this.annotationElementExpression(adapter, astRoot) != null;
	}

	public boolean containsAnnotationElement(DeclarationAnnotationElementAdapter<?> adapter) {
		return this.containsAnnotationElement(adapter, this.astRoot());
	}

	/**
	 * Return the text range corresponding to the specified element.
	 * If the element is missing, return null.
	 */
	public TextRange annotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return this.textRange(this.annotationElementExpression(adapter, astRoot));
	}

	/**
	 * Return the text range corresponding to the specified element.
	 * If the element is missing, return null.
	 */
	public TextRange annotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter) {
		return this.annotationElementTextRange(adapter, this.astRoot());
	}

	/**
	 * Return the AST node corresponding to the specified element.
	 * If the element is missing, return its parent node.
	 */
	public ASTNode annotationElementASTNode(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return adapter.astNode(this.modifiedDeclaration(astRoot));
	}

	/**
	 * Return the AST node corresponding to the specified element.
	 * If the element is missing, return its parent node.
	 */
	public ASTNode annotationElementASTNode(DeclarationAnnotationElementAdapter<?> adapter) {
		return this.annotationElementASTNode(adapter, this.astRoot());
	}

	/**
	 * Set the value of the specified element.
	 */
	public <T> void setAnnotationElementValue(final DeclarationAnnotationElementAdapter<T> adapter, final T value) {
		this.edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				adapter.setValue(value, declaration);
			}
		});		
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
		ICompilationUnit compilationUnit = this.compilationUnit();
		if ( ! compilationUnit.isWorkingCopy()) {
			compilationUnit.becomeWorkingCopy(null);
		}

		ITextFileBuffer buffer = FileBuffers.getTextFileBufferManager().getTextFileBuffer(compilationUnit.getResource().getFullPath(), LocationKind.NORMALIZE);
		boolean sharedDocument = (buffer != null);  // documents are typically shared when they are already open in an editor
		IDocument doc = sharedDocument ?
				buffer.getDocument()
			:
				new Document(compilationUnit.getBuffer().getContents());

		CompilationUnit astRoot = this.astRoot();
		astRoot.recordModifications();

		editor.edit(this.modifiedDeclaration(astRoot));

		TextEdit edits = astRoot.rewrite(doc, compilationUnit.getJavaProject().getOptions(true));
		if (sharedDocument) {
			this.modifySharedDocumentCommandExecutor().execute(new ModifySharedDocumentCommand(edits, doc));
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
		this.annotationEditFormatter().format(doc, edits);
	}

	private AnnotationEditFormatter annotationEditFormatter() {
		return this.annotationEditFormatter;
	}

	private CommandExecutor modifySharedDocumentCommandExecutor() {
		return this.modifySharedDocumentCommandExecutorProvider.commandExecutor();
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
