/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.swt.graphics.Image;

/**
 * This computer adds content assist support when it is invoked inside the query element of {@link
 * javax.persistence.NamedQuery &#64;NamedQuery}.
 *
 * @version 3.3
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

		return new JpqlJavaCompletionProposal(
			contentAssistProposals,
			proposal,
			displayString,
			additionalInfo,
			image,
			namedQuery,
			jpqlQuery,
			tokenStart + 1, // +1 is to skip the opening "
			tokenEnd, // Don't do -1 to skip the closing ", the length is right since it's tokenEnd - tokenStart
			position,
			cursorOffset
		);
	}

	@SuppressWarnings("unchecked")
	private List<Expression> children(InfixExpression expression) {
		List<Expression> children = new ArrayList<Expression>();
		children.add(expression.getLeftOperand());
		children.add(expression.getRightOperand());
		children.addAll(expression.extendedOperands());
		return children;
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
			catch (Exception ex) {
				JptJpaUiPlugin.instance().logError(ex, JptJpaUiMessages.JPA_JPQL_JAVA_COMPLETION_PROPOSAL_COMPUTER_ERROR);
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
		int tokenStart[] = { completionContext.getTokenStart() };
		int tokenEnd[]   = { completionContext.getTokenEnd() };
		if (tokenStart[0] == -1) return Collections.emptyList();

		int[] position = { completionContext.getOffset() - tokenStart[0] - 1 };
		if (position[0] < 0) return Collections.emptyList();

		ICompilationUnit compilationUnit = context.getCompilationUnit();
		if (compilationUnit == null) return Collections.emptyList();
		CompilationUnit astRoot = ASTTools.buildASTRoot(compilationUnit);

		IFile file = getCorrespondingResource(compilationUnit);
		if (file == null) return Collections.emptyList();

		JpaFile jpaFile = (JpaFile) file.getAdapter(JpaFile.class);
		if (jpaFile == null) return Collections.emptyList();

		monitor.worked(80);
		checkCanceled(monitor);

		// Retrieve the JPA's model object
		NamedQuery namedQuery = namedQuery(jpaFile, tokenStart[0]);
		if (namedQuery == null) return Collections.emptyList();

		// Retrieve the actual value of the element "query" since the content assist can be
		// invoked before the model received the new content
		String jpqlQuery = retrieveQuery(astRoot, tokenStart, tokenEnd, position);

		// Now create the proposals
		ResourceManager resourceManager = this.getResourceManager(context.getViewer().getTextWidget());
		return buildProposals(namedQuery, jpqlQuery, tokenStart[0], tokenEnd[0], position[0], resourceManager);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IContextInformation> computeContextInformation(ContentAssistInvocationContext context,
	                                                           IProgressMonitor monitor) {

		return Collections.emptyList();
	}

	private NamedQuery findNamedQuery(JpaStructureNode structureNode,
	                                  int tokenStart) {

		if (structureNode instanceof JavaPersistentType) {
			JavaPersistentType persistentType = (JavaPersistentType) structureNode;
			JavaTypeMapping typeMapping = persistentType.getMapping();

			for (Query query : typeMapping.getQueries()){
				if (query.getQueryType().equals(NamedQuery.class)){
					JavaNamedQuery namedQuery = (JavaNamedQuery) query;
					for (TextRange textRange : namedQuery.getQueryAnnotation().getQueryTextRanges()) {
						if ((textRange != null) && textRange.includes(tokenStart)) {
							return namedQuery;
						}
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
			JptJpaUiPlugin.instance().logError(ex);
			return null;
		}
	}

	private boolean isInsideNode(ASTNode node, int tokenStart, int tokenEnd) {
		int startPosition = node.getStartPosition();
		return startPosition <= tokenStart &&
		       startPosition + node.getLength() >= tokenEnd;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	String modifyJpqlQuery(String jpqlQuery, int[] position) {
		return ExpressionTools.unescape(jpqlQuery, position);
	}

	private NamedQuery namedQuery(JpaFile jpaFile, int tokenStart) {

		for (JpaStructureNode node : jpaFile.getRootStructureNodes()) {
			NamedQuery namedQuery = findNamedQuery(node, tokenStart);
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
	private String retrieveQuery(CompilationUnit astRoot, int[] tokenStart, int[] tokenEnd, int[] position) {

		// Dig into the TypeDeclarations
		for (AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>) astRoot.types()) {

			if (isInsideNode(type, tokenStart[0], tokenEnd[0])) {

				// Dig inside its modifiers and annotations
				for (IExtendedModifier modifier : (List<IExtendedModifier>) type.modifiers()) {

					if (!modifier.isAnnotation()) {
						continue;
					}

					Annotation annotation = (Annotation) modifier;

					// Dig inside the annotation
					if (isInsideNode(annotation, tokenStart[0], tokenEnd[0])) {

						// @NamedQueries({...})
						if (annotation.isSingleMemberAnnotation()) {
							SingleMemberAnnotation singleMemberAnnotation = (SingleMemberAnnotation) annotation;
							Expression value = singleMemberAnnotation.getValue();

							if (value.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
								ArrayInitializer array = (ArrayInitializer) value;

								for (Expression expression : (List<Expression>) array.expressions()) {
									if (isInsideNode(expression, tokenStart[0], tokenEnd[0])) {
										return retrieveQuery((NormalAnnotation) expression, tokenStart, tokenEnd, position);
									}
								}
							}
							else {
								NormalAnnotation childAnnotation = (NormalAnnotation) value;

								if (isInsideNode(childAnnotation, tokenStart[0], tokenEnd[0])) {
									return retrieveQuery(childAnnotation, tokenStart, tokenEnd, position);
								}
							}
						}
						// @NamedQuery()
						else if (annotation.isNormalAnnotation()) {
							return retrieveQuery((NormalAnnotation) annotation, tokenStart, tokenEnd, position);
						}
					}
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private String retrieveQuery(NormalAnnotation annotation, int[] tokenStart, int[] tokenEnd, int[] position) {

		for (MemberValuePair pair : (List<MemberValuePair>) annotation.values()) {
			org.eclipse.jdt.core.dom.Expression expression = pair.getValue();

			if (isInsideNode(expression, tokenStart[0], tokenEnd[0])) {
				Expression child = pair.getValue();

				// Single string
				if (child.getNodeType() == ASTNode.STRING_LITERAL) {
					StringLiteral literal = (StringLiteral) pair.getValue();
					return unquotedString(literal.getEscapedValue());
				}

				// Build the JPQL query from the concatenated strings
				if (child.getNodeType() == ASTNode.INFIX_EXPRESSION) {

					StringBuilder sb = new StringBuilder();
					boolean adjustPosition = true;

					for (Expression childNode : children((InfixExpression) child)) {

						StringLiteral literal = (StringLiteral) childNode;
						sb.append(unquotedString(literal.getEscapedValue()));

						if (adjustPosition && !isInsideNode(literal, tokenEnd[0], tokenEnd[0])) {
							position[0] += (literal.getLength() - 2);
						}
						else {
							adjustPosition = false;
						}
					}

					// Now adjust the start and end offsets so it includes the entire InfixExpression
					// because content assist will only replace one string literal and right now we
					// only support replacing the entire string
					tokenStart[0] = child.getStartPosition();
					tokenEnd[0] = child.getStartPosition() + child.getLength() - 1;

					return sb.toString();
				}
			}
		}

		return StringTools.EMPTY_STRING;
	}

	private String unquotedString(String value) {

		if (value == null) {
			value = StringTools.EMPTY_STRING;
		}
		else if (StringTools.isQuoted(value)) {
			value = value.substring(1, value.length() - 1);
		}

		return value;
	}
}