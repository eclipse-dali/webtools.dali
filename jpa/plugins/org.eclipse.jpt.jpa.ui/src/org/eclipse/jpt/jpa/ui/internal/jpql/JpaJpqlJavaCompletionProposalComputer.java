/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.swt.graphics.Image;

/**
 * This computer adds content assist support when it is invoked inside the query element of {@link
 * javax.persistence.NamedQuery &#64;NamedQuery}.
 *
 * @version 3.0.1
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("restriction")
public final class JpaJpqlJavaCompletionProposalComputer extends JpqlCompletionProposalComputer<ICompletionProposal>
                                                         implements IJavaCompletionProposalComputer {

	/**
	 * Creates a new <code>JpaJpqlJavaCompletionProposalComputer</code>.
	 */
	public JpaJpqlJavaCompletionProposalComputer() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	ICompletionProposal buildProposal(String proposal,
	                                  String displayString,
	                                  String additionalInfo,
	                                  Image image,
	                                  int cursorOffset) {

		return new JpqlCompletionProposal(
			contentAssistProposals,
			proposal,
			displayString,
			additionalInfo,
			image,
			namedQuery,
			actualQuery,
			jpqlQuery,
			offset + 1, // +1 is to skip the opening "
			position,
			cursorOffset,
			true
		);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ICompletionProposal> computeCompletionProposals(ContentAssistInvocationContext context,
	                                                            IProgressMonitor monitor) {

		if (context instanceof JavaContentAssistInvocationContext) {
			monitor.beginTask(null, 100);
			try {
				return computeCompletionProposals((JavaContentAssistInvocationContext) context, monitor);
			}
			catch (Exception e) {
				Status status = new Status(IStatus.ERROR, JptJpaUiPlugin.PLUGIN_ID, JptUiMessages.JpaJpqlJavaCompletionProposalComputer_Error, e);
				JptJpaCorePlugin.log(status);
			}
			finally {
				monitor.done();
			}
		}

		return Collections.emptyList();
	}

	private List<ICompletionProposal> computeCompletionProposals(JavaContentAssistInvocationContext context,
	                                                             IProgressMonitor monitor) throws Exception {

		CompletionContext completionContext = context.getCoreContext();
		if (completionContext == null) return Collections.emptyList();

		// The token "start" is the offset of the token's first character within the document.
		// A token start of -1 can means:
		// - It is inside the string representation of a unicode character, \\u0|0E9 where | is the
		//   cursor, then -1 is returned;
		// - The string is not valid (it has some invalid characters)
		int tokenStart = completionContext.getTokenStart();
		if (tokenStart == -1) return Collections.emptyList();

		int[] position = { completionContext.getOffset() - tokenStart - 1 };
		if (position[0] < 0) return Collections.emptyList();

		ICompilationUnit compilationUnit = context.getCompilationUnit();
		if (compilationUnit == null) return Collections.emptyList();
		CompilationUnit astRoot = ASTTools.buildASTRoot(compilationUnit);

		IFile file = getCorrespondingResource(compilationUnit);
		if (file == null) return Collections.emptyList();

		JpaFile jpaFile = JptJpaCorePlugin.getJpaFile(file);
		if (jpaFile == null) return Collections.emptyList();

		monitor.worked(80);
		checkCanceled(monitor);

		// Retrieve the JPA's model object
		NamedQuery namedQuery = namedQuery(astRoot, jpaFile, tokenStart);
		if (namedQuery == null) return Collections.emptyList();

		// Retrieve the actual value of the element "query" since the content assist can be
		// invoked before the model received the new content
		String jpqlQuery = jpqlQuery(astRoot, tokenStart, completionContext.getTokenEnd(), position);

		// Now create the proposals
		return buildProposals(namedQuery, jpqlQuery, tokenStart, position[0]);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IContextInformation> computeContextInformation(ContentAssistInvocationContext context,
	                                                           IProgressMonitor monitor) {

		return Collections.emptyList();
	}

	private NamedQuery findNamedQuery(JpaStructureNode structureNode,
	                                  CompilationUnit astRoot,
	                                  int tokenStart) {

		if (structureNode instanceof JavaPersistentType) {
			JavaPersistentType persistentType = (JavaPersistentType) structureNode;
			JavaTypeMapping typeMapping = persistentType.getMapping();

			if (typeMapping instanceof JavaEntity) {
				JavaEntity entity = (JavaEntity) typeMapping;

				for (Iterator<JavaNamedQuery> queries = entity.getQueryContainer().namedQueries(); queries.hasNext(); ) {
					JavaNamedQuery namedQuery = queries.next();
					TextRange textRange = namedQuery.getQueryAnnotation().getQueryTextRange(astRoot);

					if ((textRange != null) && textRange.includes(tokenStart)) {
						return namedQuery;
					}
				}
			}
		}

		return null;
	}

	private IFile getCorrespondingResource(ICompilationUnit compilationUnit) {
		try {
			return (IFile) compilationUnit.getCorrespondingResource();
		}
		catch (JavaModelException ex) {
			JptJpaCorePlugin.log(ex);
			return null;
		}
	}

	private boolean isInsideNode(ASTNode node, int tokenStart, int tokenEnd) {
		int startPosition = node.getStartPosition();
		return startPosition <= tokenStart &&
		       startPosition + node.getLength() >= tokenEnd;
	}

	private String jpqlQuery(CompilationUnit astRoot, int tokenStart, int tokenEnd, int[] position) {

		String jpqlQuery = retrieveQuery(astRoot, tokenStart, tokenEnd);

		if (jpqlQuery == null) {
			jpqlQuery = StringTools.EMPTY_STRING;
		}
		else if (StringTools.stringIsQuoted(jpqlQuery)) {
			jpqlQuery = jpqlQuery.substring(1, jpqlQuery.length() - 1);
		}

		return jpqlQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	String modifyJpqlQuery(String jpqlQuery, int[] position) {
		return ExpressionTools.unescape(jpqlQuery, position);
	}

	private NamedQuery namedQuery(CompilationUnit astRoot, JpaFile jpaFile, int tokenStart) {

		for (JpaStructureNode node : jpaFile.getRootStructureNodes()) {
			NamedQuery namedQuery = findNamedQuery(node, astRoot, tokenStart);
			if (namedQuery != null) {
				return namedQuery;
			}
		}

		return null;
	}

	/**
	 * This twisted code is meant to retrieve the real string value that is not escaped and to also
	 * retrieve the position within the non-escaped string. The query could have escape characters,
	 * such as \r, \n etc being written as \\r, \\n, the position is based on that escaped string,
	 * the conversion will convert them into \r and \r and adjust the position accordingly.
	 *
	 * @param astRoot The parsed tree representation of the Java source file
	 * @param tokenStart The beginning of the query expression of the {@link javax.persistence.NamedQuery
	 * &#64;NamedQuery}'s query member within the source file
	 * @param tokenEnd The end of the query member within the source file
	 * @param position The position of the cursor within the query expression
	 * @return The actual value retrieved from the query element
	 */
	@SuppressWarnings("unchecked")
	private String retrieveQuery(CompilationUnit astRoot, int tokenStart, int tokenEnd) {

		// Dig into the TypeDeclarations
		for (AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>) astRoot.types()) {

			if (isInsideNode(type, tokenStart, tokenEnd)) {

				// Dig inside its modifiers and annotations
				for (IExtendedModifier modifier : (List<IExtendedModifier>) type.modifiers()) {

					if (!modifier.isAnnotation()) {
						continue;
					}

					Annotation annotation = (Annotation) modifier;

					// Dig inside the annotation
					if (isInsideNode(annotation, tokenStart, tokenEnd)) {

						// @NamedQueries({...})
						if (annotation.isSingleMemberAnnotation()) {
							SingleMemberAnnotation singleMemberAnnotation = (SingleMemberAnnotation) annotation;
							ArrayInitializer array = (ArrayInitializer) singleMemberAnnotation.getValue();

							for (org.eclipse.jdt.core.dom.Expression expression : (List<org.eclipse.jdt.core.dom.Expression>) array.expressions()) {
								if (isInsideNode(expression, tokenStart, tokenEnd)) {
									return retrieveQuery((NormalAnnotation) expression, tokenStart, tokenEnd);
								}
							}
						}
						// @NamedQuery()
						else if (annotation.isNormalAnnotation()) {
							return retrieveQuery((NormalAnnotation) annotation, tokenStart, tokenEnd);
						}
					}
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private String retrieveQuery(NormalAnnotation annotation, int tokenStart, int tokenEnd) {

		for (MemberValuePair pair : (List<MemberValuePair>) annotation.values()) {
			org.eclipse.jdt.core.dom.Expression expression = pair.getValue();

			if (isInsideNode(expression, tokenStart, tokenEnd)) {
				StringLiteral literal = (StringLiteral) pair.getValue();
				return literal.getEscapedValue();
			}
		}

		return null;
	}
}