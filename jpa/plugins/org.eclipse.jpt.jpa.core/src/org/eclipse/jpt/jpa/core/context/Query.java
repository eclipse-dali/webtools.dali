/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
/**
 * Named and named native queries.
 * <p>
 * Queries can be defined on<ul>
 * <li>Java and <code>orm.xml</code> entities
 * <li>Java (<em>only</em>) mapped superclasses
 * <li><code>orm.xml</code> entity mappings elements
 * </ul>
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
public interface Query
	extends JpaNamedContextModel
{
	/**
	 * Return the query's type.
	 */
	Class<? extends Query> getQueryType();

	/**
	 * Return whether the specified query has the same state as this
	 * query but is <em>not</em> this query.
	 * @see #getQueryType()
	 */
	boolean isEquivalentTo(Query query);


	// ********** hints **********

	String HINTS_LIST = "hints"; //$NON-NLS-1$

	/**
	 * Return the query's hints.
	 */
	ListIterable<? extends QueryHint> getHints();

	/**
	 * Return the number of hints.
	 */
	int getHintsSize();

	/**
	 * Add a hint to the query and return the object representing it.
	 */
	QueryHint addHint();

	/**
	 * Add a hint to the query and return the object representing it.
	 */
	QueryHint addHint(int index);

	/**
	 * Remove the hint at the index from the query.
	 */
	void removeHint(QueryHint queryHint);

	/**
	 * Remove the hint from the query.
	 */
	void removeHint(int index);

	/**
	 * Move the hint from the source index to the target index.
	 */
	void moveHint(int targetIndex, int sourceIndex);

	QueryHint getHint(int i);


	// ********** validation **********

	void validate(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter);

	/**
	 * Return whether this query should be validated and have validation messages displayed
	 */
	boolean supportsValidationMessages();

	/**
	 * Returns the {@link TextRange} of the name property.
	 */
	TextRange getNameTextRange();
}
