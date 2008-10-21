/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ExpiryTimeOfDayTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public ExpiryTimeOfDayTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
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
			createHourTranslator(),
			createMinuteTranslator(),
			createSecondTranslator(),
			createMillisecondTranslator(),
		};
	}
	protected Translator createHourTranslator() {
		return new Translator(EXPIRY_TIME_OF_DAY__HOUR, ECLIPSELINK_ORM_PKG.getXmlCache_Size(), DOM_ATTRIBUTE);
	}
	
	protected Translator createMinuteTranslator() {
		return new Translator(EXPIRY_TIME_OF_DAY__MINUTE, ECLIPSELINK_ORM_PKG.getXmlCache_Shared(), DOM_ATTRIBUTE);
	}
	
	protected Translator createSecondTranslator() {
		return new Translator(EXPIRY_TIME_OF_DAY__SECOND, ECLIPSELINK_ORM_PKG.getXmlCache_Shared(), DOM_ATTRIBUTE);
	}
	
	protected Translator createMillisecondTranslator() {
		return new Translator(EXPIRY_TIME_OF_DAY__MILLISECOND, ECLIPSELINK_ORM_PKG.getXmlCache_AlwaysRefresh(), DOM_ATTRIBUTE);
	}
}
