/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.JptResourceTypeReference;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Common protocol for JPA models that have a context, as opposed to
 * resource models.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface JpaContextModel
	extends JpaModel, JptResourceTypeReference
{
	/**
	 * Return the persistence unit if the context model is within a 
	 * persistence unit. Otherwise throw an exception.
	 */
	PersistenceUnit getPersistenceUnit();

	/**
	 * Return the mapping file root if the context model is within a 
	 * mapping file. Otherwise throw an exception.
	 */
	MappingFile.Root getMappingFileRoot();

	JpaContextModel getParent();


	// ********** database stuff **********

	SchemaContainer getContextDefaultDbSchemaContainer();

	Catalog getContextDefaultDbCatalog();

	Schema getContextDefaultDbSchema();


	// ********** validation **********

	TextRange getValidationTextRange();

	/**
	 * Add the model's validation messages to the specified "collecting
	 * parameter".
	 */
	void validate(List<IMessage> messages, IReporter reporter);


	// *********** completion proposals ***********

	/**
	 * Return the content assist proposals for the specified position
	 * in the file.
	 */
	Iterable<String> getCompletionProposals(int pos);


	// ********** synchronize/update **********

	/**
	 * The resource model has changed; synchronize the context model with it.
	 * This will probably trigger a call to {@link #update(IProgressMonitor)}.
	 */
	void synchronizeWithResourceModel(IProgressMonitor monitor);

	/**
	 * Some non-trivial state in the JPA project has changed; update the
	 * state of the context model that is dependent on yet other parts of the
	 * model's JPA project.
	 * If the dependent state changes also, yet another <em>update</em> will be
	 * triggered, possibly followed by yet more <em>updates</em>; until the JPA
	 * project's state quiesces.
	 */
	void update(IProgressMonitor monitor);
}
