/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.caching;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * CacheSizeComposite
 */
public class CacheSizeComposite extends AbstractPane<EntityCaching>
{
	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public CacheSizeComposite(AbstractPane<EntityCaching> parentComposite,
	                          Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Integer> buildCacheSizeHolder() {
		return new PropertyAspectAdapter<EntityCaching, Integer>(getSubjectHolder(), Caching.CACHE_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getCacheSize();

				if (value == null) {
					value = -1;
				}
				return value;
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setCacheSize(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildLabeledSpinner(
			container,
			"Cache Size:",
			this.buildCacheSizeHolder(),
			Caching.DEFAULT_CACHE_SIZE,
			-1,
			Integer.MAX_VALUE,
			(String) null // TODO
		);
	}
}
