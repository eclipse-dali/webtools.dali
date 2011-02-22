/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Entity;
import org.eclipse.swt.widgets.Composite;

/**
 * @version 2.0
 * @since 2.0
 */
public class EntityCachingPropertyComposite extends Pane<Entity> {

	/**
	 * Creates a new <code>EntityCachingPropertyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public EntityCachingPropertyComposite(Pane<? extends Caching> parentComposite,
	                                      PropertyValueModel<Entity> subjectHolder,
	                                      Composite parent) {

		super(parentComposite, subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Cache Type
		new CacheTypeComposite(this, container);

		// Cache Size
		new CacheSizeComposite(this, container);

		// Share Cache
		new SharedCacheComposite(this, container);
	}
}
