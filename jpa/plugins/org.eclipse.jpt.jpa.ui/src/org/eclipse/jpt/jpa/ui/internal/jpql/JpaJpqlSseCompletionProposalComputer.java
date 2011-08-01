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
import java.util.List;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.sse.ui.contentassist.ICompletionProposalComputer;

/**
 * This computer adds content assist support when it is invoked inside the &lt;query&gt; element
 * defined in a mapping file (ORM Configuration).
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
public final class JpaJpqlSseCompletionProposalComputer extends JpqlCompletionProposalComputer<ICompletionProposal>
                                                        implements ICompletionProposalComputer {

	/**
	 * Creates a new <code>JpaJpqlSseCompletionProposalComputer</code>.
	 */
	public JpaJpqlSseCompletionProposalComputer() {
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
			offset,
			position,
			cursorOffset,
			true
		);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ICompletionProposal> computeCompletionProposals(CompletionProposalInvocationContext context,
	                                                            IProgressMonitor monitor) {

		monitor.beginTask(null, 100);

		try {
			int offset = context.getInvocationOffset();
			if (offset == -1) return Collections.emptyList();

			ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
			ITextFileBuffer buffer = manager.getTextFileBuffer(context.getDocument());
			if (buffer == null) return Collections.emptyList();

			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile file = root.getFile(buffer.getLocation());

			JpaFile jpaFile = JptJpaCorePlugin.getJpaFile(file);
			if (jpaFile == null) return Collections.emptyList();

			monitor.worked(80);
			checkCanceled(monitor);

			// Retrieve the JPA's model object
			int[] position = new int[1];
			OrmNamedQuery namedQuery = namedQuery(jpaFile, offset, position);
			if (namedQuery == null) return Collections.emptyList();

			// Keep track of the beginning of the text since the entire string is always replaced
			int tokenStart = offset - position[0];

			// Now create the proposals
			String jpqlQuery = jpqlQuery(namedQuery, context.getDocument());
			return buildProposals(namedQuery, jpqlQuery, tokenStart, position[0]);
		}
		catch (Exception e) {
			Status status = new Status(IStatus.ERROR, JptJpaUiPlugin.PLUGIN_ID, JptUiMessages.JpaJpqlSseCompletionProposalComputer_Error, e);
			JptJpaCorePlugin.log(status);
		}
		finally {
			monitor.done();
		}

		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IContextInformation> computeContextInformation(CompletionProposalInvocationContext context,
	                                                           IProgressMonitor monitor) {

		return Collections.emptyList();
	}

	private OrmNamedQuery findNamedQuery(JpaStructureNode structureNode, int offset, int[] position) {

		if (structureNode instanceof EntityMappings) {
			EntityMappings entityMappings = (EntityMappings) structureNode;

			// First look into the global queries
			OrmNamedQuery query = findNamedQuery(entityMappings.getQueryContainer(), offset, position);

			// Now traverse each entity
			if (query != null) {
				return query;
			}

			for (PersistentType persistentType : entityMappings.getPersistentTypes()) {
				TypeMapping typeMapping = persistentType.getMapping();

				if (typeMapping instanceof OrmEntity) {
					query = findNamedQuery(((OrmEntity) typeMapping).getQueryContainer(), offset, position);

					if (query != null) {
						return query;
					}
				}
			}
		}

		return null;
	}

	private OrmNamedQuery findNamedQuery(OrmQueryContainer container, int offset, int[] position) {

		for (OrmNamedQuery namedQuery : container.getNamedQueries()) {
			TextRange textRange = namedQuery.getQueryTextRange();

			if (textRange.touches(offset)) {
				position[0] = offset - textRange.getOffset();
				return namedQuery;
			}
		}

		return null;
	}

	private String jpqlQuery(OrmNamedQuery namedQuery, IDocument document) {
		try {
			TextRange range = namedQuery.getQueryTextRange();
			return document.get(range.getOffset(), range.getLength());
		}
		catch (BadLocationException e) {
			return StringTools.EMPTY_STRING;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	String modifyJpqlQuery(String jpqlQuery, int[] position) {
		return ExpressionTools.unescape(jpqlQuery, position);
	}

	private OrmNamedQuery namedQuery(JpaFile jpaFile, int offset, int[] position) {

		for (JpaStructureNode node : jpaFile.getRootStructureNodes()) {
			OrmNamedQuery namedQuery = findNamedQuery(node, offset, position);
			if (namedQuery != null) {
				return namedQuery;
			}
		}

		return null;
	}
}