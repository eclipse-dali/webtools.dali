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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.internal.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.persistence.jpa.internal.jpql.WordParser;
import org.eclipse.persistence.jpa.internal.jpql.parser.Expression;
import org.eclipse.persistence.jpa.internal.jpql.parser.IdentifierRole;
import org.eclipse.persistence.jpa.internal.jpql.parser.JPQLExpression;
import org.eclipse.persistence.jpa.jpql.ContentAssistProposals;
import org.eclipse.persistence.jpa.jpql.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.spi.IMapping;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import static org.eclipse.jpt.common.utility.internal.CollectionTools.*;

/**
 * The abstract definition of JPQL content assist support.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings({"nls", "restriction"})
abstract class JpqlCompletionProposalComputer<T> {

	/**
	 * The current value of the query element.
	 */
	String actualQuery;

	/**
	 * The provider of content assist items based on the position of the cursor within the JPQL query.
	 */
	ContentAssistProposals contentAssistProposals;

	/**
	 * The local registry containing the images used to display the possible proposals. The registry is
	 * disposed when the session ended.
	 */
	private ImageRegistry imageRegistry;

	/**
	 * The same value as {@link #actualQuery} or the modified query that was used by the Hermes parser.
	 */
	String jpqlQuery;

	/**
	 * The JPA model object that is used to access the Java project.
	 */
	NamedQuery namedQuery;

	/**
	 * The start position of the query within the document.
	 */
	int offset;

	/**
	 *
	 */
	private String partialWord;

	/**
	 * The position of the cursor within {@link #actualQuery}.
	 */
	int position;

	/**
	 * This helper is responsible to retrieve the possible proposals to complete or to add more
	 * information to a JPQL based on the position of the cursor.
	 */
	final JpaJpqlQueryHelper queryHelper;

	/**
	 * Creates a new <code>JpqlCompletionProposalComputer</code>.
	 */
	public JpqlCompletionProposalComputer() {
		super();
		queryHelper = new JpaJpqlQueryHelper();
	}

	/**
	 * Adds completion proposals for the abstract schema names that are possible proposals.
	 *
	 * @param proposals The list used to store the new completion proposals
	 */
	private void addAbstractSchemaNames(List<T> proposals) {
		for (IEntity abstractSchemaType : sortByNames(contentAssistProposals.abstractSchemaTypes())) {
			proposals.add(buildAbstractSchemaNameProposal(abstractSchemaType));
		}
	}

	/**
	 * Adds completion proposals for the identification variables that are possible proposals.
	 *
	 * @param proposals The list used to store the new completion proposals
	 */
	private void addIdentificationVariables(List<T> proposals) {
		for (String variable : sort(contentAssistProposals.identificationVariables())) {
			proposals.add(buildIdentificationVariableProposal(variable));
		}
	}

	/**
	 * Adds completion proposals for the JPQL identifiers that are possible proposals.
	 *
	 * @param proposals The list used to store the new completion proposals
	 */
	private void addIdentifiers(List<T> proposals) {
		for (String identifier : sort(contentAssistProposals.identifiers())) {
			proposals.add(buildIdentifierProposal(identifier));
		}
	}

	private String additionalInfo(String proposal) {
		return JpqlIdentifierMessages.localizedMessage(proposal);
	}

	/**
	 * Adds completion proposals for the state fields and association fields that are possible proposals.
	 *
	 * @param proposals The list used to store the new completion proposals
	 */
	private void addMappings(List<T> proposals) {
		for (IMapping mapping : sort(contentAssistProposals.mappings())) {
			proposals.add(buildMappingProposal(mapping));
		}
	}

	private T buildAbstractSchemaNameProposal(IEntity abstractSchemaType) {
		String proposal = abstractSchemaType.getName();
		return buildProposal(proposal, proposal, entityImage());
	}

	private Comparator<IEntity> buildEntityNameComparator() {
		return new Comparator<IEntity>() {
			public int compare(IEntity entity1, IEntity entity2) {
				return entity1.getName().compareTo(entity2.getName());
			}
		};
	}

	private String buildIdentificationVariableDisplayString(String identificationVariable) {

		IEntity abstractSchemaType = contentAssistProposals.getAbstractSchemaType(identificationVariable);

		if (abstractSchemaType != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(identificationVariable);
			sb.append(" : ");
			sb.append(abstractSchemaType.getName());
			identificationVariable = sb.toString();
		}

		return identificationVariable;
	}

	private T buildIdentificationVariableProposal(String proposal) {
		return buildProposal(
			proposal,
			buildIdentificationVariableDisplayString(proposal),
			identificationVariableImage()
		);
	}

	private T buildIdentifierProposal(String proposal) {

		String additionalInfo = additionalInfo(proposal);
		IdentifierRole role = JPQLExpression.identifierRole(proposal);
		boolean realFunction = (role == IdentifierRole.FUNCTION) && isRealFunction(proposal);
		int cursorOffset = 0;

		// There is at least one letter before the cursor, if the setting "Match First Letter Case"
		// is true, then match the case of the JPQL identifier with the first character
		if ((partialWord.length() > 0) && shouldMatchFirstCharacterCase()) {
			if (Character.isLowerCase(partialWord.charAt(0))) {
				proposal = proposal.toLowerCase();
			}
		}
		// Convert the case of the JPQL identifier based on the setting
		else if (shouldUseLowercaseIdentifiers()) {
			proposal = proposal.toLowerCase();
		}

		// For JPQL function, we add () to the display string, example: AVG()
		// But for TRUE, FALSE, etc, we don't add ()
		if (realFunction) {
			proposal += "()";
			cursorOffset--;
		}

		return buildProposal(
			proposal,
			proposal,
			additionalInfo,
			identifierImage(realFunction),
			cursorOffset
		);
	}

	private T buildMappingProposal(IMapping mapping) {
		String proposal = mapping.getName();
		return buildProposal(proposal, proposal, mappingImage(mapping));
	}

	private T buildProposal(String proposal, String displayString, Image image) {
		return buildProposal(proposal, displayString, null, image, 0);
	}

	/**
	 * Creates a new completion proposal for the given proposal.
	 *
	 * @param proposal A valid proposal that can be inserted into the query
	 * @param displayString The human readable string of the proposal
	 * @param additionalInfo Optional additional information about the proposal. The additional
	 * information will be presented to assist the user in deciding if the selected proposal is the
	 * desired choice
	 * @param image The image that represents the proposal
	 * @param cursorOffset An offset that moves the cursor backward or forward after it is adjusted
	 * based on the given proposal
	 * @return The completion proposal
	 */
	abstract T buildProposal(String proposal,
	                         String displayString,
	                         String additionalInfo,
	                         Image image,
	                         int cursorOffset);

	/**
	 * Creates the list of completion proposals based on the current content of the JPQL query and at
	 * the specified position.
	 *
	 * @param namedQuery The model object used to access the application metadata information
	 * @param actualQuery The model object may sometimes be out of sync with the actual content, the
	 * actual query is required for proper content assist
	 * @param offset The beginning of the string within the document
	 * @param position The position of the cursor within the query, which starts at the beginning of
	 * that query and not the document
	 * @return The list of completion proposals
	 */
	final List<T> buildProposals(NamedQuery namedQuery, String actualQuery, int offset, int position) {

		this.offset      = offset;
		this.actualQuery = actualQuery;
		this.namedQuery  = namedQuery;

		// It's possible the string has literal representation of the escape characters, if required,
		// convert them into actual escape characters and adjust the position accordingly
		int[] positions  = { position };
		this.jpqlQuery   = modifyJpqlQuery(actualQuery, positions);
		this.position    = positions[0];
		this.partialWord = partialWord();

		// Gather the possible proposals
		this.queryHelper.setQuery(namedQuery, jpqlQuery);
		this.contentAssistProposals = queryHelper.buildContentAssistProposals(positions[0]);
		this.queryHelper.dispose();

		// Create the proposals for those proposals
		List<T> proposals = new ArrayList<T>();
		addAbstractSchemaNames    (proposals);
		addIdentificationVariables(proposals);
		addIdentifiers            (proposals);
		addMappings               (proposals);

		return proposals;
	}

	final void checkCanceled(IProgressMonitor monitor) throws InterruptedException {
		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}

	/**
	 * Clears the information used to retrieve the content assist proposals.
	 */
	final void clearInformation() {

		offset      = -1;
		position    = -1;
		actualQuery = null;
		namedQuery  = null;
		partialWord = null;
		contentAssistProposals = null;
	}

	private Image entityImage() {
		return getImage(JptUiIcons.ENTITY);
	}

	/**
	 * Returns the reason why this computer was unable to produce any completion proposals or
	 * context information.
	 *
	 * @return An error message or <code>null</code> if no error occurred
	 */
	public String getErrorMessage() {
		return null;
	}

	private Image getImage(String key) {
		ImageRegistry imageRegistry = getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null) {
			imageRegistry.put(key, getImageDescriptor(key));
			image = imageRegistry.get(key);
		}
		return image;
	}

	private ImageDescriptor getImageDescriptor(String key) {
		return JptJpaUiPlugin.getImageDescriptor(key);
	}

	private ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry(Display.getCurrent());
		}
		return imageRegistry;
	}

	private Image identificationVariableImage() {
		return getImage(JptUiIcons.JPQL_VARIABLE);
	}

	private Image identifierImage(boolean function) {

		if (function) {
			return getImage(JptUiIcons.JPQL_FUNCTION);
		}

		return getImage(JptUiIcons.JPQL_IDENTIFIER);
	}

	private boolean isRealFunction(String identifier) {
		return identifier != Expression.TRUE         &&
		       identifier != Expression.FALSE        &&
		       identifier != Expression.NULL         &&
		       identifier != Expression.CURRENT_DATE &&
		       identifier != Expression.CURRENT_TIME &&
		       identifier != Expression.CURRENT_TIMESTAMP;
	}

	private Image mappingImage(IMapping mapping) {
		switch (mapping.getMappingType()) {
			case BASIC:               return getImage(JptUiIcons.BASIC);
			case BASIC_COLLECTION:    return getImage(JptUiIcons.ELEMENT_COLLECTION);
			case BASIC_MAP:           return getImage(JptUiIcons.ELEMENT_COLLECTION);
			case ELEMENT_COLLECTION:  return getImage(JptUiIcons.ELEMENT_COLLECTION);
			case EMBEDDED:            return getImage(JptUiIcons.EMBEDDED);
			case EMBEDDED_ID:         return getImage(JptUiIcons.EMBEDDED_ID);
			case ID:                  return getImage(JptUiIcons.ID);
			case MANY_TO_MANY:        return getImage(JptUiIcons.MANY_TO_MANY);
			case MANY_TO_ONE:         return getImage(JptUiIcons.MANY_TO_ONE);
			case ONE_TO_MANY:         return getImage(JptUiIcons.ONE_TO_MANY);
			case ONE_TO_ONE:          return getImage(JptUiIcons.ONE_TO_ONE);
			case TRANSFORMATION:      return getImage(JptUiIcons.BASIC);      // TODO
			case VARIABLE_ONE_TO_ONE: return getImage(JptUiIcons.ONE_TO_ONE); // TODO
			case VERSION:             return getImage(JptUiIcons.VERSION);
			default:                  return getImage(JptUiIcons.TRANSIENT);
		}
	}

	/**
	 * In some context, the given JPQL query needs to be modified before it is parsed.
	 *
	 * @param jpqlQuery The JPQL query to keep unchanged or to modify before parsing it
	 * @param position The position of the cursor within the JPQL query, which needs to be updated if
	 * the query is modified
	 * @return The given JPQL query or a modified version that will be parsed
	 */
	String modifyJpqlQuery(String jpqlQuery, int[] position) {
		return jpqlQuery;
	}

	private String partialWord() {
		WordParser wordParser = new WordParser(jpqlQuery);
		wordParser.setPosition(position);
		return wordParser.partialWord();
	}

	/**
	 * Informs the computer that a content assist session has ended.
	 */
	public void sessionEnded() {

		queryHelper.disposeProvider();
		clearInformation();

		if (imageRegistry != null) {
			imageRegistry.dispose();
		}
	}

	/**
	 * Informs the computer that a content assist session has started.
	 */
	public void sessionStarted() {
		// Nothing to do
	}

	private boolean shouldMatchFirstCharacterCase() {
		return JptJpaUiPlugin.instance().getPreferenceStore().getBoolean(JptJpaUiPlugin.JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY);
	}

	private boolean shouldUseLowercaseIdentifiers() {
		String value = JptJpaUiPlugin.instance().getPreferenceStore().getString(JptJpaUiPlugin.JPQL_IDENTIFIER_CASE_PREF_KEY);
		return JptJpaUiPlugin.JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE.equals(value);
	}

	private Iterable<IEntity> sortByNames(Iterable<IEntity> abstractSchemaTypes) {
		return CollectionTools.sort(abstractSchemaTypes, buildEntityNameComparator());
	}
}