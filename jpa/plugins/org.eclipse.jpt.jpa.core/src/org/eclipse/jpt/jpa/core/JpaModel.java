/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * JPA-specific protocol. All JPA objects belong to a JPA project, are
 * associated with a resource, and have a parent (excepting the JPA project).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.0
 */
public interface JpaModel
	extends Model, IAdaptable
{
	/**
	 * Return the JPA project manager.
	 */
	JpaProject.Manager getJpaProjectManager();

	/**
	 * Return the JPA project the node belongs to.
	 */
	JpaProject getJpaProject();

	/**
	 * Return the model's JPA platform.
	 */
	JpaPlatform getJpaPlatform();

	/**
	 * Return the JPA node's parent. The JPA project will not have a parent.
	 */
	JpaModel getParent();

	/**
	 * Some state or child (or grandchild etc.) of the JPA model changed.
	 * Fire a state change event. Implied by this behavior is that any change
	 * to any JPA model in a JPA project will trigger the JPA project to fire a
	 * state change event.
	 * <p>
	 * The intent is for this method to be called by the JPA model's children
	 * whenever their state has changed.
	 * 
	 * @see Model#addStateChangeListener(org.eclipse.jpt.common.utility.model.listener.StateChangeListener)
	 * @see org.eclipse.jpt.common.utility.model.event.StateChangeEvent
	 * @see org.eclipse.jpt.common.utility.model.listener.StateChangeListener
	 */
	void stateChanged();

	/**
	 * Return the model's resource, typically used for validation messages.
	 */
	IResource getResource();

	/**
	 * A predicate that returns whether a JPA model's JPA version compatible
	 * with the configured version.
	 * @see JpaPlatform.Version#isCompatibleWithJpaVersion(String)
	 */
	class JpaVersionIsCompatibleWith
		extends CriterionPredicate<JpaModel, String>
	{
		public JpaVersionIsCompatibleWith(String version) {
			super(version);
			if (version == null) {
				throw new NullPointerException();
			}
		}
		public boolean evaluate(JpaModel jpaModel) {
			return jpaModel.getJpaProject().getJpaPlatform().getJpaVersion().isCompatibleWithJpaVersion(this.criterion);
		}
	}
}
