/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.wst.common.internal.emf.resource.Translator;

public class CacheTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public CacheTranslator() {
		super(CACHE, ECLIPSELINK_ORM_PKG.getXmlCacheHolder_Cache());
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			createExpiryTranslator(),
			createExpiryTimeOfDayTranslator(),
			createSizeTranslator(),
			createSharedTranslator(),
			createTypeTranslator(),
			createAlwaysRefreshTranslator(),
			createRefreshOnlyIfNewerTranslator(),
			createDisableHitsTranslator(),
			createCoordinationTypeTranslator(),
		};
	}

	protected Translator createExpiryTranslator() {
		return new Translator(CACHE__EXPIRY, ECLIPSELINK_ORM_PKG.getXmlCache_Expiry());
	}
	
	protected Translator createExpiryTimeOfDayTranslator() {
		return new Translator(EXPIRY_TIME_OF_DAY, ECLIPSELINK_ORM_PKG.getXmlCache_ExpiryTimeOfDay());
	}
	
	protected Translator createSizeTranslator() {
		return new Translator(CACHE__SIZE, ECLIPSELINK_ORM_PKG.getXmlCache_Size(), DOM_ATTRIBUTE);
	}
	
	protected Translator createSharedTranslator() {
		return new Translator(CACHE__SHARED, ECLIPSELINK_ORM_PKG.getXmlCache_Shared(), DOM_ATTRIBUTE);
	}
	
	protected Translator createTypeTranslator() {
		return new Translator(CACHE__TYPE, ECLIPSELINK_ORM_PKG.getXmlCache_Type(), DOM_ATTRIBUTE);
	}
	
	protected Translator createAlwaysRefreshTranslator() {
		return new Translator(CACHE__ALWAYS_REFRESH, ECLIPSELINK_ORM_PKG.getXmlCache_AlwaysRefresh(), DOM_ATTRIBUTE);
	}
	
	protected Translator createRefreshOnlyIfNewerTranslator() {
		return new Translator(CACHE__REFRESH_ONLY_IF_NEWER, ECLIPSELINK_ORM_PKG.getXmlCache_RefreshOnlyIfNewer(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDisableHitsTranslator() {
		return new Translator(CACHE__DISABLE_HITS, ECLIPSELINK_ORM_PKG.getXmlCache_DisableHits(), DOM_ATTRIBUTE);
	}
	
	protected Translator createCoordinationTypeTranslator() {
		return new Translator(CACHE__COORDINATION_TYPE, ECLIPSELINK_ORM_PKG.getXmlCache_CoordinationType(), DOM_ATTRIBUTE);
	}
	
}
