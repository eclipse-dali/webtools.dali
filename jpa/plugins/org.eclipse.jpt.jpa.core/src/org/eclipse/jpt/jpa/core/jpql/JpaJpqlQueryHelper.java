/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.core.jpql;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpql.spi.IManagedTypeBuilder;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeProvider;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaQuery;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.persistence.jpa.jpql.JPQLQueryProblem;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar;
import org.eclipse.persistence.jpa.jpql.tools.AbstractJPQLQueryHelper;
import org.eclipse.persistence.jpa.jpql.tools.ContentAssistExtension;
import org.eclipse.persistence.jpa.jpql.tools.ContentAssistProposals;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappingBuilder;
import org.eclipse.persistence.jpa.jpql.tools.spi.IQuery;
import org.eclipse.persistence.jpa.jpql.tools.utility.XmlEscapeCharacterConverter;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * This helper can perform the following operations over a JPQL query:
 * <ul>
 * <li>Calculates the result type of a query: {@link #getResultType()};</li>
 * <li>Calculates the type of an input parameter: {@link #getParameterType(String)}.</li>
 * <li>Calculates the possible choices to complete the query from a given
 *     position (used for content assist): {@link #buildContentAssistItems(int)}.</li>
 * <li>Validates the query by introspecting its grammar and semantic:
 *     <ul>
 *     <li>{@link #validate()},</li>
 *     <li>{@link #validateGrammar()},</li>
 *     <li>{@link #validateSemantic()}.</li>
 *     </ul></li>
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public abstract class JpaJpqlQueryHelper extends AbstractJPQLQueryHelper {

	private JpaProject jpaProject;

	/**
	 * Caches the provider in order to prevent recreating the SPI representation of the JPA artifacts
	 * more than once.
	 */
	private IManagedTypeProvider managedTypeProvider;

	/**
	 * Creates a new <code>JpaQueryHelper</code>.
	 *
	 * @param jpqlGrammar The grammar that defines how to parse a JPQL query
	 */
	protected JpaJpqlQueryHelper(JPQLGrammar jpqlGrammar) {
		super(jpqlGrammar);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentAssistProposals buildContentAssistProposals(int position) {
		return super.buildContentAssistProposals(position, buildContentAssistProposalsHelper());
	}

	/**
	 * Creates the helper that will be used to extend the support for JPQL content assist.
	 *
	 * @return A new instance of {@link GenericContentAssistProposalHelper}
	 */
	protected ContentAssistExtension buildContentAssistProposalsHelper() {
		return new DefaultContentAssistExtension(jpaProject);
	}

	/**
	 * Create the builder that will create the right implementation of {@link org.eclipse.persistence.
	 * jpa.jpql.spi.IManagedType IManagedType}.
	 *
	 * @return A new {@link IMappingBuilder}
	 */
	protected abstract IManagedTypeBuilder buildManagedTypeBuilder();

	/**
	 * Create the builder that will create the right implementation of {@link org.eclipse.persistence.
	 * jpa.jpql.spi.IMapping IMapping}.
	 *
	 * @return A new {@link IMappingBuilder}
	 */
	protected abstract IMappingBuilder<AttributeMapping> buildMappingBuilder();

	/**
	 * Calculates the start and end positions by adjusting them to be at the same position within
	 * <em>jpqlQuery</em>, which may differ from <em>parsedJpqlQuery</em> since the parsed tree does
	 * not keep track of multiple whitespace. The range is also increased if the start and end
	 * positions are the same.
	 *
	 * @param problem The {@link JPQLQueryProblem problem} that was found in the JPQL query, which is
	 * either a grammatical or semantic problem
	 * @param parsedJpqlQuery The generated string from {@link org.eclipse.persistence.jpa.jpql.
	 * parser.JPQLExpression JPQLExpression}
	 * @param jpqlQuery The actual JPQL query that was parsed and validated
	 * @param actualJpqlQuery The actual string that is not escaped and found in the document (either
	 * in an XML file or in a Java annotation)
	 * @param offset This offset is used to move the start position
	 * @param escapeType Determines how to escape the JPQL query, if required
	 * @return The start and end positions, which may have been adjusted
	 */
	public int[] buildPositions(JPQLQueryProblem problem,
	                            String parsedJpqlQuery,
	                            String jpqlQuery,
	                            String actualJpqlQuery,
	                            int offset,
	                            EscapeType escapeType) {

		int[] positions = { problem.getStartPosition(), problem.getEndPosition() };

		// If the start and end positions are the same, then expand the text range
		if (positions[0] == positions[1]) {
			positions[0] = Math.max(positions[0] - 1, 0);
		}

		// Reposition the cursor so it's correctly positioned in the non-escaped JPQL query
		ExpressionTools.reposition(parsedJpqlQuery, positions, jpqlQuery);

		// Now add the leading offset
		positions[0] += offset;
		positions[1] += offset;

		// Now convert the adjusted positions once again to be in the actual JPQL query that is
		// found in the document, i.e. that may contain escape characters
		if (escapeType == EscapeType.JAVA) {
			ExpressionTools.repositionJava(actualJpqlQuery, positions);
		}
		else if (escapeType == EscapeType.XML) {
			XmlEscapeCharacterConverter.reposition(actualJpqlQuery, positions);
		}

		return positions;
	}

	/**
	 * Creates a new {@link IMessage} for the given {@link JPQLQueryProblem}.
	 *
	 * @param namedQuery The model object for which a new {@link IMessage} is creating describing the problem
	 * @param problem The {@link JPQLQueryProblem problem} that was found in the JPQL query, which is
	 * either a grammatical or semantic problem
	 * @param textRanges The list of {@link TextRange} objects that represents the JPQL query string
	 * within the document. The list should contain either one {@link TextRange} if the JPQL query is
	 * a single string or many if the JPQL query is split into multiple strings
	 * @param parsedJpqlQuery The generated string from {@link org.eclipse.persistence.jpa.jpql.
	 * parser.JPQLExpression JPQLExpression}
	 * @param jpqlQuery The actual JPQL query that was parsed and validated
	 * @param actualJpqlQuery The actual string that is not escaped and found in the document (either
	 * in an XML file or in a Java annotation)
	 * @param offset This offset is used to move the start position
	 * @param escapeType Determines how to escape the JPQL query, if required
	 * @return The list {@link IMessage} objects that has the required information to display the
	 * problem, which support split locations (i.e. for a split strings)
	 */
	protected List<IMessage> buildProblems(NamedQuery namedQuery,
	                                       List<TextRange> textRanges,
	                                       JPQLQueryProblem problem,
	                                       String parsedJpqlQuery,
	                                       String jpqlQuery,
	                                       String actualJpqlQuery,
	                                       int offset,
	                                       EscapeType escapeType) {

		// Convert the positions from the parsed JPQL query to the actual JPQL query
		int[] positions = buildPositions(problem, parsedJpqlQuery, jpqlQuery, actualJpqlQuery, offset, escapeType);

		List<IMessage> messages = new ArrayList<IMessage>();
		int problemLength = positions[1] - positions[0];
		int problemOffset = positions[0];
		boolean done = false;

		// Traverse the list of TextRanges in order to properly calculate
		// the offset within either the single string or the split string
		for (TextRange textRange : textRanges) {

			// (offset * 2) is to not including the double quotes (specified by the offset) if present
			int textRangeOffset = (escapeType == EscapeType.JAVA) ? 2 : 0;
			int textRangeLength = (textRange.getLength() - textRangeOffset);

			// The position of the problem is within the TextRange
			if (problemOffset <= textRangeLength) {

				// Calculate to see if the problem is entirely within the TextRange,
				// otherwise it will be divided into two or more TextRanges
				int partialProblemLength = problemLength;

				if (problemOffset + problemLength > textRangeLength) {
					partialProblemLength = textRangeLength - problemOffset;
					problemLength -= partialProblemLength;
					textRangeLength = 0;
				}
				else {
					done = true;
				}

				// Create the validation message
				IMessage message = new Message(
					"jpt_jpa_core_jpql_validation",
					IMessage.HIGH_SEVERITY,
					problem.getMessageKey(),
					problem.getMessageArguments(),
					namedQuery.getResource()
				);
				message.setMarkerId(JpaProject.MARKER_TYPE);
				int lineNumber = textRange.getLineNumber();
				message.setLineNo(lineNumber);
				if (lineNumber == IMessage.LINENO_UNSET) {
					message.setAttribute(IMarker.LOCATION, " "); //$NON-NLS-1$
				}
				message.setOffset(textRange.getOffset() + problemOffset);
				message.setLength(partialProblemLength);

				messages.add(message);

				// Done traversing the list of TextRanges
				if (done) {
					break;
				}
			}

			// The problem is not within the TextRange, remove the TextRange length from the
			// problem's data so it can be properly calculated within a subsequent TextRange
			problemOffset = (textRangeLength == 0) ? 0 : problemOffset - textRangeLength;
		}

		return messages;
	}

	/**
	 * Creates a new {@link JpaManagedTypeProvider} which will provide access to the application's
	 * JPA metadata information.
	 *
	 * @param jpaProject The JPA project associated with the Eclipse project
	 * @param persistenceUnit The persistence unit model
	 * @return A new {@link JpaManagedTypeProvider}
	 */
	protected JpaManagedTypeProvider buildProvider(JpaProject jpaProject, PersistenceUnit persistenceUnit) {
		return new JpaManagedTypeProvider(
			jpaProject,
			persistenceUnit,
			buildManagedTypeBuilder(),
			buildMappingBuilder()
		);
	}

	protected int getValidationSeverity(NamedQuery namedQuery) {
		return JptJpaCorePlugin.instance().getValidationMessageSeverity(
				namedQuery.getResource().getProject(),
				JptJpaCoreValidationMessages.JPQL_QUERY_VALIDATION.getID(),
				JptJpaCoreValidationMessages.JPQL_QUERY_VALIDATION.getDefaultSeverity()
			);
	}

	/**
	 * Sets the given named query and string representation of the JPQL query.
	 *
	 * @param namedQuery The model object where the JPQL query is stored
	 * @param actualQuery The actual JPQL query, which can differ from the one owned by the model
	 * object, which happens when the model is out of sync because it has not been updated yet
	 */
	public void setQuery(NamedQuery namedQuery, String actualQuery) {

		jpaProject = namedQuery.getJpaProject();

		if (managedTypeProvider == null) {
			managedTypeProvider = buildProvider(jpaProject, namedQuery.getPersistenceUnit());
		}

		IQuery query = new JpaQuery(managedTypeProvider, namedQuery, actualQuery);
		super.setQuery(query);
	}

	protected boolean shouldValidate(NamedQuery namedQuery) {
		return this.getValidationSeverity(namedQuery) != ValidationMessage.IGNORE_SEVERITY;
	}

	/**
	 * Validates the given {@link NamedQuery} by validating the JPQL query.
	 *
	 * @param namedQuery The JPQL query to validate
	 * @param jpqlQuery The JPQL query, which might be different from what the model object since
	 * the escape characters should not be in their literal forms (should have '\r' and not '\\r')
	 * @param textRanges The list of {@link TextRange} objects that represents the JPQL query string
	 * within the document. The list should contain either one {@link TextRange} if the JPQL query is
	 * a single string or many if the JPQL query is split into multiple strings
	 * @param offset This offset is used to move the start position
	 * @param messages The list of {@link IMessage IMessages} that will be used to add validation problems
	 * @param escapeCharacters Determines whether the special characters (\n, \r for instance) should
	 * be escaped or not
	 */
	public void validate(NamedQuery namedQuery,
	                     String jpqlQuery,
	                     String actualJpqlQuery,
	                     List<TextRange> textRanges,
	                     int offset,
	                     EscapeType escapeType,
	                     List<IMessage> messages) {

		try {
			// Make this quick check so we don't validate the query, which is time consuming
			if (shouldValidate(namedQuery)) {

				setQuery(namedQuery, jpqlQuery);
				String parsedJpqlQuery = getParsedJPQLQuery();

				for (JPQLQueryProblem problem : validate()) {

					List<IMessage> results = buildProblems(
						namedQuery,
						textRanges,
						problem,
						parsedJpqlQuery,
						jpqlQuery,
						actualJpqlQuery,
						offset,
						escapeType
					);

					messages.addAll(results);
				}
			}
		}
		finally {
			// Only dispose the information related to the query
			dispose();
		}
	}

	/**
	 * Constants used to determine how to escape the JPQL query.
	 */
	public enum EscapeType {

		/**
		 * Escapes some characters when the JPQL query is in an annotation, eg: \t becomes \\t.
		 */
		JAVA,

		/**
		 * No modification will be performed on the JPQL query.
		 */
		NONE,

		/**
		 * Escapes XML reserved characters when the JPQL query is in defined xml, eg: > becomes &gt;.
		 */
		XML
	}
}
