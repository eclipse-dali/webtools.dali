/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;

/**
 * Transform a JPA node into a
 * flag indicating whether the node supports JPA 2.0.
 * The flag is <code>null</code> if the JPA node is <code>null</code>.
 */
public final class Jpa2_0FlagTransformer<T extends JpaModel>
	extends AbstractTransformer<T, Boolean>
	implements Serializable
{
	/**
	 * Convert the specified JPA node model into a boolean model that returns
	 * whether the JPA node supports JPA 2.0.
	 * The flag is <code>null</code> if the JPA node is <code>null</code>.
	 */
	public static <T extends JpaModel> PropertyValueModel<Boolean> convertToFlagModel(PropertyValueModel<T> jpaNodeModel) {
		return new TransformationPropertyValueModel<T, Boolean>(jpaNodeModel, Jpa2_0FlagTransformer.<T>instance());
	}

	@SuppressWarnings("rawtypes")
	private static final Transformer INSTANCE = new Jpa2_0FlagTransformer();

	@SuppressWarnings("unchecked")
	public static <T extends JpaModel> Transformer<T, Boolean> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private Jpa2_0FlagTransformer() {
		super();
	}

	@Override
	protected Boolean transform_(T jpaNode) {
		return Boolean.valueOf(jpaNode.getJpaProject().getJpaPlatform().getJpaVersion().isCompatibleWithJpaVersion(JpaProject2_0.FACET_VERSION_STRING));
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
