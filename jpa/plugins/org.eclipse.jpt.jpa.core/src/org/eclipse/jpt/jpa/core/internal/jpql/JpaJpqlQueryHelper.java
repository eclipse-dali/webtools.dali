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
package org.eclipse.jpt.jpa.core.internal.jpql;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationPreferences;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.persistence.jpa.jpql.JPQLQueryHelper;
import org.eclipse.persistence.jpa.jpql.JPQLQueryProblem;
import org.eclipse.persistence.jpa.jpql.spi.IQuery;
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
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public final class JpaJpqlQueryHelper extends JPQLQueryHelper {

	/**
	 * Caches the provider in order to prevent recreating the SPI representation of the JPA artifacts
	 * more than once.
	 */
	private JpaManagedTypeProvider managedTypeProvider;

	/**
	 * Creates a new <code>JpaQueryHelper</code>.
	 */
	public JpaJpqlQueryHelper() {
		super();
	}

	/**
	 * Calculates the start and end positions by adjusting them to be at the same position within
	 * <em>jpqlQuery</em>, which may differ from <em>parsedJpqlQuery</em> since the parsed tree does
	 * not keep track of multiple whitespace. The range is also increased if the start and end
	 * positions are the same.
	 *
	 * @param problem The {@link JPQLQueryProblem problem} that was found in the JPQL query, which is
	 * either a grammatical or semantic problem
	 * @param parsedJpqlQuery The string representation of the parsed tree representation of the JPQL
	 * query
	 * @param actualQuery The actual JPQL query that was parsed and validated
	 * @return The start and end positions, which may have been adjusted
	 */
	public int[] buildPositions(JPQLQueryProblem problem, String parsedJpqlQuery, String actualQuery) {

		int startPosition = problem.getStartPosition();
		int endPosition   = problem.getEndPosition();

		// If the start and end positions are the same, then expand the text range
		if (startPosition == endPosition) {
			startPosition = Math.max(startPosition - 1, 0);
		}

		// Reposition the cursor so it's correctly positioned in the actual query, which is the
		// since it may contains more than one whitespace for a single whitespace
		int newStartPosition = ExpressionTools.repositionCursor(parsedJpqlQuery, startPosition, actualQuery);

		if (newStartPosition != startPosition) {
			endPosition  += (newStartPosition - startPosition);
			startPosition = newStartPosition;
		}

		return new int[] { startPosition, endPosition };
	}

	/**
	 * Creates a new {@link IMessage} for the given {@link JPQLQueryProblem}.
	 *
	 * @param namedQuery The model object for which a new {@link IMessage} is creating describing the
	 * problem
	 * @param problem The {@link JPQLQueryProblem problem} that was found in the JPQL query, which is
	 * either a grammatical or semantic problem
	 * @param textRange The range of the JPQL query in the Java source file
	 * @param parsedJpqlQuery The string representation of the parsed tree representation of the JPQL
	 * query, which may differ from the actual JPQL query since it does not keep more than one
	 * whitespace
	 * @param actualQuery The actual JPQL query that was parsed and validated
	 * @param offset This offset is used to move the start position
	 * @return A new {@link IMessage} that has the required information to display the problem
	 * underline and the error message in the Problems view
	 */
	private IMessage buildProblem(NamedQuery namedQuery,
	                              TextRange textRange,
	                              JPQLQueryProblem problem,
	                              String parsedJpqlQuery,
	                              String actualQuery,
	                              int offset) {

		// Convert the positions from the parsed JPQL query to the actual JPQL query
		int[] positions = buildPositions(problem, parsedJpqlQuery, actualQuery);

		// Now convert the adjusted positions once again to be in the query where the escape
		// characters are in their literal forms
		int[] newStartPosition = { positions[0] };
		ExpressionTools.escape(actualQuery, newStartPosition);
		int escapeOffset = positions[0] - newStartPosition[0];

		positions[0] -= escapeOffset;
		positions[1] -= escapeOffset;

		// Create the text range of the problem
		textRange = new SimpleTextRange(
			textRange.getOffset() + positions[0] + offset,
			positions[1] - positions[0],
			textRange.getLineNumber()
		);

		// Now create the message
		IMessage message = DefaultJpaValidationMessages.buildMessage(
			severity(namedQuery),
			problem.getMessageKey(),
			problem.getMessageArguments(),
			namedQuery,
			textRange
		);
		message.setBundleName("jpa_jpql_validation");
		return message;
	}

	private JpaManagedTypeProvider buildProvider(NamedQuery query) {
		try {
			return new JpaPersistenceUnit(query.getJpaProject(), query.getPersistenceUnit());
		}
		catch (Exception e) {
			return new JpaMappingFile(query.getJpaProject(), query.getMappingFileRoot().getParent());
		}
	}

	/**
	 * Disposes the provider so the application metadata is not kept in memory.
	 */
	public void disposeProvider() {
		managedTypeProvider = null;
	}

	/**
	 * Sets the given named query and string representation of the JPQL query.
	 *
	 * @param namedQuery The model object where the JPQL query is stored
	 * @param actualQuery The actual JPQL query, which can differ from the one owned by the model
	 * object, which happens when the model is out of sync because it has not been updated yet
	 */
	public void setQuery(NamedQuery namedQuery, String actualQuery) {

		if (managedTypeProvider == null) {
			managedTypeProvider = buildProvider(namedQuery);
		}

		IQuery query = new JpaQuery(managedTypeProvider, namedQuery, actualQuery);
		super.setQuery(query);
	}

	/**
	 * Retrieve the severity that is associated to JPQL query validation, which cannot be retrieved
	 * using the JPQL problem message key.

	 * @param targetObject The object for which a new {@link IMessage} is creating describing the
	 * problem
	 * @return The global severity for validating JPQL queries
	 */
	private int severity(Object targetObject) {
		return JpaValidationPreferences.getProblemSeverityPreference(
			targetObject,
			JpaValidationMessages.JPQL_QUERY_VALIDATION
		);
	}

	private boolean shouldValidate(NamedQuery namedQuery) {
		return JpaValidationPreferences.getProblemSeverityPreference(
			namedQuery,
			JpaValidationMessages.JPQL_QUERY_VALIDATION
		) == -1;
	}

	/**
	 * Validates the given {@link NamedQuery} by validating the JPQL query.
	 *
	 * @param namedQuery The JPQL query to validate
	 * @param jpqlQuery The JPQL query, which might be different from what the model object since
	 * the escape characters should not be in their literal forms (should have '\r' and not '\\r')
	 * @param textRange The range of the JPQL query string within the document
	 * @param offset This offset is used to move the start position
	 * @param messages The list of {@link IMessage IMessages} that will be used to add validation
	 * problems
	 */
	public void validate(NamedQuery namedQuery,
	                     String jpqlQuery,
	                     TextRange textRange,
	                     int offset,
	                     List<IMessage> messages) {

		// Make this quick check so we don't validate the query, which is time consuming
		if (shouldValidate(namedQuery)) {

			setQuery(namedQuery, jpqlQuery);
			String parsedJpqlQuery = getParsedJPQLQuery();

			for (JPQLQueryProblem problem : validate()) {

				IMessage message = buildProblem(
					namedQuery,
					textRange,
					problem,
					parsedJpqlQuery,
					jpqlQuery,
					offset
				);

				messages.add(message);
			}
		}
	}
}