/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.jpa.core.JpaModel;

/**
 * Transform a JPA model into a flag indicating whether the model supports
 * the configured JPA version.
 * The flag is <code>null</code> if the JPA model is <code>null</code>.
 */
public class JpaVersionIsCompatibleWith<M extends JpaModel>
	extends AbstractTransformer<M, Boolean>
{
	private final String version;

	public JpaVersionIsCompatibleWith(String version) {
		super();
		if (version == null) {
			throw new NullPointerException();
		}
		this.version = version;
	}

	@Override
	protected Boolean transform_(M jpaModel) {
		return Boolean.valueOf(jpaModel.getJpaProject().getJpaPlatform().getJpaVersion().isCompatibleWithJpaVersion(this.version));
	}
}
